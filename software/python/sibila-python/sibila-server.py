'''
Server Web que implementará la API de Sibila.
Por el momento es simplemente un sandbox para hacer algunas
pruebas de las librerías de Python y otros conceptos.
'''

from typing import Optional
from fastapi import FastAPI

app = FastAPI()


@app.get("/")
async def read_root():
    return {"Hello": "World"}


@app.get("/items/{item_id}")
async def read_item(item_id: int, q: Optional[str] = None):
    return {"item_id": item_id, "q": q}

