import requests
import json

# Enviamos la respuesta a corregir
# sibila.website:8080/respuesta/corregir'
respuesta = 'para evaluar expresiones aritmeticas y funcionales'
response = requests.post(
    'http://sibila.website:8080/respuesta/corregir', 
    json = {'respuesta':respuesta}
    )
# Tomamos los terminos
# todo ver porque el campo de terminos esta vacio
# terminos = rqst.json()['datos']['terminos']
# Ver resultados del json
#print(rqst)
print (response.content)
#if (response.ok):
    # Convertir la cadena de bytes en un string, con encode utf-8
parsed = json.loads(response.content.decode("utf-8"))

print (response, json.dumps(parsed,indent=3))
