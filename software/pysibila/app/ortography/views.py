import requests

from app.ortography.models import Answer
from config.urls import URL


def answer_correct(answer):
    request = {
        "respuesta": answer,
    }
    response = requests.post(f'{URL}/respuesta/corregir', json=request)

    return response.json()