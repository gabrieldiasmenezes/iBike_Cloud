#!/bin/bash

# Atualizar pacotes
echo "Atualizando pacotes..."
sudo apt update -y

# Instalar Docker
echo "Instalando Docker..."
sudo apt install -y apt-transport-https ca-certificates curl software-properties-common
curl -fsSL https://download.docker.com/linux/ubuntu/gpg  | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu  $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
sudo apt update -y
sudo apt install -y docker-ce

# Adicionar usuário ao grupo Docker
echo "Configurando permissões..."
sudo usermod -aG docker $USER

echo "Docker instalado com sucesso!"
echo "Reinicie a sessão SSH para aplicar as mudanças."