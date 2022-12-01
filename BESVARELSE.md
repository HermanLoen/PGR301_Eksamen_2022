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
* Workflowen feilet fordi vi ikke har definert repository secrets for dockerhub. jeg opprettet to secrets i repoet mitt, DOCKERHUB_USERNAME og DOCKERHUB_TOKEN.
* Derretter la jeg inn brukernavn og passordet i secrets.
* Vedlagt ser man workflowen kjører og bygger docker image og pusher til dockerhub.
<img src="/images/deloppgave-1-part-3.png">

### Oppgave 2
* Jeg har skrevet om DockerFile til å bruke multi stage build. Det betyr at vi kan bygge et docker image med bare det vi trenger for å kjøre applikasjonen.
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
Inspirasjon er hentet fra: https://docs.docker.com/language/java/configure-ci-cd/
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
**Lag nøkkler på AWS**
* Logg inn på din AWS konto -> Øverst i høyre hjørne Klikker du på ditt brukernavn "dittnavn@2445-3000-8913" -> Security Credentials -> Velg fanen AWS IAM credentials -> Create New Access Key
* Lagre Access Key ID og Secret Access Key i en fil/notater på din maskin. Du vil ikke få tilgang til disse igjen.

**Legg til secrets i Github repoet**
* Gå til repoet ditt på Github -> Settings -> Secrets -> New repository secret
* Legg til to secrets, _AWS_ACCESS_KEY_ID_ og _AWS_SECRET_ACCESS_KEY_, Bruk verdiene du lagret i steg 1.

**Lag ditt eget ECR repo**
* Fra terminalen kan du kjøre følgenede kommando
```bash
aws ecr create-repository --repository-name [navn på repoet]
```

**Neste steg er å endre docker.yml**
* I docker.yml filen har jeg lagt til en step som heter "Build and push Docker image to AWS ECR" som henter nøkkler fra secrets og setter opp AWS credentials.
```yaml
  name: Build and push Docker image to AWS ECR
        env:
          AWS_ACCESS_KEY_ID: ${{ secrets.AWS_ACCESS_KEY_ID }}
          AWS_SECRET_ACCESS_KEY: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
        run: |
          aws ecr get-login-password --region eu-west-1 | docker login --username AWS --password-stdin 244530008913.dkr.ecr.eu-west-1.amazonaws.com
          rev=$(git rev-parse --short HEAD)
          docker build . -t shopifly
          docker tag shopifly 244530008913.dkr.ecr.eu-west-1.amazonaws.com/1051:$rev
          docker push 244530008913.dkr.ecr.eu-west-1.amazonaws.com/1051:$rev
```
* Her er det viktig å endre til ditt eget repo navn. Jeg har brukt 1051 som repo navn. Det er også viktig at du endrer til din egen AWS account ID. Jeg har brukt 244530008913 som AWS account ID.
* Sjekk også at regionen er riktig satt. Jeg har brukt eu-west-1 som region.
* Eksempel på hvordan det skal se ut:
```yaml
docker tag shopifly [dinAcountId].dkr.ecr.eu-west-1.amazonaws.com/[dittRepoNavn]:$rev
docker push [dinAcountId].dkr.ecr.eu-west-1.amazonaws.com/[dittRepoNavn]:$rev
```

**Se repoet på AWS**
* Push endringene du har gjort i koden til Github.
* Github vil automatisk trigge en workflow som bygger docker image og pusher til AWS ECR.
* Gå til AWS konsollen -> Velg fanen ECR -> Velg repoet ditt/søk på repo navn -> Velg fanen Images -> Du skal nå se at det er lagt til et nytt image i repoet ditt.

