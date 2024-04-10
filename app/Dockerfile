FROM maven:3.8-openjdk-17

LABEL authors="Etienne Binginot, Axelle Mazuy"

WORKDIR /app

# Install dependencies
COPY pom.xml /app
RUN mvn clean dependency:go-offline

# Get project files
COPY . /app

# Run springboot
CMD mvn spring-boot:run