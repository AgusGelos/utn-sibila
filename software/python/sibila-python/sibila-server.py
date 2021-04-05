'''
Server Web que implementará la API de Sibila.
Por el momento es simplemente un sandbox para hacer algunas
pruebas de las librerías de Python y otros conceptos.
'''
from entities.graphclasses import Concepto
import logging
from rich import print,inspect
from rich.logging import RichHandler
from managers.knowledgemanager import KnowledgeManager
from fastapi import FastAPI, Request

FORMAT = "%(message)s"
#logging.basicConfig(level=logging.INFO,handlers=[RichHandler()])
logging.basicConfig(
    level="NOTSET", format=FORMAT, datefmt="[%X]", handlers=[RichHandler()]
)

km = KnowledgeManager()
app = FastAPI()

'''
Los VERBOS (prefijos de los métodos) van a ser:
INS: Insertar un nuevo registro
DEL: Eliminar un registro existente
UPD: Modificar un registro existente
GET: Obtener información
'''

@app.get("/")
async def read_root():
    return {"Sibila Server": "Servidor Web Sibila (FastAPI/Python)"}


@app.get("/conceptos/")
async def getConceptos():
    return km.getConceptos()

@app.post("/concepto/")
async def insConcepto(concepto : Concepto):
    result,id = km.insConcepto(concepto)
    print (result)
    return result

@app.put("/concepto/")
async def updConcepto(concepto : Concepto):
    oldConcepto = Concepto(Nombre=concepto.Nombre)
    result = km.updConcepto(oldConcepto,concepto)
    print (result)

@app.delete("/concepto/")
async def delConcepto(concepto : Concepto):
    result = km.delConcepto(concepto)
    print (result)


@app.get("/relaciones/")
async def get_relaciones(request : Request):
    return km.getRelaciones()

    