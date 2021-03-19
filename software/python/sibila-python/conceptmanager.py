
from typing import Dict, List,Tuple
from dbmanager import DBManager
from entities.sibilaclasses import *
import re

class ConceptManager:
    db : DBManager = None
    """
    Concept Manager:
    Clase que engloba todas las operaciones de gestión de conceptos y relaciones en OrientDB
    """
    def __init__ (self, host='http://localhost', port=2480, database='PPR', user='admin', password='admin'):        
        self.db = DBManager(host,port,database,user,password)

    '''
    Los VERBOS de la gestión de la base de datos van a ser:
    INS: Insertar un nuevo registro
    DEL: Eliminar un registro existente
    UPD: Modificar un registro existente
    GET: Obtener información
    '''

    #--------------------------------------------------------------------------------------------
    # GESTION DE CONCEPTOS
    # --------------------------------------------------------------------------------------------
    def insConcepto (self,concepto : Concepto) -> Tuple[str,int]:
        result = self.db.insVertex(
            classname="Concepto",
            fields={
                "Nombre":concepto.Nombre
                }
            )
        if result:
            id = self.db.extractId(result)
        else:
            id = None
        return result,id

    def updConcepto (self,oldConcepto : Concepto,newConcepto : Concepto) -> Tuple[str, int]:
        result = self.db.updVertex(
            classname="Concepto",
            keys={
                "Nombre":oldConcepto.Nombre
            },
            fields={
                "Nombre":newConcepto.Nombre
                }
            )
        if result:
            # El update no devuelve un ID sino la cantidad de registros afectados
            count = self.db.extractCount(result)
        else:
            count = 0
        return result,count

    def delConcepto (self,concepto : Concepto, safe : bool=True) -> Tuple[str,int]:
        result = None
        count = 0
        if safe:
            if not self.isSafeDelete(concepto):
                raise Exception("El concepto tiene relaciones, no es posible eliminarlo")

        result = self.db.delVertex(
            classname="Concepto",
            keys={
                "Nombre":concepto.Nombre
            })
        if result:
            # El delete no devuelve un ID sino la cantidad de registros afectados
            count = self.db.extractCount(result)
        else:
            count = 0
        return result,count

    def isSafeDelete (self, concepto : Concepto) -> bool:
        r = self.getRefsConcepto(concepto)
        return r == 0
        # Probando, porque por algun motivo la linea de abajo cree que es una tupla
        # return bool(refs == 0)

    def getRefsConcepto (self,concepto : Concepto) -> int:
        '''
        La funcion obtiene la cantidad de referencias (entrantes y salientes) de un concepto dado.
        Si no encuentra el Concepto devuelve 0
        '''
        query = "match {{class: Concepto, as: c, where: (Nombre = '{nombre}')}} return (c.in().size()+c.out().size()) as referencias".format(nombre=concepto.Nombre)
        result = self.db.execCommand(query)
        if result:
            referencias = result[0]["referencias"]
            return referencias
        else:
            return 0

    def getConceptos (self, keys : Dict = None, limit : int = 0):
        if keys:
            condicion = self.db.__getWhereFromDict__(keys)
        else:
            condicion = '1=1'
        if limit:
            limite = 'LIMIT {limit}'.format(limit=limit)
        else:
            limite = ''
        query = "match {{class: Concepto, as: c, where: ({condicion})}} return expand(c) as result {limite}".format(condicion=condicion,limite=limite)
        result = self.db.execCommand(query)
        if result:
            return result
        else:
            return None

    def getConceptosComplejos (self):
        result = self.getConceptos()
        complejos = []
        for concepto in result:
            if concepto['Nombre']:
                t = concepto['Nombre'].split()
                if (len(t) > 1):
                    complejos.append(concepto)
        return complejos

    def getConceptoByName (self,name : str):
        return self.getConceptos(keys={"Nombre":name})

    #--------------------------------------------------------------------------------------------
    # GESTION DE EQUIVALENCIAS
    # --------------------------------------------------------------------------------------------
    def insEquivalencia (self,concepto : Concepto, equivalencia : str, peso : float):
        result = None
        count = 0
        query = "update Concepto SET Equivalencias['{equivalencia}'] = {peso} where (Nombre = '{nombre}')".format(nombre=concepto.Nombre,equivalencia=equivalencia,peso=peso)
        result = self.db.execCommand(query)
        if result:
            count = self.db.extractCount(result)
        else:
            count = 0
        return result,count
    
    def delEquivalencia (self,concepto : Concepto, equivalencia : str):
        result = None
        count = 0
        query = "update Concepto remove Equivalencias = '{equivalencia}' where (Nombre = '{nombre}')".format(nombre=concepto.Nombre,equivalencia=equivalencia)
        result = self.db.execCommand(query)
        if result:
            count = self.db.extractCount(result)
        else:
            count = 0
        return result,count

    def getEquivalencias (self,concepto : Concepto) -> str:
        query = "match {{class: Concepto, as: c, where: (Nombre = '{nombre}')}} return c.Equivalencias as equivalencias".format(nombre=concepto.Nombre)
        result = self.db.execCommand(query)
        if result:
            equivalencias = result[0]["equivalencias"]
            return equivalencias
        else:
            return None

    #--------------------------------------------------------------------------------------------
    # GESTION DE RELACIONES
    # --------------------------------------------------------------------------------------------
    def insRelacion (self,conceptoOrigen : Concepto, relacion : Relacion, conceptoDestino : Concepto):
        result = self.db.insEdge(
            sourceClass='Concepto',
            sourceCondition={'Nombre':conceptoOrigen.Nombre},
            destClass='Concepto',
            destCondition={'Nombre':conceptoDestino.Nombre},
            edgeClass=relacion.Class
            )
        return result

    def isValidRelacionName (self,relacionName : str) -> bool:
        # El nombre de una relacion es valido si es una palabra, sino hay que adecuarlo
        return (len(relacionName.split()) == 1)

    def addTipoRelacion (self,nombreTipo : str):
        result = None
        count = 0
        if not self.isValidRelacionName(nombreTipo):
            raise Exception ("El nombre no es válido para la relación")

        query = "create class {classname} if not exists extends Relacion".format(classname=nombreTipo)
        result = self.db.execCommand(query)
        # La creacion de una clase no devuelve id ni count
        return result,count

    def getRelacionesComplejas (self):
        result = self.getRelaciones()
        complejas = []
        for relacion in result:
            t = re.findall('([A-Z][a-z]+)', relacion['@class'])
            if (len(t) > 1):
                complejas.append(relacion)
        return complejas

    def getRelaciones (self, keys : Dict = None, limit : int = 0):
        if keys:
            condicion = self.db.__getWhereFromDict__(keys)
        else:
            condicion = '1=1'
        if limit:
            limite = 'LIMIT {limit}'.format(limit=limit)
        else:
            limite = ''
        query = "select @class,count(*) as records from Relacion where {condicion} group by @class {limite}".format(condicion=condicion,limite=limite)
        result = self.db.execQuery(query=query)
        return result

    def getRelacionByName (self,name : str):
        # El query utiliza el nombre de la clase para filtrar
        return self.getRelaciones(keys={'@class':name})

    #--------------------------------------------------------------------------------------------
    # OTRAS OPERACIONES
    # --------------------------------------------------------------------------------------------
    def getTipoTermino (self,termino : Termino):
        pass
    
    def cleanTerminos (self,terminos : List) -> List:
        pass

    def insStruct (self,conceptoOrigen : Concepto, relacion : Relacion, conceptoDestino : Concepto):
        pass
        # TODO: Insertar una estructura, creando todos los elementos necesarios

    def existStruct (self,conceptoOrigen : Concepto, relacion : Relacion, conceptoDestino : Concepto) -> bool:
        query = "match {{class: Concepto, as: c1, where:(Nombre = '{c1}')}}.out('{r}') " \
                "{{class: Concepto, as: c2, where:(Nombre = '{c2}')}} " \
                "return count(*) as count".format(c1=conceptoOrigen.Nombre,r=relacion.Class,c2=conceptoDestino.Nombre)
        result = self.db.execQuery(query)
        count = result[0]['count']        
        return (count == 1)
        
    def existConcepto (self,concepto : str) -> bool:
        result = self.getConceptoByName(concepto)
        if result:
            return True
        else:
            return False

    def existTipoRelacion (self,tipo : str) -> bool:
        result = self.getRelacionByName(tipo)
        if result:
            return True
        else:
            return False

    def normalizeTipoRelacion (self,tipo : str) -> str:
        # Capitalizar cada palabra
        pascalcase = tipo.title()
        # Eliminar todo tipo de espacios
        normalized = re.sub(r'\s', '', pascalcase)
        return normalized

    
    #--------------------------------------------------------------------------------------------
    # CONSULTAS ESPECIFICAS Y METODOS ASOCIADOS
    # --------------------------------------------------------------------------------------------
    def buildRouteQuery (self,conceptoInicial : Concepto, conceptosIncluidos : List) -> str: 
        pass
        # TODO: Construir el texto de la query que devuelve una ruta determinada por el concepto inicial y los conceptos incluidos

    def buildRouteDepth (self,conceptoInicial : Concepto, profundidad : int) -> str:
        pass
        # TODO: Construir el texto de la query que devuelve una ruta determinada por el concepto inicial y los conceptos hasta una profundidad dada

    def getRoute (self,conceptoInicial : Concepto, conceptosIncluidos : List):
        pass
        # TODO: Devolver la ruta que hay entre un concepto inicial y que contenga los conceptos incluidos

    def getRouteRespuesta (self,respuesta : Respuesta):
        pass
        # TODO: Devolver la ruta correspondiente a los nodos de una respuesta

    def getRoutesFrom (self,conceptoInicial : Concepto, profundidad : int):
        pass
        # TODO: Devolver la ruta que inicie en un concepto y que incluya todos los conceptos hasta una profundidad indicada

    def getRoutesByType (self,relacion : Relacion):
        pass
        # TODO: Devuelve todas las rutas, con sus conceptos, de un tipo indicado
