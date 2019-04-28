# Coffee Word Search  
[![CircleCI](https://circleci.com/gh/acnagy/coffee-word-search.svg?style=svg&circle-token=7cc52e080d42dfd58a72c980b4cff2d1dea69bc8)](https://circleci.com/gh/acnagy/coffee-word-search)

a RESTful api to find a word in a file or a string.

a.k.a a demo app on [Spring Boot](https://spring.io) and [kubernetes (MiniKube)](https://kubernetes.io). 
Uses Spring Boot, Java 8, CircleCI, Gradle 5.0, Docker, Minikube and bring-your-own-cloud
Docker images are built on CircleCI, pushed to DockerHub, and deployed. 

Check out the [slides here](http://cs422-slides.geckoandginko.live).

## Getting Started 
 
Install all the docker/kubernetes dependencies
 - Install [Docker](https://docs.docker.com/install/)
 - Install a Hypervisor ([VirtualBox](https://www.virtualbox.org/wiki/Downloads))
 - Install [kubectl](https://kubernetes.io/docs/tasks/tools/install-kubectl/) and [minikube](https://kubernetes.io/docs/tasks/tools/install-minikube/)


## Writing the API
This application is super small. The class structure is as follows:

```
├── main
│   ├── java
│   │   ├── exceptions
│   │   │   └── ...
│   │   ├── search
│   │   │   └── ...
│   │   └── txt
│   │       └── ...
│   └── resources
│       ├── static
│       │   └── ...
│       └── templates
│           └── ...
└── test
    ├── java
    │   ├── search
    │   │   └── ...
    │   └── txt
    │       └── ...
    └── resources
```

The `search` package contains the `Search` api controller class(es) and entrypoint. The `txt` package is a package for business logic, e.g. building results returned by the controller. Test for both are mirrored in the `test` package tree. 

### Make a Change
Application changes should be written in Java 8. The intention behind the business logic packages is to allow for more rapid change without impacting the api controller. The `txt` directory is a good place to make changes. 

Before or during the change, add unit tests in the business logic package unit tests directory. New endpoints should get controller and integration tests as well. 

### Local Testing
(1) Test manually by running as a jar. the api will be available on [localhost:8080](http://localhost:8080/). Test plans consist of `curl`ing each of the endpoints, with appropriate data or parameters that will error (e.g. nonexistent files, missing param)

```
$ gradle clean build
$ java -jar coffee-word-search-0.3.0.jar
```

(2) Make sure the docker build works:

```
$ docker build -t owner/repo:tag .
```

if you run the docker container with:

```
$ docker run -p 8080:8080 owner/repo:tag
```
then the api will again be available on [localhost:8080](http://localhost:8080/).

(3) Deploy the docker container a local kubernetes cluster with Minikube

```
$ minikube start
$ kubectl create -f deployment.yaml # edit this file to match docker naming/tagging
$ kubectl create -f service.yaml    # edit this too
```

Then, get the ip of the cluster with `minikube ip`. the api will be availabe on `MINIKUBE_IP:30495`


### Build and Deploy
After making a change, run tests and build locally: 

```
$ gradle clean build
```
 
to make sure everything works as it should. Then, commit to a branch and push. 

CircleCI will automatically pick up the build, and following the `.circle/config.yaml` it will: 
 - run tests
 - build a jar
 - build a Docker image with that jar, tagging it with the version and build number
 - push the Docker image to [Docker Hub (acnagy/coffee-word-search)](https://cloud.docker.com/u/acnagy/repository/docker/acnagy/coffee-word-search) 


## Calls 

The API supports three endpoints. 

GET: `URL/api/`
params: none

```
$ curl -s URL/
{
  "term": null,
  "count": 1,
  "input": "Hi! Welcome to the app :)"
}

```

POST: `URL/api/string`
params:
  - term: string
  - string: string (to search)

```
$ curl -s -X POST \
-d term="something" \
-d string="something something else" \
URL/api/string | jq

{
  "term": "something",
  "count": 2,
  "input": "something something else"
}

```

POST: `URL/api/file`
params: 
 - term: string
 - file: file path

```
$ curl -s -X POST \
-F term="something" \
-F file="fileName" \
URL/api/file | jq

 {
   "term": "somethin",
   "count": 2,
   "input": "test-file3.txt"
 }
```

## Useful Resources
These docs and tutorials were pretty awesome and helpful. 
  - [spring.io - Getting Started - Building an Application with Spring Boot](https://spring.io/guides/gs/spring-boot/)
  - [spring.io - Getting Started - Uploading Files](https://spring.io/guides/gs/uploading-files/)
  - [github.com/IBM/spring-boot-microservices-on-kubernete](https://github.com/IBM/spring-boot-microservices-on-kubernetes)
  - [CI/CD Pipelines with Docker on CircleCI](https://circleci.com/blog/build-cicd-piplines-using-docker/)
  - [Gorilla Logic - Build and Deploy a Spring Boot App on Kubernetes (Minikube)](https://gorillalogic.com/blog/build-and-deploy-a-spring-boot-app-on-kubernetes-minikube/)
  - [Kubernetes.io Deployments](https://kubernetes.io/docs/concepts/workloads/controllers/deployment/)
  - [Kubernetes NodePort vs LoadBalancer vs Ingress?](https://medium.com/google-cloud/kubernetes-nodeport-vs-loadbalancer-vs-ingress-when-should-i-use-what-922f010849e0)
