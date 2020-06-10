#!/bin/bash
echo "Detectando servidores:"
echo "======================"
echo "Sibila Web: 8080"
echo "Sibila OrientDB: 2480"
echo "Sibila SPACY: 5000"
echo "======================"
echo "Resultados"
echo "----------"
netstat -tulpn | grep '8080\|5000\|2480'
echo "=========================================="
echo "Direcciones IP de los contenedores Docker:"
echo "=========================================="
echo "OrientDB:"
docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' sibila-orientdb
echo "Spacy"
docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' sibila-spacy
echo "Java"
docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' sibila-java
