From openjdk:8
copy ./target/stock-0.0.1-SNAPSHOT.jar stock-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","stock-0.0.1-SNAPSHOT.jar"]
EXPOSE 8010