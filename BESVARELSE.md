### Del 1 DevOps-prinsipper
####A)
Utfordringen med dagens systemutviklingsprosess er at det er mange manuelle steg i prosessen. Ved å bruke fossefall som utviklings-metode kan det føre til at prosessen tar lenger tid. 
Hver prosjektfase må fullføres før du går videre til neste fase.
Andre problemer som kan oppstå under dagens prosess er at det kan være svært tidkrevende å rette opp i feil når de først oppdages. Det kan være vanskelig å innse et problem under en fase, 
som resulterer i at du må gå tilbake til en tidligere fase for å rette opp i feilen. Dette kan føre til at prosessen tar lenger tid enn den burde.
Waterfall-metodikken krever at du skisserer prosjektet fra start til slutt. Dette tillater ikke mye fleksibilitet eller endring underveis i prosessen da dette kan føre til 
komplikasjoner med tidligere/fremtidige faser i prosjekter.
Dagens leveranse skjer ved at Utviklingsteamet bruker FTP til å overføre en Spring boot JAR sammen med dokumentasjon i en ZIP. En egen avdeling tar i mot disse filene og 
installerer i AWS / Produksjon. Dette er unødvendig bruk av ressurser da dette burde vært automatisert.

Innføring av devops i prosjektet vil føre til en rekke forbedringer. Flow(Flyt) handler om å minimere risikoen forbundet med utrullinger ved å bruke prinsipper for kontinuerlig levering som:
* Lage automatisk miljøer for utvikling, test og produksjon etter forespørsel
* Streng versjonskontroll
* Automatisering av bygg, test og integrasjon av koden.
* Ved bruk av unleash kan ny utviklet kode testes mot en spesifikt valgt brukergruppe og størrelse.
  * Dette forenkler prosessen med å deploye ny kode, hvor man får inblikk i hva som funket bra og eventulet hva som burde endres før koden deployes til hele bruker gruppen.
* Det senker også risikoen ved å gå fra "Monolithic" til "Microservice" arkitektur.

Ved å innføre DevOps får vi i større grad innblikk og tilbakemeldinger fra applikasjonen. Overvåkning av applikasjonen vil føre til tidligere oppdagelse av feil. Dette muliggjør en sikker leveranseflyt.
Tilbakemeldinger kan inføres ved hjelp av ulike verktøy som for eksempel: 
* Alarmer
* Logs
* Metrics
* Test suits
* Integrasjonstester
* Ytelse-testing i pipeline.

DevOps er ikke bare en utviklingsmetode men en kultur for kontinuerlig forbedring. Ved å dele ansvar mellom utviklere og driftsavdeling vil det føre til en bedre kommunikasjon og forståelse 
for hverandres arbeid. Dette vil føre til en bedre kvalitet på applikasjonen og en bedre kundeopplevelse.
Utviklerene vil måtte ta ansvar for egne feil og utføre forbedringer, samt lære av feil for å redusere feil i fremtidige utviklingsprosesser. 

Prinsippene som blir brutt er:
* Automatisering
* CI
* CD
* Overvåkning/Tilbakemelding/logging
* Alarmer

####B)
Problemet med denne tilnærmingen er at de ikke tar tak i selve problemet og den underliggende årsaken til at feilene først oppstår under release.
Bedriften fokuserer på å ikke ha feil i produksjon i stedet for å fokusere på hvorfor det feiler. Ved å rette opp i feilene underveis i utviklingen vil det føre til færre feil i produksjon. 
Det er også viktig at bedriften fokuserer på å lære av feilene de gjør og holder utviklerne ansvarlig for egne feil. Dette vil føre til at de blir flinkere til å finne feilene før de havner i produksjon.


####C)
Utfordringer med å overlevere koden til en sepparat drifts-avdeling er at utviklerne ikke har ansvar for koden de har utviklet. Dette betyr at vi ikke er ansvarlige for eventuelle feil som oppstår under produksjonen. 
Som videre betyr at de ikke lærer av feilene de gjør, samt ikke blir flinkere til å oppdage dem før de havner i produksjon.
Ved å ha en utvikler på vakt som har kjennskap til koden som er ute i produksjon kan drastisk redusere tiden det tar fra en feil er oppdaget i produksjon til den er rettet opp i.
####D)
Å release kode ofte kan føre til utfordinger i form av feil i produksjonskode. Feil i produksjon kan i verstefall føre til nedetid for applikasjonen og derfor en dårlig kundeopplevelse og økte kostnader 
for bedriften i form av feilsøking og overtidsarbeid. 
Ved bruk av DevOps prinsipper kan vi redusere/fjerne risikoen ved hyppige leversanser:
* Ved å bruke CI/CD vil vi kunne teste koden i et testmiljø før den havner i produksjon. Dette vil redusere risikoen for feil i produksjon.
* Overvåkning i form av alarmer og Metrics vil gi oss varslinger/indikasjoner om feil som  kan oppstå i produksjon. Dette vil hjelper oss med å oppdage eventuelle feil 
før de har oppstått i produksjon og redusere tiden det tar å rette opp i feilen.
* Ved bruk av verktøy som for eksempel Unleash kan vi teste koden mot en spesifikt valgt brukergruppe og størrelse. Dette vil føre til at bare en liten bruker gruppe får ny kode,
derrav får vi testet koden mot en liten brukergruppe før vi deployer koden til hele brukergruppen. Dette vil redusere risikoen for feil i produksjon. Samtidig kan vi velge å reversere koden dersom det oppstår uventete feil i andre deler av applikasjonen.

## Del 2-CI
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

### Del 5 oppgave 1
Grunnen til at Terraform prøver å opprette en bucket selv om den eksisterer fra før, er på grunn av state. State er en fil som inneholder informasjon om hva som er opprettet og hva som er endret i terraform.
Skrur vi på skjulte filer i intelliJ vil vi kunne se .terraform katalog. Den inneholder en terraform "provider" for AWS. Denne provideren har en state fil som heter "terraform.tfstate". Denne filen inneholder informasjon om hva som er opprettet og hva som er endret i terraform. 
Denne filen er lagret i en bucket som heter eksempelvis "terraform-state-244530008913". Denne bucketen eksisterer fra før og er opprettet i AWS konsollen. 
Når vi kjører terraform init, så vil terraform prøve å opprette en ny bucket med samme navn. Det er derfor vi får feilmeldingen "Error: Error creating S3 bucket: BucketAlreadyOwnedByYou: Your previous request to create the named bucket succeeded and you already own it".

For å løse dette problemet må vi slette den eksiterende bucketen i AWS konsollen og endre "databucket.tf" og legge til en backend blokk som forteller Terraform att state-informasjon skal lagers i S3.
Eksempel på backend blokk:
```terraform
terraform {
  backend "s3" {
    bucket = "pgr301-exam-2021-terraform-state"
    key    = "<studentId>/apprunner-exam.tfstate"
    region = "eu-north-1"
  }
}
```