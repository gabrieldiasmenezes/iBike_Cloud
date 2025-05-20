#!/bin/bash

# Variáveis
RESOURCE_GROUP="rg-ibike"
VM_NAME="vm-ibike"
LOCATION="brazilsouth"
ADMIN_USER="azureuser"

# Criar Grupo de Recursos
echo "Criando grupo de recursos..."
az group create --name $RESOURCE_GROUP --location $LOCATION

# Criar Máquina Virtual
echo "Provisionando a VM..."
az vm create \
  --resource-group $RESOURCE_GROUP \
  --name $VM_NAME \
  --image Ubuntu2204 \
  --admin-username $ADMIN_USER \
  --generate-ssh-keys

# Abrir Porta 8080
echo "Abrindo porta 8080..."
az vm open-port --port 8080 --resource-group $RESOURCE_GROUP --name $VM_NAME

# Mostrar IP Público da VM
echo "Obtendo IP público da VM..."
IP_PUBLICO=$(az vm list-ip-addresses --resource-group $RESOURCE_GROUP --name $VM_NAME --query "[].virtualMachine.network.publicIpAddresses[0].ipAddress" -o tsv)
echo "VM criada com sucesso!"
echo "Conecte-se à VM via SSH usando:"
echo "ssh $ADMIN_USER@$IP_PUBLICO"