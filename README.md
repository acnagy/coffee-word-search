# Coffee Word Search  
[![CircleCI](https://circleci.com/gh/acnagy/coffee-word-search.svg?style=svg&circle-token=7cc52e080d42dfd58a72c980b4cff2d1dea69bc8)](https://circleci.com/gh/acnagy/coffee-word-search)

a RESTful api to find a word in a file or a string. Fancy-sauce hello world, amiright? 

a.k.a a demo app on [Spring Boot](https://spring.io) and [kubernetes (MiniKube)](https://kubernetes.io). 
Uses Spring Boot, Java 8, CircleCI, Gradle 5.0, Docker, Kubernetes and bring-your-own-cloud. 
Docker images are built in CI, pulled/tagged for deployment.

Check out the [slides here](http://slides.cs422.geckoandginko.live).

## Getting Started 
 
Install all the dependencies
 - Install [Java8](https://www.oracle.com/technetwork/java/javase/overview/java8-2100321.html) + [Gradle](https://gradle.org/install/)
 - Install [Docker](https://docs.docker.com/install/) + [kubectl](https://kubernetes.io/docs/tasks/tools/install-kubectl/)+ [kops](https://kubernetes.io/docs/setup/custom-cloud/kops/)) + [minikube](https://kubernetes.io/docs/tasks/tools/install-minikube/) and a hypervisor like ([VirtualBox](https://www.virtualbox.org/wiki/Downloads)) for local development

## Documentation

Read up on the design of this project in the [CLASSES.md](/CLASSES.md) and [CONFIG.md](/CONFIG.md) files. User docs for how to call the endpoints and what to expect are found below in the [API Calls](#API-Calls) section and as the `docs.html` page. 

### Make a Change
Application changes should be written in Java 8, and separated by concern - `site`, `api`, or backend logic (`txt` and any potential siblings).

Before or during the change, add unit tests in the business logic package unit tests directory. New endpoints/pages should get controller and integration tests as well. 

### Local Testing
(1) Test manually by running as a jar. the api will be available on [localhost:8080](http://localhost:8080/). Test plans consist of:
 - `curl`ing each of the endpoints (`/api`, `/api/string`, `/api/file`), with appropriate data or parameters that will error (e.g. nonexistent files, missing param)
 - visit each page in a browser (`/`, `/docs`, `/resources`)

(1) Test the containerized application locally by building the jar, building a dev docker container, and running locally. The site will be available at [localhost:8080](http://localhost:8080)

```
$ gradle clean build
$ docker build -t acnagy/coffee-word-search:local .
$ docker run -p 8080:8080 acnagy/coffee-word-search:local
```

(2) Deploy the docker container a local kubernetes cluster with [Minikube](https://kubernetes.io/docs/setup/minikube/) for local development and use `$ minikube start` to create a cluster. For production, create a separate cluster somewhere (e.g. with [kops](https://kubernetes.io/docs/setup/custom-cloud/kops/)or a hosted kubernetes service).

```
$ kubectl create -f deployment.yaml
$ kubectl create -f service.yaml  
```

The address of the cluster will either be the name of the load balancer created (in the cloud), or it will be the external IP that `minikube service coffee-word-search` knows about. 

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

To deploy, grab the image and re-tag with the minor version referenced in `deployment.yaml`

```
$ docker pull acnagy/coffee-word-search:BUILD_TAG
$ docker tag IMAGE_NAME acnagy/coffee-word-search:TAG
$ docker push acnagy/coffee-word-search:TAG
```

then, deploy to the kubernetes cluster with:

```
$ kubectl apply -f deployment.yaml
```

show the status of the roll-out and get more information via: 

```
$ kubectl get deployment coffee-word-search
# or
$ kubectl describe deployment coffee-word-search
```

## API Calls 

The API supports three endpoints. 

GET: `URL/api/`
params: none

```
$ curl -s URL/api/
{
  "term": null,
  "count": 1,
  "input": "Hi! Welcome to the app :)"
}

```

POST: `URL/api/string/`
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

POST: `URL/api/file/`
params: 
 - term: string
 - file: file path

```
$ curl -s -X POST \
-F term="something" \
-F file="@fileName" \
URL/api/file | jq

 {
   "term": "somethin",
   "count": 2,
   "input": "test-file3.txt"
 }
```
