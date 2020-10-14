FROM openjdk:8

ADD build/libs/sudoku-0.0.1-SNAPSHOT.jar sudoku.jar

ENTRYPOINT ["java", "-jar", "sudoku.jar"]

EXPOSE 8080