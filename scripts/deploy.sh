#!/bin/bash
set -e

# -----------------------------
# Deploy no Azure Container Instances (API + Postgres)
# -----------------------------


# Nomes dos recursos
RESOURCE_GROUP="rg-ibike"
ACR_NAME="acribike"
APP_NAME="aci-app-ibike"
DB_NAME="aci-db-ibike"
IMAGE_NAME="appibike"
IMAGE_TAG="latest"

echo "=== Obtendo login server do ACR ==="
ACR_LOGIN_SERVER=$(az acr show --name $ACR_NAME --query loginServer -o tsv)

echo "=== Criando container do Postgres ==="
az container create \
  --resource-group $RESOURCE_GROUP \
  --name $DB_NAME \
  --image postgres:15-alpine \
  --os-type Linux \
  --cpu 1 \
  --memory 1.5 \
  --ports 5432 \
  --ip-address public \
  --environment-variables POSTGRES_USER=postgres POSTGRES_PASSWORD=postgres POSTGRES_DB=appdb \
  --restart-policy Always

# Espera o container do Postgres inicializar
echo "=== Esperando o Postgres iniciar (30s) ==="
sleep 30

# Obtém o IP público do Postgres
DB_IP=$(az container show -g $RESOURCE_GROUP -n $DB_NAME --query ipAddress.ip -o tsv)
echo "=== IP público do Postgres: $DB_IP ==="

echo "=== Criando container da aplicação ==="
az container create \
  --resource-group $RESOURCE_GROUP \
  --name $APP_NAME \
  --image $ACR_LOGIN_SERVER/$IMAGE_NAME:$IMAGE_TAG \
  --os-type Linux \
  --cpu 1 \
  --memory 1.5 \
  --ports 8080 \
  --registry-login-server $ACR_LOGIN_SERVER \
  --registry-username $(az acr credential show --name $ACR_NAME --query "username" -o tsv) \
  --registry-password $(az acr credential show --name $ACR_NAME --query "passwords[0].value" -o tsv) \
  --environment-variables DB_HOST=$DB_IP DB_USER=postgres DB_PASSWORD=postgres DB_NAME=appdb \
  --restart-policy Always \
  --ip-address public

echo "=== Deploy concluído! ==="
echo "IP público da aplicação:"
az container show -g $RESOURCE_GROUP -n $APP_NAME --query ipAddress.ip -o tsv