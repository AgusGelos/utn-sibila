#!/bin/bash
# declare STRING variable
STRING="Iniciando Docker Container (Sibila Java)"
#print variable on a screen
echo $STRING
echo "Iniciando el servidor Java de Sibila"
docker run -d --rm -p 8080:8080 --hostname sibila-java --name sibila-java mcasatti/sibila-java 
echo "Obteniendo la IP asignada a SIBILA JAVA"
sleep 3s
docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' sibila-java

