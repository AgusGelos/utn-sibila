Configuración de OrientDB 
=

Los siguientes pasos son necesarios para instalar y configurar OrientDB para su uso con éste u otros proyectos.

OrientDB se debe descargar desde el siguiente enlace (la versión Community)

https://orientdb.com/download-2/

El archivo descargado se descomprime en cualquier carpeta y se deben realizar las siguientes modificaciones en los 
archivos de configuración para que el servidor arranque en cualquier equipo Windows. 

#### Archivo /config/orientdb-server-config.xml ####

Este archivo almacena configuraciones globales del servidor. Se deben agregar los usuarios correspondientes para que se
permita la conexión al servidor. 

Se debe buscar la sección 

    <users>

del servidor y editarla de forma tal que quede el siguiente contenido:

	<users>
        <user resources="*" password="root" name="root"/>
        <user resources="connect,server.listDatabases,server.dblist" password="guest" name="guest"/>
	    <user resources="*" password="admin" name="admin"/>
    </users>    

esto configura 3 usuarios:

* **root**: con password **root**. Derechos completos
* **guest**: con password **guest**. Derechos de conexión y de consulta de bases de datos
* **admin**: con password **admin**. Derechos iguales a root. En producción estos derechos se reducirán pero sin llegar al 
límite de **guest**.

#### Archivo /bin/server.bat ####

Se debe modificar el archivo que inicia el servidor para que no intente reservar tanta memoria al arranque (solicita 2GB exclusivos)

Para eso se debe bucar en este archiv la linea

    set MAXHEAP=-Xms2G -Xmx2G
    
y reemplazarla por 

    set MAXHEAP=-Xms512m -Xmx512m
    
#### Corrección de versión Server de Java ####

En algunos casos es probable que al intentar arrancar el servidor se informe un mensaje de error informando:

    Error: missing `server' JVM at `C:\Program Files\Java\jre1.8.0_171\bin\server\jvm.dll'.
    Please install or use the JRE or JDK that contains these missing components.
    
Para corregir este error se debe buscar la carpeta `client` de Java y copiar el archivo `jvm.dll` que se encuentra en la carpeta `client` de Java a la carpeta `server`.

Una vez copiado el archivo el inicio del servidor no debería dar más problema.

#### Backup y Restores

El servidor de base de datos no se versionará, pero si se hara lo propio con los backups de la base de datos.

Los backups se generarán cuando haya cambios sustanciales en los datos o cuando haya cambios en las estructuras de trabajo (y los mismos sean estables)

A continuación se darán las indicaciones para realizar las operaciones de `BACKUP` y de `RESTORE` correspondientes.

##### CONEXION OFFLINE

Los backups (y los restores) se deben realizar en `modalidad offline`, por lo que el servidor debe estar apagado.

Para conectarse al servidor en esta modalidad se debe ejecutar (desde la consola "console.bat"):

    connect plocal:../databases/PPR admin admin
    
Esto permite conectarse a la base de datos de modo exclusivo y realizar las operaciones que se detallan a continuación.

##### BACKUP

Estando conectados en modalidad `offline` se procede a ejecutar (desde la misma consola) el siguiente comando:

    orientdb {db=PPR}> backup database C:/path/archivo.zip
    
El comando genera un archivo .zip con todo el contenido de la base de datos.

##### RESTORE

Estando conectados en modalidad `offline` se procede a ejecutar (desde la misma consola) el siguiente comando:

    orientdb {db=PPR}> restore database C:/path/archivo.zip
    
El comando lee el archivo de backup generado en el paso anterior y reemplaza todas las estructuras y datos de la base 
de datos seleccionada.
