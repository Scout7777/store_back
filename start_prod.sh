export SPRING_PROFILES_ACTIVE=prod

mvn package -B
# java -jar target/backend-1.0-SNAPSHOT-jar-with-dependencies.jar
java -jar web/target/web-1.0-SNAPSHOT.jar

