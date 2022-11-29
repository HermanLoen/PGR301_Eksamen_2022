### Oppgave 3 

* Gå til **Settings/Branches** og se etter seksjonen "Branch Protection Rules".
* Velg _"Add"_
* Velg _"main"_ Som branch i branch name pattern
* Velg _"require a pull request before merging"_
* velg _"require approval"_, sett antall til 1
* Velg _"Require status check to pass before merging"_
* I søkefeltet skriv inn teksten _"build"_ som skal la deg velge "GitHub Actions".
* Lagre endringene ved å trykke på "_Save changes_"
* Nå kan vi ikke Merge en pull request inn i Main uten at status sjekken er i orden. Det betyr at vår Workflow har kjørt OK.
* Ingen i teamet kan heller "snike seg unna" denne sjekken ved å comitte kode rett på main branch.

## Del 3 - Docker

### Oppgave 1
Workflowen feilet fordi vi ikke har definert repository secrets for dockerhub. jeg opprettet to secrets i repoet mitt, DOCKERHUB_USERNAME og DOCKERHUB_TOKEN.
Derretter la jeg inn brukernavn og passordet i secrets.
Vedlagt ser man workflowen kjører og bygger docker image og pusher til dockerhub.
<img src="/images/deloppgave-1-part-3.png">

### Oppgave 2
Jeg har skrevet om DockerFile til å bruke multi stage build. Det betyr at vi kan bygge en docker image med bare det vi trenger for å kjøre applikasjonen.
```
FROM maven:3.6-jdk-11 as builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn package

FROM adoptopenjdk/openjdk11:alpine-slim
COPY --from=builder /app/target/*.jar /app/application.jar
ENTRYPOINT ["java","-jar","/app/application.jar"]
```

Fjernet unødvedige steps og ryddet i docker.yml filen.
<img src="/images/Fjernet-steps-oppdaterts-yml.png">
```
name: Docker build
on:
  push:
    branches: [ main ]
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout
        uses: actions/checkout@v2

      - name: Login to Docker Hub
        uses: docker/login-action@v2
        with:
          username: ${{ secrets.DOCKER_HUB_USERNAME }}
          password: ${{ secrets.DOCKER_HUB_TOKEN }}

      - name: Build and push
        uses: docker/build-push-action@v3
        with:
          context: .
          file: ./Dockerfile
          push: true
          tags: ${{ secrets.DOCKER_HUB_USERNAME }}/shopifly:latest
```

### Oppave 3
