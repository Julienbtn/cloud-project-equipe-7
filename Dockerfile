FROM frolvlad/alpine-oraclejdk8:slim
ADD target/user-api-0.0.1-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
<<<<<<< HEAD
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","-Dspring.profiles.active=prod","/app.jar"]
=======
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]bash-3.2$
>>>>>>> bcfe7687cbdfde058df8fc2ac0b9479ca04e7c65
