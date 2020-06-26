#!/bin/bash
# declare STRING variable
STRING="Iniciando Docker Container (Spacy)"
#print variable on a screen
echo $STRING
echo "Iniciando el servidor SPACY de Sibila"
docker run -d --rm -p 5000:5000 --hostname sibila-spacy --name sibila-spacy mcasatti/sibila-spacy 
echo "Obteniendo la IP asignada a SPACY"
sleep 3s
docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' sibila-spacy

