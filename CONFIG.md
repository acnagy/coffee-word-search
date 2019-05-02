# Config
This document provides a light overview of the configuration files in this project

`build.gradle` - automates testing and creation of a runnable `jar` 
`.circleci/config.yml` - defines automated continuous integration build which runs tests, builds the jar, builds a candidate docker container, and pushes the docker container to [Docker Hub](https://hub.docker.com/)
`deployment.yml` - specifies the kubernetes deployment for the application. Includes details such as the port that should be open on the container and the number of times the container is replicated in a pod
`service.yml` - specifies a service that exposes inbound traffic on a load balancer
`Dockerfile` - defines how to build the container image for this application.