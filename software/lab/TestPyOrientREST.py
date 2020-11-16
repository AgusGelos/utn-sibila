import requests
from requests.auth import HTTPBasicAuth
import json

"""Prueba de documentacion general?.

"""

endpoint = "http://sibila.website:2480"
"""Raíz de los endpoint utilizados para las llamadas REST
"""
method_query = "/query/PPR/sql/"
"""Ruta básica utilizada para las llamadas REST. Utiliza 'endpoint' como prefijo.
"""

def buildRouteDepth(root, depth):
    """Devuelve un nodo y todas sus relaciones hasta un nivel determinado

    La función recibe un nodo raíz (string) y una profundidad determinada y devuelve
    el query que al ejecutar obtiene esos datos.
    Args:
        root (string): Nombre del nodo raiz para iniciar la búsqueda
        depth (int): Profundidad máxima para la búsqueda de relaciones

    Returns:
        string: JSON con los datos del resultado
    """    
    query_conceptos_depth = """select FROM ( 
    MATCH {class: Concepto, where: (Nombre = '%s')}.out() 
          {class: Concepto, as: dest, while: ($depth < %i)} 
    RETURN 
        dest.@rid as idConcepto,
        dest.Nombre as Concepto,  
        dest.out() as idConceptosRelacionados, 
        dest.outE().@class as Relaciones, 
        dest.out().Nombre as ConceptosRelacionados
    )""" % (root,depth)
    return query_conceptos_depth

def buildRouteQuery(root,concepts):
    """Devuelve una ruta a partir de un nodo raiz y una lista de conceptos.

    La funcion recibe un nodo raíz (string) y una lista de conceptos (list(strings)) y
    devuelve el query que al ejecutarse devuelve esos datos.
    Establece una profundidad máxima igual a la cantidad de conceptos relacionados
    para evitar loops infinitos en caso de haber ciclos en la ruta
    Args:
        root (string): Nombre del nodo raiz para iniciar la búsqueda
        concepts (list): Lista de strings con los nombres de los nodos a buscar

    Returns:
        string: JSON con los datos encontrados
    """    
    max_len = len(concepts)
    concept_list = json.dumps(concepts)
    concept_list = concept_list.replace('"',"'")
    query_route = """select FROM (
    MATCH {class: Concepto, where: (Nombre = '%s')}.out()
        {class: Concepto, as: dest, 
  			while: (
              ($depth < %d) and 
              (Nombre IN %s)
            ),
  			where: (
              Nombre IN %s
            )
  		} 
    RETURN 
  	    DISTINCT 
  		dest.@rid as idConcepto,
        dest.Nombre as Concepto,  
        dest.out() as idConceptosRelacionados, 
        dest.outE().@class as Relaciones, 
        dest.out().Nombre as ConceptosRelacionados
    )""" % (root,max_len,concept_list,concept_list)
    return query_route

def execQuery (query):
    """Ejecuta la consulta pasada por parámetro y devuelve los datos en formato JSON.

    Args:
        query (string): Query a ejecutar

    Returns:
        string: JSON con los datos devueltos
    """    
    req_query = endpoint+method_query+query
    response = requests.get(req_query, auth=HTTPBasicAuth('admin', 'admin'))
    if (response.ok):
        # Convertir la cadena de bytes en un string, con encode utf-8
        parsed = json.loads(response.content.decode("utf-8"))
        # el response tiene un elemento llamado result, que contiene los valores devueltos
        result = parsed['result']
    else:
        result = None
    return result


if (__name__ == '__main__'):
    '''
    query = buildRouteDepth('PARADIGMA',1)
    result = execQuery(query)
    if (result != None):
        print(result)
        print ("".ljust(40,"="))
        print (json.dumps(result,indent=3))
    else:
        print ("No hay datos a mostrar")
    '''
    query = buildRouteQuery(root='POO',concepts=['POO','PROGRAMA','ALGORITMO','TEST','MENSAJE','ESTADO','OBJETO','SIMPLE'])
    result = execQuery(query)
    if (result != None):
        print(result)
        print ("".ljust(40,"="))
        print (json.dumps(result,indent=3))
    else:
        print ("No hay datos a mostrar")

'''
------------------------------------------------------------------------------------------
CODIGO DE PRUEBAS. UTILIZAR COMO REFERENIA PERO DOCUMENTAR LAS MODIFICACIONES QUE SE HAGAN
------------------------------------------------------------------------------------------
query_conceptos_depth2 = "select $path from (traverse both() from (select from Concepto where Nombre = 'PARADIGMA') while $depth <= 1)"

method_connect = "/connect/PPR"
query = "select from Concepto"
query_where = "select from Concepto where Nombre = 'PARADIGMA'"
query_match = "match {class: Concepto, as: c} return c"

req_conn = endpoint+method_connect

# Primero llamar al connect, para autenticar
#response = requests.get(req_conn, auth=HTTPBasicAuth('admin', 'admin'))
#print (response, response.content)
print("Llamando al servicio REST")
print("Despues de llamar a REST. Evaluando respuesta...")
# Si el response viene con codigo 200 es que todo funciono
if (response.ok):
    # Convertir la cadena de bytes en un string, con encode utf-8
    parsed = json.loads(response.content.decode("utf-8"))
    print (response, json.dumps(parsed,indent=3))
    # el response tiene un elemento llamado result, que contiene los valores devueltos
    result = parsed['result']
    # el result contiene una lista de diccionarios (clave/valor) para cada elemento devuelto
    for item in result:
        print ("".ljust(20,"="))
        for k,v in item.items():
            print (k,":",v) 
else:
    print(response)

print("Fin.")
'''