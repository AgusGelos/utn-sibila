import logging
from dbmanager import DBManager
from entities.sibilaclasses import *

def main():
    logging.basicConfig(level=logging.INFO)
    db = DBManager()
    
    '''
    concepto = Concepto('Internet')
    r = db.insVertex(clase="Concepto",campos=concepto.__dict__)
    print (r)
    
    r = db.getVertex(classname="Concepto",keys={"Nombre" : "'OBJETO'"})
    print (r)
    id = db.extractId(r)
    print (id)
    '''

    #r = db.insVertex(classname="Concepto", fields={"Nombre" : "'IoT'"})
    #print (r)
    #r = db.delEntity("Concepto",{"Nombre":"'IoT'"})
    #print (r)
    #r = db.updVertex("Concepto",{"Nombre":"'Internet'"},{"Vista":"'La Web'"})
    #print (r)

    r = db.insEdge(
        sourceClass="Concepto",
        sourceCondition={"Nombre":"'IoT'"},
        destClass="Concepto",
        destCondition={"Nombre":"'Internet'"},
        edgeClass="E"
        )
    print (r)

    r = db.delEdge(
        sourceClass="Concepto",
        sourceCondition={"Nombre":"'IoT'"},
        destClass="Concepto",
        destCondition={"Nombre":"'Internet'"},
        edgeClass="E"
        )
    print (r)

if __name__ == '__main__':
    main()