#
# Build stage
#
FROM maven:3.8.4-openjdk-17 as build
WORKDIR /usr/src/app
COPY . .
RUN mvn install

#
# Package stage
#
FROM openjdk:17
COPY --from=build /usr/src/app/target/*.war /seafood-wholesaler-oms-1.0.0.war
ENTRYPOINT ["java","-jar","/seafood-wholesaler-oms-1.0.0.war"]

#
# Local development's command
#

# Build image
# docker build -t hello-world:1.0 .

# Runs the container and expose 3306 outside the container
# docker run --platform linux/amd64 -d --name your_container_name -p 3306:3306 your_image_name