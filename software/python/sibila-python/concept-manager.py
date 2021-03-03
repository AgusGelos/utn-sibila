
from typing import List
from dbmanager import DBManager
from entities.sibilaclasses import *

class ConceptManager:
    """
    Concept Manager:
    Clase que engloba todas las operaciones de gestión de conceptos y relaciones en OrientDB
    """
    def __init__ (self, host='http://localhost', port=2480, database='PPR', user='admin', password='admin'):
        
        db = DBManager(host,port,database,user,password)

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
    def insConcepto (concepto : Concepto):
        pass

    def updConcepto (concepto : Concepto):
        pass

    def delConcepto (concepto : Concepto, safe : bool=False):
        pass

    def getRefsConcepto (concepto : Concepto):
        pass

    def insEquivalencia (concepto : Concepto, equivalencia : str, peso : float):
        pass

    def getEquivalencias (concepto : Concepto):
        pass

    def insStruct (conceptoOrigen : Concepto, relacion : Relacion, conceptoDestino : Concepto):
        pass

    def getConceptos ():
        pass

    def getConceptoComplejos ():
        pass

    def getConceptoByName (name : str):
        pass

    #--------------------------------------------------------------------------------------------
    # GESTION DE RELACIONES
    # --------------------------------------------------------------------------------------------
    def insRelacion (conceptoOrigen : Concepto, relacion : Relacion, conceptoDestino : Concepto):
        pass

    def addTipoRelacion (nombreTipo : str):
        pass

    def getRelacionesComplejas ():
        pass

    def getRelaciones ():
        pass

    def getRelacionByName (name : str):
        pass

    #--------------------------------------------------------------------------------------------
    # OTRAS OPERACIONES
    # --------------------------------------------------------------------------------------------
    def getTipoTermino (termino : Termino):
        pass
    
    def cleanTerminos (terminos : List) -> List:
        pass

    def existStruct (conceptoOrigen : Concepto, relacion : Relacion, conceptoDestino : Concepto) -> bool:
        pass

    def existConcept (concepto : Concepto) -> bool:
        pass

    def existTipoRelacion (tipo : str) -> bool:
        pass

    def normalizeTipoRelacion (tipo : str) -> str:
        pass

    
    #--------------------------------------------------------------------------------------------
    # CONSULTAS ESPECIFICAS Y METODOS ASOCIADOS
    # --------------------------------------------------------------------------------------------
    def buildRouteQuery (conceptoInicial : Concepto, conceptosIncluidos : List) -> str: 
        pass

    def buildRouteDepth (conceptoInicial : Concepto, profundidad : int) -> str:
        pass

    def getRoute (conceptoInicial : Concepto, conceptosIncluidos : List):
        pass

    def getRouteRespuesta (respuesta : Respuesta):
        pass

    def getRoutesFrom (conceptoInicial : Concepto, profundidad : int):
        pass

    def getRoutesByType (relacion : Relacion):
        pass
