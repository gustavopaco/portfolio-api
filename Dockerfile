# Image: amazoncorretto:17
FROM amazoncorretto:17
# Informações sobre o mantenedor da imagem
LABEL authors="Gustavo Paco"
# Criando diretório de trabalho
WORKDIR /app
# Copiando o arquivo jar para o diretório de trabalho
COPY target/*.jar /app/portfolio-api.jar
# Expondo a porta 8080
EXPOSE 8080
# Executando o comando java -jar para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "/app/portfolio-api.jar"]

