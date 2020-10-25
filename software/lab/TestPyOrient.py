'''
El driver de PyOrient se debe instalar de la siguiente manera:

crear un archivo requirements.txt con el siguiente contenido
-e git+https://github.com/orientechnologies/pyorient.git@master#egg=pyorient

desde una consola instalar con pip ejecutando

pip install -r requeriments.txt

Una vez hecho esto se puede utilizar el driver actualizado que se encuentra en el repo de OrientDB
'''

import pyorient
from pyorient.ogm import Graph, Config
import json
import networkx as nx
from networkx import adjacency_graph
import matplotlib.pyplot as plt

# Establecer una conexion con el servidor en el puerto 2424 (BINARIO, la conexion a 2480 es para API Rest)
client = pyorient.OrientDB("sibila.website",2424)

# Abrir la base de datos PPR con usuario=admin y pass=admin
db = client.db_open(
    "PPR", "admin", "admin"
)

print ("CONECTADO!")

# Ejecutar un query a la Clase (similar a tabla) Concepto
res = client.query("select from Concepto")

# Investigar bien qué viene en res, todavia en investigación
for resultado in res:
    print (resultado.oRecordData, type(resultado.oRecordData))
    print (resultado._class,"\n")
    for attr in dir(resultado): 
        if callable(getattr(resultado, attr)) :
            print(attr +'()') 
        else: 
            print(attr)

'''
graph = Graph(
    Config.from_url(
    "http://sibila.website:2424/PPR",
    "admin",
    "admin"
    )
)
'''

plt.figure(figsize=(15,15))

# Establecer una conexion con el servidor en el puerto 2424 (BINARIO, la conexion a 2480 es para API Rest)
client = pyorient.OrientDB("sibila.website",2424)

# Abrir la base de datos PPR con usuario=admin y pass=admin
db = client.db_open(
    "PPR", "admin", "admin"
)

print ("CONECTADO!")


#NOMBRE Y FILTRO A USAR
nombre = "'LENGUAJE FORMAL'"
filtro="out_Utiliza"

#BUSQUEDA DE LOS DATOS EN LA BD
nom_querry = client.command("SELECT FROM Concepto WHERE Nombre = {}".format(nombre))
for i in nom_querry:
    id_buscar = (i._rid)
centro = client.command("SELECT FROM Concepto WHERE @rid = {}".format(id_buscar))

exterior = client.command("select from (traverse both() from (select from Concepto where Nombre = {}) while $depth <= {})".format(nombre,3))
#exterior = client.command("SELECT FROM Concepto WHERE @rid IN (SELECT out FROM (SELECT expand({}) FROM Concepto) WHERE in = {})".format(filtro,id_buscar))

#CREACION DEL GRAFO
G = nx.Graph()
for i in centro:
    x = G.add_node(i.Nombre)
    nombre = i.Nombre
    
edge_labels={}
pos=0
for i in exterior:
    G.add_node(i.Nombre)
    G.add_edge(nombre,i.Nombre)
    edge_labels[pos] = (filtro)
    pos+=1
    

#DIBUJO DEL GRAFO
pos = nx.spring_layout(G)
nx.draw(G,pos,edge_color='black',width=1,linewidths=1,node_size=1000,node_color='red',alpha=0.9,labels={node:node for node in G.nodes()},font_size=16)
edge_labels=dict([((u,v,),filtro)
for u,v,d in G.edges(data=True)])

nx.draw_networkx_edge_labels(G,pos,edge_labels=edge_labels,font_color='blue',font_size=12)

plt.axis('off')
plt.show()

'''
CODIGO JAVA QUE ARMA LOS QUERYS PARA GRAFICAR

'''