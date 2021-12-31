# Práctica 4 Comunicaciones (WebSocket, Kafka, REST, gRPC y GraphQL)

## Description

Aplicación distribuida para la generación de plantas eólicas: EoloPlanner.
Esta aplicación está compuesta por los siguientes servicios:
- Client: Código JavaScript ejecutado en un navegador web.
- Server: Servidor web que expone una API GraphQL y comunicaciones WebSocket que será consumida por el cliente.
- Planner: Servidor que comunica con las colas de Kafka generando mensajes de progreso según comunica con los siguientes servicios.
- WeatherService: Devuelve información meteorológica.
- TopoService: Devuelve información sobre la orografía del terreno.
Estos servicios se comunican de la siguiente forma:

![secuencia](./images/secuencia.png)

A continuación se detalla esta comunicación:
- Client: Código JavaScript ejecutado en un navegador web.
- Server: Servidor web que expone una API GraphQL y un endpoint WebSockets que será usado por el cliente. Las peticiones solicitadas se las redirige al Planner de forma asíncrona a través de colas de mensajes.
- Planner: Módulo que realiza la planificación de la planta eólica, obteniendo la información del WeatherService y el TopoService y comunicando el progreso al Server.
- WeatherService: Devuelve información meteorológica.
- TopoService: Devuelve información sobre la orografía del terreno.

## System requirements

- Java 17
- Node 16
- Docker 19

## Build

    > npm install
    > node install.js

Descarga las dependencias y construye los 3 proyectos

## Start Docker DB images

    > node exec_aux_services.js

El script descargará e iniciará las imagenes de MySQL, Mongo  necesarias para persistir la información y lensesio/fast-data-dev (Kafka) 
para la gestion de las colas.

## Setup 

    > node exec.js

Este script levanta las 3 aplicaciones:

- weatherservice : http://localhost:9090 (gRPC)
- toposervice : http://localhost:8089 (REST)
- planner : http://localhost:8088 (Kafka)
- server : http://localhost:3000 (GraphQL y WS)

## Predefined data

Para poder probar en el arranque de toposervice se dá de alta las sigueintes ciudades con estas características:

    new City("madrid", "flat"),
    new City("barcelona", "flat"),
    new City("valencia", "flat"),
    new City("sevilla", "flat"),
    new City("albacete", "flat"),
    new City("segovia", "mountain"),
    new City("avila", "mountain"),
    new City("cuenca", "mountain"),
    new City("toledo", "mountain"),
    new City("guadalajara", "mountain"),
    new City("bilbao", "mountain"));

## How to test
 
El servidor dispone de un cliente estático para visualizar las plantas eólicas existentes e insertar nuevas plantas eólicas. Accediendo desde [localhost:3000](http://localhost:3000) donde se encontrará el portal de gestión.

![portal](./images/pantalla.png)

También se pueden probar los endpoints usando [Postman Collection](./Practica3.postman_collection.json)