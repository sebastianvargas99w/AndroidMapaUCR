#Aclaración

La base de datos podría no estar disponible.

# Universidad de Costa Rica
# Documento de Conceptualización
# Sistema Guía@UCR
# Versión 0.4

## Introducción

El presente documento pretende describir a grandes rasgos los
aspectos más importantes del sistema Guía@UCR. Esto con el
propósito de que tanto el cliente, como el equipo de desarrollo
tengan una referencia para ayudarse a entender mejor el 
proyecto en cuestión. 
A su vez el documento está dividido en las siguientes secciones:

* Descripción general del sistema
* Prototipos de la aplicación
* Artefactos de bases de datos
* Decisiones técnicas

### Equipo

* Fabián Antonio Rojas Masís B66236
* Pablo César Ruiz Vásquez B56404
* Sebastián Vargas Soto B47431
* Sharon Bejarano Hernández A90868
* Steven Barahona Meza B70893

## Descripción general del sistema

Esta sección se encarga de brindar contexto sobre la aplicación y
el problema que trata de resolver, así como describir los elementos
principales del sistema.

### Contexto, situación actual, y problema a resolver

Todos los años ingresa una gran cantidad de nuevos estudiantes a la 
Universidad de Costa Rica. La gran mayoría de ellos no tienen
el conocimiento del campus como para manejarse, y debido a esto es
muy común que en el primer semestre los estudiantes lleguen tarde
a las clases, que se equivoquen de aula, o que simplemente no sepan
cómo ubicarse cuando por ejemplo les piden que saquen alguna copia.

### Solución propuesta

Por esta, y más razones, se piensa en desarrollar una aplicación que
consista en un mapa de la UCR, la cual brinde funcionalidades que
le faciliten el tránsito por el campus a los nuevos estudiantes, e
incluso a visitantes de la UCR.

### Descripción de los principales epics

Para esta versión 0.2 del sistema, se tienen propuestos dos epics:

* Mapa Principal: Corresponde a la funcionalidad base del sistema, como lo es 
presentar un mapa de la UCR, en la sede Rodrigo Facio en San Pedro de Montes de Oca, que esté 
limitado a la navegación por las tres fincas: campus central, ciudad de la investigación e 
instalaciones deportivas. En este mapa se puede ver la ubicación actual del usuario si se encuentra 
dentro de los límites antes descritos, diferentes puntos de interés dentro de la universidad con una 
pequeña descripción y además permite realizar búsquedas de estos puntos.

* Navegación: Este epic se montra sobre cierta funcionalidad del Mapa 
Principal. ya que su principal objetivo es posibilitar la definición de 
la ruta más corta para algún destino que defina el usuario. Esto permite 
que los usuarios tengan una guía a mano sobre como movilizarse de la mejora
manera dentro de la universidad. Esta funcionalidad se centra en usuarios 
que son nuevos y no tienen conocimiento del entorno que les rodea.

* Favoritos: Este epic va de la mano con el epic de Identificación. Se trata 
de proveerle al usuario la manera de guardar una serie de puntos de interés para 
tenerlos a disposición para más fácil acceso. Esto permite consultar la información 
de estos puntos de manera más rápida y poder encontrarlos más sencillo dentro de los 
demás puntos presentes en el mapa.

* Identificación: Este epic pone a disposición del usuario una manera de crear un 
perfil para la aplicación. Esto le permite tener información propia que puede tener a 
disposición para acceder desde otro dispositivo si fuera necesario.

### Requerimientos funcionales

* El sistema debe mostrar un mapa con los puntos de interés de la finca en la que se encuentra 
enfocado el usuario.

* El sistema debe permitir que el usuario cambie su enfoque entre las tres fincas pertenencientes 
a la UCR Sede Rodrigo Facio.

* La aplicación permite que el usuario inicie sesión para tener acceso a puntos favoritos.

* La aplicación guía al usuario por la ruta más corata entre dos puntos de interés.

* El usuario tiene la posibilidad de buscar puntos de interés por el nombre de estos.

* El usuario puede filtrar los puntos de interés según su tipo para ver solo los que de verdad 
le interesan.

Para ver los detalles de los requerimientos funcionales, diríjase al
siguiente link: [Jira](http://10.1.4.22:8080/secure/RapidBoard.jspa?rapidView=37&projectKey=CNA&view=planning&selectedIssue=CNA-5)

### Requerimientos no funcionales

* Disponibilidad: El sistema debe mantenerse corriendo en todo momento al ser una aplicación móvil.

* Eficiencia en el tiempo: El sistema debe responder en un corto período de tiempo, como límite, 
la app no debería durar más de 5 segundos sin responder.

* Usabilidad: El sistema debe ser fácil de aprender a utilizar, que el usuario esté familiarizado 
con el propósito y funcionalidad de la aplicación en menos de 10 minutos.

* Adaptabilidad: El sistema debe poder utilizarse en diversos entornos sin mucha complicación, que 
pueda correr en diversas versiones de Android y en diversos tipos de teléfono.

### Posibles interacciones con sistemas externos

El sistema GuiaUCR utiliza para su funcionamiento principal MapBox SDK.

## Prototipos de la aplicación

![Prototipos](/Prototipos/Prototipos1.png)

## Artefactos de bases de datos

### Esquema conceptual

Se adjunta el enlace para ver el esquema conceptual actualizado a la 
versión de este documento: 

![Esquema](/Prototipos/Esquema.png)

## Decisiones técnicas

### Metodologías utilizadas

Para el desarrollo del proyecto se escoge utilizar la metodología ágil
Scrum con sus actividades relacionadas (Sprint Planning, Grooming, 
Sprint Review...). Como método de asignación de trabajo se decide asignar
una historia de usuario por cada uno de los integrantes del equipo de
desarrollo. Los canales principales de comunicación son un server de 
Discord dedicado al proyecto, y un chat de equipo en la aplicación
WhatsApp. De ser necesario se realizan validaciones con el PO.

### Artefactos utilizados

* Backlog en Jira

* Prototipos de la aplicación 

* Esquema conceptual de la base de datos

### Tecnologías utilizadas

* Android Studio (Versión 4.0.0)

* MapBox Android SDK (Versión 9.4.0)

### Repositorio de código

Como repositorio se utiliza BitBucket. La estrategia que se utiliza
para administrar los branches es la de 1 branch por desarrollador y
1 master.

### Definición de listo

* El código compila

* Se agrega código que aporta funcionalidad

* La funcionalidad cumple con los criterios de aceptación negociados con el PO.

* La funcionalidad fue revisada y validada por otro desarrollador.

* La funcionalidad se encuentra integrada a la rama master del repositorio de código del proyecto.

* La funcionalidad es validada por el PO.

### Casos de Prueba

![Caso1](/Prototipos/CasoPrueba1.png)

![Caso2](/Prototipos/CasoPrueba2.png)