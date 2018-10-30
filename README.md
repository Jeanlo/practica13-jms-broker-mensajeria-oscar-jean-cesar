# Décima tercera práctica - Bróker de Mensajería 

![PUCMMM-logo](https://i.imgur.com/9eEIci9.png)

**Décima tercera práctica** realizada para la asignatura Programación Web Avanzada (ISC-517) perteneciente a la carrera Ingeniería de Sistemas y Computación de la Pontificia Universidad Católica Madre y Maestra (PUCMM) en el ciclo Agosto-Diciembre 2018.

## Realizado por

**JEAN LOUIS TEJEDA GARCÍA** -  MAT. 2013-1459

**OSCAR DIONISIO NÚÑEZ SIRI** -  MAT. 2014-0056

**CESAR JOSÉ PEÑA MARTE** - MAT. 2013-0488

## Objetivo general

Hacer una aplicación que implemente un Bróker de Mensajería, que se base en el intercambio de mensajes asíncronos y que implemente colas de suscripción y publicación, todo esto debe ser basado en el estándar JMS (Java Message Service). Las tareas requeridas pueden ser vistas en [Tareas requeridas](#tareas-requeridas).

## Vídeo de demostración del proyecto

https://youtu.be/wZz62v5-O24  ==> (click en el link para ver el vídeo)

## Tecnologías requeridas

- JMS (Java Message Service)
- Java
- Elegir entre las tres opciones:
	- MQTT
	- OpenWire
	- AMQP
- ActiveMQ
- JavaScript
- WebSocket
- Google Charts
- SparkJava
- Freemarker
- Bootstrap 4
- H2
- HTML
- CSS

## Estructura de los datos

La estructura de datos requerida para esta práctica consta de cuatro datos, con sus formatos respectivos:

- Fecha de generación (String en formato de DD/MM/YYYY HH:mm:ss)
- IdDispositivo (Integer)
- Temperatura (Number)
- Humedad (Number)

## Tareas requeridas

- Desarrolle un cliente que simule los Endpoint, que generen de forma aleatoria valores de temperatura y humedad y envíe la trama en el formato JSON definido a la cola indicada. Debe instanciar dos clientes para la prueba.
- Implemente un servidor que soporte JMS y algún protocolo orientado a mensaje que inicialice la cola de distribución del tipo publicación/subscripción. Puede ser una aplicación con el protocolo de forma embebida o un servidor diseñado para esos fines (Apache ActiveMQ, RabbitMQ, Mosquitto, entre otros).
- Realicen una aplicación web que visualice los datos procesados mediantes gráficos del tipo linea en tiempo real, donde se visualice la información de la temperatura y la humedad vs el tiempo (separar los gráficos), para cada uno de los sensores conectados. La comunicación entre la aplicación web y cliente puede ser utilizando el protocolo HTTP o WebSocket.

## Diagrama del escenario

![Diagrama-del-escenario](https://i.imgur.com/8vQGIcf.png)
