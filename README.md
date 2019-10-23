PID4812
Proyecto de UTN - Reconocimiento de patrones sobre grafos

El proyecto es una segunda etapa del proyecto de corrección automatizada desarrollado entre los años 2015 a 2017.

Puesta en marcha
Generar .jar
Estando en software/java, ejecutar:

gradle build

Si no desea correr los tests, ejecutar

gradle build -x test

Si necesito correrlo actualizando las dependencias

gradle build --refresh-dependencies

Ejecutar servidor Java
Teniendo el .jar ejecutar java -jar path_to_jar

Configurar servidor de Python
Crear un entorno virtual e instalar dependencias
python -m virtualenv venv

Siendo venv el nombre del entorno virtual. Asegurar que el entorno se ejecuta en python 3. Si no se posee python3 corriendo por defecto, especificar la versión con el parámetro -p

Activar el entorno y luego instalar las dependencias con

pip install -r requirements.txt

Descargar diccionario para spacy
Ejemplo

python -m spacy download es_core_news_sm o python -m spacy download es_core_news_md

Tener en cuenta que para usar uno u otro diccionario se debe especificar en spacy_server/services/manejador_spacy. Por defecto se utiliza es_core_news_sm . Tener en cuenta adems que el tiempo de ejecución varía en función del tamaño del diccionario que se utiliza.

Ejecutar servidor de python
Con el entorno activado estando en /spacy_server/ ejecutar: python app.py

Además tener en cuenta que la base de datos debe estar corriendo, de lo contrario no funcionará.