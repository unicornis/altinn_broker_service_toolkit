# Altinn Broker Service Toolkit
A Java-based microservice for communicating messages to Altinn's broker service.

Requires Maven and Java JDK 1.8.0.

# Setup
The simplest way to set this up is by compiling an image for Docker.

1. Create a new directory `conf` and add your P12 certificate to that directory as `certfile.p12`
2. Copy `src/main/resources/config/application-sample.properties` to `conf/` and edit the file with your own credentials
3. Run `docker build .`

# About
Developed by Unicornis AS for use in the membership management system HyperSys and for use as reference code by other integrators.

This code is part of a simplification project with The Norwegian Register Centre ("Brønnøysundregistra"). The project goal was to create specifications and open up the central registry for direct submissions from membership management systems used by non-profit organisations (NGOs) to report changes of the board and other central information directly without use of normal registry submission channels like Altinn.

Note that the code is to be used as a reference for developers to understand the use of APIs, and not intended to be directly used in production systems.
