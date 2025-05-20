#!/bin/bash

# Variáveis
RESOURCE_GROUP="rg-iBike"

# Deletar Grupo de Recursos
echo "Deletando grupo de recursos e todos os recursos associados..."
az group delete --name $RESOURCE_GROUP --yes --no-wait

echo "Recursos deletados com sucesso!"