import logging
from dbmanager import DBManager
from entities.sibilaclasses import *
from conceptmanager import ConceptManager 
from rich import print,inspect

def main():
    logging.basicConfig(level=logging.INFO)
    cm = ConceptManager()
    
    try:
        ##result = cm.getConceptosComplejos()
        c1 = Concepto(nombre="PARADIGMA")
        c2 = Concepto(nombre="POO")
        r = Relacion(classname="Coso")
        result = cm.existStruct(conceptoOrigen=c1,relacion=r,conceptoDestino=c2)
        inspect(result)
    except Exception as e:
        inspect (e)

if __name__ == '__main__':
    main()