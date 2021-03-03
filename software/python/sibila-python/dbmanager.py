from typing import Dict
import requests
from urllib import parse
from requests import auth
from requests.auth import HTTPBasicAuth
import json
from entities.sibilaclasses import *
import logging

class DBManager:
    host = None
    port = None
    database = None
    user = None
    password = None
    # URL's
    baseURL = None
    getURL = None
    batchURL = None
    documentURL = None
    commandURL = None
    
    def __init__ (self, host='http://localhost', port=2480, database='PPR', user='admin', password='admin'):
        self.host = host
        self.port = port
        self.database = database
        self.user = user
        self.password = password
        
        self.baseURL = host+":"+str(port)
        self.getURL = parse.urljoin(self.baseURL,"/query/"+self.database+"/sql/") 
        self.batchURL = parse.urljoin(self.baseURL,"/batch/"+self.database)
        self.documentURL = parse.urljoin(self.baseURL,"/document/"+self.database)
        self.commandURL = parse.urljoin(self.baseURL,"/command/"+self.database+"/sql")

    def __getWhereFromDict__ (self, keys : Dict):
        condition = ""
        condition = parse.urlencode(keys)
        condition = condition.replace ("&"," AND ").replace("%27","'").replace("+"," ")
        return condition

    def __getFieldsFromDict__ (self, fields : Dict):
        flds = ""
        flds = parse.urlencode(fields)
        flds = flds.replace ("&",",").replace("%27","'").replace("+"," ")
        return flds

    def extractResult (self, response: requests.Response):
        parsed = json.loads(response.content.decode("utf-8"))
        # el response tiene un elemento llamado result, que contiene los valores devueltos
        result = parsed['result']
        return result

    def extractId (self, result: str):
        json_res = result #json.loads(result)
        id = json_res[0]["@rid"]
        return id

    def execQuery (self,query : str) -> str:
        """Ejecuta la consulta pasada por parámetro y devuelve los datos en formato JSON.
        Args:
            query (string): Query a ejecutar
        Returns:
            string: JSON con los datos devueltos
        """    
        req_query = parse.urljoin(self.getURL,query)
        response = requests.get(req_query, auth=HTTPBasicAuth(self.user, self.password))
        if (response.ok):
            # Convertir la cadena de bytes en un string, con encode utf-8
            parsed = json.loads(response.content.decode("utf-8"))
            # el response tiene un elemento llamado result, que contiene los valores devueltos
            result = parsed['result']
        else:
            result = None
        return result
    
    def execCommand (self,command:str):
        payload = {"command" : command}
        logging.info ("{url} - {method} - {payload}".format(url=self.commandURL,method="DEL",payload=payload))
        response = requests.post(self.commandURL,json=payload,auth=HTTPBasicAuth(self.user,self.password))
        if (not response.ok):
            logging.error (response.status_code, response.reason, response.text)
            return None
        else:
            return self.extractResult(response)

    def getVertex (self, classname : str, keys : Dict):
        condition = self.__getWhereFromDict__(keys)
        query = "MATCH {{class: {classname}, as: r, where: ({condition})}} return expand(r)".format(classname=classname,condition=condition)
        req_query = parse.urljoin(self.getURL,query)
        response = requests.get(req_query, auth=HTTPBasicAuth(self.user, self.password))
        if (not response.ok):
            logging.error (response.status_code, response.reason, response.text)
            return None
        else:
            return self.extractResult(response)

    def insVertex (self, classname : str, fields : Dict):
        flds = self.__getFieldsFromDict__(fields)
        command = "CREATE VERTEX {classname} SET {fields}".format(classname=classname,fields=flds)
        return self.execCommand(command)

    def delVertex (self, classname : str, keys : Dict):
        # Buscar los registro de la clase que cumplan con las claves
        condition = self.__getWhereFromDict__(keys)
        command = "DELETE VERTEX {classname} WHERE {condition}".format(classname=classname,condition=condition)
        return self.execCommand(command)

    def updVertex (self, classname : str, keys : Dict, fields : Dict):
        flds = self.__getFieldsFromDict__(fields)
        where = self.__getWhereFromDict__(keys)
        command = "UPDATE {classname} SET {fields} WHERE {condition}".format(classname=classname,fields=flds,condition=where)
        return self.execCommand(command)

    def execBatch (self,execScript : str):
        script = """BEGIN;
        {execScript}
        COMMIT;""".format(execScript = execScript)
        operaciones = [{"type":"script","language":"sql","script":[script]}]
        data = {"transaction":True,"operations":operaciones}
        response = requests.post(self.batchURL,json=data,auth=HTTPBasicAuth(self.user, self.password))
        return response

    def insEdge (self, sourceClass : str, sourceCondition : Dict, destClass : str, destCondition : Dict, edgeClass : str):
        outCondition = self.__getWhereFromDict__(sourceCondition)
        inCondition = self.__getWhereFromDict__(destCondition)
        command = "CREATE EDGE {edgeClass} FROM (SELECT FROM {sourceClass} WHERE {sourceCondition}) TO (SELECT FROM {destClass} WHERE {destCondition})".format(
            edgeClass=edgeClass,sourceClass=sourceClass,sourceCondition=outCondition,destClass=destClass,destCondition=inCondition
        )
        return self.execCommand(command)

    def delEdge (self, sourceClass : str, sourceCondition : Dict, destClass : str, destCondition : Dict, edgeClass : str):
        outCondition = self.__getWhereFromDict__(sourceCondition)
        inCondition = self.__getWhereFromDict__(destCondition)
        command = "DELETE EDGE {edgeClass} FROM (SELECT FROM {sourceClass} WHERE {sourceCondition}) TO (SELECT FROM {destClass} WHERE {destCondition})".format(
            edgeClass=edgeClass,sourceClass=sourceClass,sourceCondition=outCondition,destClass=destClass,destCondition=inCondition
        )
        return self.execCommand(command)
