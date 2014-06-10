eCuentas
========

eCuentas nos permite llevar todos los ingresos y egresos de nuestro microemprendimiento de manera online en cualquier lugar donde te encuentres.


Instalación
-----------
- clonar el proyecto
- correr  "mvn eclipse:eclipse"
- importar proyecto en eclipse
- convertir el proyecto a dynamic proyect
    - propiedades del proyecto..
    - "Project Facets"..
    - clikar en Convert..
    - checkear "Java" y "Dynamic Web Module", luego aceptamos..
- agregar las librerias del M2 a el deploy path
    - propiedades del proyecto.. "Deployment Assembly"
    - quitamos el source /WebContent (nuestro contenido web esta en webapp)
    - agregamos "folder" /src/main/webapp
    - por ultimo agregar "Java Build Path Entries", seleccionamos todas las librerías y aceptamos.
- borramos la carpeta WebContent que se genero en el raiz del proyecto ya que no la vamos a usar.
- correr en tomcat 7
    - para ello agregamos un server apache tomcat 7
    - next a todo
    - una vez listo clik derecho sobre el server y agregamos el proyecto

Heroku
------

Este branch lo usamos para subir los cambios a heroku

una vez la version productiva este en el master, nos paramos en el branch heroku y hacemos un merge del master..

en este punto ya tenemos los cambios que queremos subir.

Para subir a heroku hacemos "git push heroku heroku:master" lo que hacemos con este codigo es hacer un git push..

al server 'heroku' del branch heroku al master de este servidor (heroku:master)


Test Local
------------
export DATABASE_URL=postgres://'user':'pass'@localhost:5432/'dbName'

mvn package

java -jar target/dependency/jetty-runner.jar target/*.war
