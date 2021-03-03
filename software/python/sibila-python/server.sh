#!/bin/sh
echo "Iniciando servidor SIBILA"

uvicorn sibila-server:app --reload 