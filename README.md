# PID4812

Proyecto de UTN - Reconocimiento de patrones sobre grafos

El proyecto es una segunda etapa del proyecto de corrección automatizada desarrollado entre los años 2015 a 2017.


## Puesta en marcha

#### Generar .jar

Estando en software/java, ejecutar:

`gradle build`

Si no desea correr los tests, ejecutar

`gradle build -x test`

Si necesito correrlo actualizando las dependencias

`gradle build --refresh-dependencies`


#### Configurar servidor de Python

Crear un entorno virtual de python. Por ejemplo, haciendo

`python -m virtualenv venv`

Siendo _venv_ el nombre del entorno virtual.
Asegurar que el entorno se ejecuta en **python 3**. Si no se posee python3 corriendo por defecto, especificar la versión con el parámetro _-p_

Activar el entorno y luego instalar las dependencias con

`pip install -r requirements.txt`
