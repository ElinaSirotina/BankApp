# Определение базового образа
FROM openjdk:11-jre-slim

# Установка рабочей директории
WORKDIR /app

# Копирование JAR-файла приложения внутрь контейнера
COPY target/myapp.jar /app

# Определение переменной среды для указания порта
ENV PORT 8080

# Определение переменной среды для указания опций запуска JVM
ENV JAVA_OPTS "-Xmx512m"

# Определение команды для запуска приложения
CMD ["sh", "-c", "java $JAVA_OPTS -jar myapp.jar --server.port=$PORT"]