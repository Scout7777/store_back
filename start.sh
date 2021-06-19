export HISTSYS_DB_URL="jdbc:postgresql://localhost:5433/csys"
export HISTSYS_DB_USERNAME="neo"
export HISTSYS_DB_PASSWORD=""

mvn package -B
java -jar target/backend-1.0-SNAPSHOT-jar-with-dependencies.jar
