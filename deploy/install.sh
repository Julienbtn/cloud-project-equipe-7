#!/usr/bin/env bash
# Install docker

sudo apt-get remove docker docker-engine docker.io
sudo apt-get update
sudo apt-get install \
    linux-image-extra-$(uname -r) \
    linux-image-extra-virtual

sudo apt-get update
sudo apt-get install \
    apt-transport-https \
    ca-certificates \
    curl \
    software-properties-common

curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
sudo apt-key fingerprint 0EBFCD88
sudo add-apt-repository \
   "deb [arch=amd64] https://download.docker.com/linux/ubuntu \
   $(lsb_release -cs) \
   stable"

sudo apt-get update
sudo apt-get install docker-ce

# Install docker compose
sudo curl -o /usr/local/bin/docker-compose -L "https://github.com/docker/compose/releases/download/1.15.0/docker-compose-$(uname -s)-$(uname -m)"
sudo chmod +x /usr/local/bin/docker-compose
docker-compose -v

# Download the docker-compose.yml
sudo rm -f docker-compose.yml
wget https://raw.githubusercontent.com/PolytechLyon/cloud-project-equipe-7/feature/docker/deploy/docker-compose.yml

# Delete previous containers and images
docker rm -f mongodb
docker rm -f cloud-java

docker rmi julienbtn/cloud-java

docker-compose up -d

echo Waiting for the application to start...
sleep 15  # Waits 5 seconds.

HTTP_STATUS="$(curl -IL --silent http://localhost:80/user | grep HTTP )";
echo "${HTTP_STATUS}";