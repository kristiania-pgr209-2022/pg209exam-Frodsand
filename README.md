[![Java CI with Maven](https://github.com/kristiania-pgr209-2022/pg209exam-Frodsand/actions/workflows/maven.yml/badge.svg)](https://github.com/kristiania-pgr209-2022/pg209exam-Frodsand/actions/workflows/maven.yml)

# PG209 Backend programmering eksamen

## Sjekkliste for innleveringen

* [x] Dere har lest eksamensteksten
* [x] Koden er sjekket inn på github.com/pg209-2022 repository
* [ ] Dere har lastet opp en ZIP-fil lastet ned fra Github
* [x] Dere har committed kode med begge prosjektdeltagernes GitHub-konto (alternativt: README beskriver hvordan dere har jobbet)

## README.md

* [x] Inneholder link til Azure Websites deployment
* [x] Inneholder en korrekt badge til GitHub Actions
* [ ] Beskriver hva dere har løst utover minimum
* [ ] Inneholder et diagram over databasemodellen

## Koden

* [ ] Oppfyller Java kodestandard med hensyn til indentering og navngiving
* [x] Er deployet korrekt til Azure Websites
* [ ] Inneholder tester av HTTP og database-logikk
* [x] Bruker Flyway DB for å sette opp databasen
* [ ] Skriver ut nyttige logmeldinger

## Basisfunksjonalitet

* [x] Kan velge hvilken bruker vi skal opptre som
* [x] Viser eksisterende meldinger til brukeren
* [ ] Lar brukeren opprette en ny melding
* [ ] Lar brukeren svare på meldinger
* [ ] For A: Kan endre navn og annen informasjon om bruker
* [ ] For A: Meldingslisten viser navnet på avsender og mottakere

## Kvalitet

* [ ] Datamodellen er *normalisert* - dvs at for eksempel navnet på en meldingsavsender ligger i brukertallen, ikke i meldingstabellen
* [ ] Når man henter informasjon fra flere tabellen brukes join, i stedet for 1-plus-N queries (et for hovedlisten og et per svar for tilleggsinformasjon)
* [ ] Det finnes test for alle JAX-RS endpoints og alle DAO-er


### Chat  
Link to azure:

Vår databasemodell:


I vår frontend klikker du på hvem du vil sende en melding fra og hvem du vil sende til.
For å se meldingshistorikken for hver bruker, klikk på brukernavnet under Avsender.

I vår frontend er det kun mulig å sende/svare og se meldinger som bruker skrevet eller mottatt.
For att minimera tiden på frontend kode, har vi i stedet løst flere funksjonaliteter ved hjelp av tester. 
I testene vil du se at det er mulig å slette en melding,
oppdatere informasjon om en bruker, returnere melding etter emne, opprette ny bruker, se melding basert på bruker.






