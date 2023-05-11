FROM openjdk:11-jre-slim

WORKDIR /app

COPY target/myapp.jar /app

ENV PORT 8080

ENV JAVA_OPTS "-Xmx512m"

CMD ["sh", "-c", "java $JAVA_OPTS -jar myapp.jar --server.port=$PORT"]