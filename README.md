# MySnemovna

## Description

This application was created as diploma thesis on the FIT CTU (FIT ČVUT).
This application serves as back-end for front-end mobile application written in Flutter:
```
https://github.com/demchiva/MySnemovnaFrontend.git
```

The application offers api endpoints with data of "Poslanecká sněmovna" (PSP).
This structure is a part of Czech Republic Parliament.
The source data used for this app can be found on the official PSP web:
```
https://www.psp.cz/sqw/hp.sqw?k=1300
```

The application in current version has REST API endpoint for next PSP agendas:

- Meetings. PSP meetings on which parliament members votes for some points.
- Votes. Votes of parliament members and point.
- Members. PSP member which can vote.

For information about another agendas see official web (link above).

## Documentation

The application has OpenAPI and Swagger Documentation of REST API. 

For view interactive Swagger docs open:
```
http://localhost:8085/swagger-ui.html
```

For view docs in OpenAPI json format open:
```
http://localhost:8085/api-docs
```

## Project requirements

Project requirements:

- Java 17 or higher
- IntelliJ IDEA

## Steps to run project

Clone this repository using:
```
git clone https://github.com/demchiva/MySnemovna.git
```

Open project in IntelliJ IDEA:
```
Open IntelliJ IDEA -> File -> Open -> Find project and click Open
```

Choose project Java SDK version (Java 17).

Find the main class with name:
```
SnemovnaApplication
```

and edit it running configuration:
```
Edit configuration -> Fill "Active profiles" with value "local"
```

Now you can run the project locally. Run green triangle and Tomcat server should be started. 

## Copyright

This program can be distributed under GNU General Public License.

If you use this code please cite:

```
Bc. I. Demchenko, "Aplikace pro zobrazení výsledků hlasování Poslanecké
sněmovny", MySnemovna 2023
```