FROM centos:7
RUN yum install -y java-1.8.0-openjdk-devel maven
COPY . /altinn
COPY conf/certfile.p12 /altinn/src/main/resources/certs/
COPY conf/application.properties /altinn/src/main/resources/config/
WORKDIR /altinn
RUN mvn clean install
WORKDIR /altinn/target
EXPOSE 54869
CMD ["./altinn-broker-service-toolkit-0.8.war"]
