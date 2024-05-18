# Use the official OpenJDK image as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

COPY . .

# Copy the packaged JAR file into the container at /app
COPY target/bim-application-0.0.1-SNAPSHOT.jar /app

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the JAR file when the container launches
CMD ["java", "-jar", "bim-application-0.0.1-SNAPSHOT.jar"]
