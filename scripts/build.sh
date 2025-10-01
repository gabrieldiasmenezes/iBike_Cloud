#!/bin/bash
set -e

# -----------------------------
# Build e push da imagem Docker (via ACR Tasks)
# -----------------------------



# Nomes dos recursos
RESOURCE_GROUP="rg-ibike"
ACR_NAME="acribike"
IMAGE_NAME="appibike"
IMAGE_TAG="latest"

echo "=== Criando grupo de recursos (se não existir) ==="
az group create --name $RESOURCE_GROUP --location brazilsouth

echo "=== Criando Azure Container Registry (se não existir) ==="
az acr create \
  --resource-group $RESOURCE_GROUP \
  --name $ACR_NAME \
  --sku Basic \
  --admin-enabled true \
  --only-show-errors || true

echo "=== Buildando a imagem direto no ACR (ACR Tasks) ==="
az acr build \
  --registry $ACR_NAME \
  --image $IMAGE_NAME:$IMAGE_TAG \
  .

echo "=== Build e push concluídos! Imagem disponível em: $ACR_NAME.azurecr.io/$IMAGE_NAME:$IMAGE_TAG ==="