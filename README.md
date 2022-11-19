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
* [x] Beskriver hva dere har løst utover minimum
* [x] Inneholder et diagram over databasemodellen

## Koden

* [x] Oppfyller Java kodestandard med hensyn til indentering og navngiving
* [x] Er deployet korrekt til Azure Websites
* [x] Inneholder tester av HTTP og database-logikk
* [x] Bruker Flyway DB for å sette opp databasen
* [x] Skriver ut nyttige logmeldinger

## Basisfunksjonalitet

* [x] Kan velge hvilken bruker vi skal opptre som
* [x] Viser eksisterende meldinger til brukeren
* [x] Lar brukeren opprette en ny melding
* [x] Lar brukeren svare på meldinger
* [x] For A: Kan endre navn og annen informasjon om bruker (Visar detta i tester)
* [x] For A: Meldingslisten viser navnet på avsender og mottakere

## Kvalitet

* [x] Datamodellen er *normalisert* - dvs at for eksempel navnet på en meldingsavsender ligger i brukertallen, ikke i meldingstabellen
* [x] Når man henter informasjon fra flere tabellen brukes join, i stedet for 1-plus-N queries (et for hovedlisten og et per svar for tilleggsinformasjon)
* [x] Det finnes test for alle JAX-RS endpoints og alle DAO-er


### Chat  
Link to azure:
https://pgr209-exam-2022-1009.azurewebsites.net/

Vår databasemodell:
![](document/database.png)

I vår databasemodell har vi valgt å bruke tre tabeller:
- Brukere har kolonnene id, username, email og phone_number
- Chat har kolonnene id, sender_id, receiver_id og message_id
- Meldinger har kolonnene id, message_body og subject

Chat-tabellen fungerer som en tråd mellom messages og user tabellene. En chat/tråd må ha 1 eller flere users. 
Users trenger ikke ha en chat/tråd, men kan ha flere chatter/tråder.
Messages kan kun ha en chat/tråd, da vi har valgt at det ikke er mulig å sende gruppemeldinger. 
En chat/tråd trenger ikke ha en messages, men kan ha flere messages.

Vi har valgt å sette mest fokus på backend, fordi det er en eksamen i backend. Vår frontend er derfor veldig enkel med alle funksjoner på første side.
Der kan du velge hvilken bruker du ønsker å representere. Deretter vises samtidig denne brukerens meldingshistorikk frem. 
Både meldinger sendt og mottatt av denne brkeren vises. Du kan også se hvem brukeren har mottatt meldinger fra.
Etter det kan du velge hvem du vil sende en melding til, skrive inn et subject og en body. Klikk send for å send meldingen.
Ønsker du å se meldingen du sendte, klikker du på brukeren du har sendt til (han er da en aktiv user), du finner meldingen hans under mottatte meldinger.
Her kan du så, ved å velge den tidligere avsenderen som mottaker, svare på meldingen.

Vi har også implementert mer funksjonalitet, men valgt å vise dette gjennom tester og ikke i GUI.

I testene vil du se at det er mulig å:

- Sende og motta meldinger
-- test og frontend
- Slette en melding
-- I test - MessageDaoTest
- Hente ut meldinger mellom to brukere 
-- I test 
--- ChatDaoTest og ChatServerTest
- Hente ut melding basert på sender
-- I test og frontend
- Hente ut melding baser på mottaker
-- I test og frontend
- Hente ut meldinger etter emne 
-- I test 
--- MessageDaoTest
- Opprette ny bruker
-- I test 
--- UserDao og ChatServerTest
- Hente ut sender og mottaker
-- I test og frontend
- Oppdatere informasjon om eksisterende bruker 
-- I test
--- UserDaoTest og ChatServerTest

Samarbeidet under dette prosjektet har gått veldig bra. Vi har både jobbet sammen på skolen, på discord samtidig 
som vi deler skjerm eller hver for seg med ulike oppgaver. 
Vi har begge committed til Github fra begge Github-kontoene og brukt branching flittig for å unngå konflikter.
Samtidig har vi vært flinke til å ta hverandre gjennom vår kode for at begge skal forstå hva den andre har gjort.

Funksjonalitet utover minimum
- Kan endre informasjon om eksisterende bruker med PUT http method
- Bruker lagrer i tillegg mobil nummer
- Meldinger viser avsender og mottaker (gjort med join, vist i tester og på frontend)
- meldinger kan slettes 







