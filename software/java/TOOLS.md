#### Documentacion de los fuentes

Si bien la documentación generada es JavaDoc se provee un proyecto de Doxygen.
La ventaja de Doxygen es que genera automáticamente los gráficos de clases, de herencia y de llamadas entre objetos.
La herramienta se puede bajar, de forma completa, en esta dirección:

    `http://www.stack.nl/~dimitri/doxygen/download.html`

Descargada e instalada la herramienta se debe abrir el archivo llamado Doxyfile dentro de la carpeta 
    
    \Doc 

Al ajecutar se generará toda la documentacion en la carpeta 
    
    \Doc\Source

> NO SE DEBE VERSIONAR LA DOCUMENTACION. SI SE DEBE VERSIONAR EL ARCHIVO PROYECTO DE DOCUMENTACION (DOXYFILE) SI TIENE ALGUN CAMBIO.

Para la generacion de los gráficos Doxygen requiere el software graphviz que se debe encontrar en la raiz del repositorio en la carpeta 

    \graphviz

Para evitar sobrecargar el control de versiones no se va a versionar (de hecho está en la lista de archivos a ignorar) pero se puede descargar desde la siguiente ubicacion:

    http://www.graphviz.org/Download_windows.php

#### Algunas librerías a Analizar

##### Guava: 
Librería de Google que aumenta la potencia de las librerías estandar de Java a la vez que simplifica su uso y agrega algunas funcionalidades de uso comun que no estan disponibles en el SDK estándar.

    https://github.com/google/guava/wiki

##### Gson:
Libreria para convertir a/de formato JSON. Es rápida y no requiere anotaciones en el codigo de los objetos que se desean convertir. Es muy usada internamente por Google.

    https://github.com/google/gson

##### Mockito:
Libreria para hacer mocking y pruebas unitarias. Segun la propia pagina es de uso muy simple y tiene multitud de alabanzas.

    https://github.com/mockito/mockito/wiki
    
    http://site.mockito.org/

#### Visualización

En esta sección se deben ir colocando librerías o informacion sobre la visualización de los grafos

##### Graphstream

Libreria de visualizacion con layout automático, implementada en Java.
Supuestamente es de fácil implementacion y tiene algoritmos de layout y reorganización.

    http://graphstream-project.org/

Articulo con demostraciones de como utilizar GraphStream

    https://ademirgabardo.wordpress.com/2016/02/19/302/

Documentacion y tutoriales

    http://graphstream-project.org/doc/

#### API REST

Herramientas para diseñar, construir y probar APIs REST

    http://apiworkbench.com/docs/

    http://raml.org/

