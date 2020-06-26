#!/bin/bash
# declare STRING variable
STRING="Iniciando Docker Container (OrientDB)"
#print variable on a screen
echo $STRING
echo "Iniciando el servidor ORIENTDB de Sibila"
docker run -e ORIENTDB_OPTS_MEMORY="-Xmx512m" -d --rm -p 2480:2480 -p 2424:2424 --hostname sibila-orientdb --name sibila-orientdb mcasatti/sibila-orientdb 
echo "Obteniendo la IP asignada a ORIENTDB"
sleep 3s
docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' sibila-orientdb

