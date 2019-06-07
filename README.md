# Tarea Reservas de Aulas
## Profesor: José Ramón Jiménez Reyes
## Alumno: David Escoriza Castells

El cliente nos acaba de dar unos nuevos requisitos a aplicar sobre la última versión que le mostramos y que le gustó bastante. Lo que nos pide el cliente es lo siguiente:

 - Que la aplicación no almacene los datos en ficheros y que lo haga en una base de datos creada para tal efecto.
 
Los datos de la BD, que es una BD MongoDB, son los siguientes:

 - Servidor: 35.180.192.242
 - Puerto: 27017
 - BD: reservasaulas-XXX, donde XXX es el usuario de correo que me mandaste en el foro.
 - Usuario: usuario de correo que me mandaste en el foro.
 - Contraseña: La que te he enviado por medio de mensaje en la plataforma.
 
Tu tarea consiste en dotar a la aplicación de la tarea anterior de un nuevo modelo de datos que en vez de utilizar ficheros para almacenar los datos lo haga haciendo uso de una Base de Datos NoSQL. Se pide al menos:

 - Acomodar el proyecto para que gradle gestione la dependencia con el driver para java de MongoDB en su última vesión. Además deberás modificar el proyecto para que se puedan ejecutar todas las versiones: ficheros con IU textual, ficheros con IU gráfica, BD con IU textual y BD con IU gráfica.
 - Gestionar las aulas para que su persistencia se lleve a cabo por medio de dicha BD.
 - Gestionar los profesores para que su persistencia se lleve a cabo por medio de icha BD.
 - Gestionar las reservas para que su persistencia se lleve a cabo por medio de dicha BD.
 
Para ello debes realizar las siguientes acciones:

1.Lo primero que debes hacer es crear un repositorio  en GitHub a partir de tu repositorio de la tarea anterior.
2.Clona dicho repositorio localmente para empezar a modicfiarlo. Modifica el fichero README.md para que indique tus datos y los de esta tarea. Realiza tu primer commit.
3.Realiza los cambios necesarios para que el proyecto pueda contener cuatro aplicaciones diferentes: ficheros con IU textual, ficheros con IU gráfica, BD con IU textual y BD con IU gráfica. Haz un commit.
4.Haz que la gestión de aulas utilice la persistencia en la BD. Haz un commit.
5.Haz que la gestión de profesores utilice la persistencia en la BD. Haz un commit.
6.Haz que la gestión de reservas utilice la persistencia en la BD. Haz un commit.
 
######Se valorará:

 - La nomenclatura del repositorio de GitHub y del archivo entregado sigue las indicaciones de entrega.
 - La indentación debe ser correcta en cada uno de los apartados.
 - El nombre de las variables debe ser adecuado.
 - El proyecto debe pasar todas las pruebas que van en el esqueleto del mismo y toda entrada del programa será validada para evitar que el programa termine abruptamente debido a una excepción.
 - Se deben utilizar los comentarios adecuados.
 - Se valorará la corrección ortográfica tanto en los comentarios como en los mensajes que se muestren al usuario.
 
 
 
 
 
