import requests
from requests.auth import HTTPBasicAuth
import json

endpoint = "http://sibila.website:2480"
method_query = "/query/PPR/sql/"
method_connect = "/connect/PPR"
query = "select from Concepto"
query_where = "select from Concepto where Nombre = 'PARADIGMA'"
query_match = "match {class: Concepto, as c}"

req_query = endpoint+method_query+query
req_conn = endpoint+method_connect

# Primero llamar al connect, para autenticar
#response = requests.get(req_conn, auth=HTTPBasicAuth('admin', 'admin'))
#print (response, response.content)

response = requests.get(req_query, auth=HTTPBasicAuth('admin', 'admin'))
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
