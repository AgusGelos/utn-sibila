# Practica Supervisada Grafos

Pasos levantar el proyecto

1.Instalar la última versión de nodeJS --> https://nodejs.org/es/ .

2.Descargar o clonar el proyecto.

3.En la carpeta angular-2-cytoscape donde estan los archivos de Angular, abrir con un CMD y ejecutar NPM INSTALL. Con esto se instalaran todas las dependencias necesarias.

4.En la carpeta Back-nodeJS abrir con un CMD y ejecutar NPM INSTALL. Con esto se instalaran todas las dependencias necesarias.

3.Levantar la base de datos orientDB -->carpeta bin-->server.bat

4.Levantar el server en nodeJS. En la carpeta Back-nodeJS abrir con un CMD, y ejecutar en la consola node index.js

5.Levantar el front-end. En la carpeta angular-2-cytoscape abrir con un CMD y ejecutar en la consola ng serve.

6.Abrir el navegador en htpp://localhost:4200


# Arquitectura 

#Contiene Una capa Web realizada en Angular, con dos componentes. Un componente contiene la integración de la líbreria Cytoscape.js
y otro con la funcionalidad para la interacción con el usuario.

#Un server con realizado con NodeJS y la librería express.js. Se conecta a la base de datos mediante OrientJS.

#Base de datos en OrientDB.

