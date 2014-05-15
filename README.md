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
    - por ultimo borramos la carpeta WebContent que se genero en el raiz ya que no la vamos a usar.
- agregar las librerias del M2 a el deploy path
    - propiedades del proyecto..
    - quitamos el source /WebContent (nuestro contenido web esta en webapp)
    - agregamos "folder" /src/main/webapp
    - por ultimo agregar "Java Build Path Entries", seleccionamos todas las librerías y aceptamos.
- correr en tomcat 7
    - para ello agregamos un server apache tomcat 7
    - next a todo
    - una vez listo clik derecho sobre el server y agregamos el proyecto
