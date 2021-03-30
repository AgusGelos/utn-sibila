import logging
from dbmanager import DBManager
from entities.sibilaclasses import *
from knowledgemanager import KnowledgeManager 
from rich import print,inspect

def main():
    logging.basicConfig(level=logging.INFO)
    km = KnowledgeManager()
    
    try:
        
        c1 = Concepto(Nombre="PARADIGMA")
        conceptos = ['MODELO','DISEÑO','PROGRAMA','OBJETO']

        result = km.getPath(conceptoInicial=c1,conceptosIncluidos=conceptos)
        print (result)

        result = km.getPathsFrom(conceptoInicial=c1,profundidad=1)
        print (result)
        '''
        result = cm.getPathsByType(relacion=Relacion('EsUn'))
        print (result)
        '''
        # inspect(query)
    except Exception as e:
        inspect (e)

if __name__ == '__main__':
    main()