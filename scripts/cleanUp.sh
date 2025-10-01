#!/bin/bash
# =================================================
# Limpeza de todos os recursos do Resource Group
# =================================================

RESOURCE_GROUP="rg-ibike"

echo "=== Excluindo Resource Group $RESOURCE_GROUP e todos os recursos dentro dele ==="
az group delete --name $RESOURCE_GROUP --yes --no-wait

echo "=== Grupo de recursos esxcluidos com sucesso ==="
