import hunspell
from settings import BASE_DIR


class Ortografia:

    def __init__(self):
        ruta_recursos = BASE_DIR + "/res/hunspell-es/"
        print(ruta_recursos)
        #self.dic = hunspell.Hunspell(ruta_recursos + "es_ANY.dic", ruta_recursos + "es_ANY.aff")
        self.dic = hunspell.Hunspell(ruta_recursos + "es_ANY")

    def corregir(self, texto):

        error = False
        correcciones = []
        palabras = texto.split()

        for palabra in palabras:

            correcta = self.dic.spell(palabra)
            sugerencias = self.dic.suggest(palabra) if not correcta else []

            correcciones.append({
                "original": palabra,
                "error": not correcta,
                "sugerencias": sugerencias
            })

            if not correcta:
                error = True

        return correcciones, error
