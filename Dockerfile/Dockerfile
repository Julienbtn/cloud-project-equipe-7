FROM alpine:edge
# Noyau Linux

# Ajout de java 8
RUN apk add --no-cache openjdk8

# Ajout mongodb
RUN apk add --no-cache mongodb

# On peut ouvrir un bash dans notre conteneur
RUN apk add --no-cache bash

# On crée le volume pour notre base de données
VOLUME ["/data/db"]

# Au démarrage, le conteneur exécute mongo
CMD [ "mongod" ]



# Commande pour créer l'image
# docker build -t img_mongo_java --no-cache .

# Commande pour lancer l'image
# docker run -d -p 27017:27017 --name cont_mongo_java img_mongo_java
