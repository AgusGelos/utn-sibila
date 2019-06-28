
from flask import Flask, request, json

from services.ortografia import Ortografia
from services.manejador_spacy import ManejadorSpacy

app = Flask(__name__)

@app.route('/')
def hello_world():
    return "hola"

@app.route('/correccion', methods=['POST'])
def correccion():
    # texto = request.args.get("txt", "")

    try:
        data = request.json

        texto = data.get('txt')
        ortografia = Ortografia()
        correcciones, error = ortografia.corregir(texto)

        codigo_respuesta_http = "200"
        estado = "ok" if not error else "error"
        mensaje = ""

        data = {
            "estado": estado,
            "mensaje": mensaje,
            "datos": correcciones
        }
        response = app.response_class(
            response=json.dumps(data),
            status=codigo_respuesta_http,
            mimetype="application/json"
        )
        return response
    except Exception as e:
        print(e)

@app.route('/tokenizacion', methods=['POST'])
def tokenize():
    data = request.json

    texto = data.get('txt')
    tokens = ManejadorSpacy.analizar_texto(texto)
    datos_texto = {
        "tokens": tokens,
        "original": texto
    }
    data = {
        "estado": "ok",
        "mensaje": "",
        "datos": datos_texto
    }
    response = app.response_class(
        response=json.dumps(data),
        status="200",
        mimetype="application/json"
    )
    return response



if __name__ == '__main__':
    app.run()
