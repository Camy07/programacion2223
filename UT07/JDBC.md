Una base de datos consiste en datos organizados; es decir, los datos en sí y un esquema que proporciona estructuras de datos. Hoy en día, la mayoría de las bases de datos están organizadas en tablas que consisten en filas y columnas. Esta es una forma natural de organizar los datos, y probablemente estés familiarizado con ellos a través del uso de hojas de cálculo. Puede definir las características de la tabla independientemente de los datos reales que va a almacenar en ella. Un campo es un elemento de datos individual dentro de una tabla que corresponde a la intersección de una fila y una columna. Se pueden especificar una o más columnas como claves únicas para identificar a cada empleado. Para este propósito, puede usar una de las columnas mencionadas anteriormente o la combinación de nombre, apellido y fecha de nacimiento. La clave única utilizada  preferentemente sobre las demás se llama la clave principal o primaria de una tabla.

Casi cualquier aplicación no trivial contiene algún tipo de base de datos. Algunas aplicaciones utilizan una base de datos en memoria, mientras que otras utilizan un sistema tradicional de gestión de bases de datos relacionales (RDBMS). En cualquier caso, es esencial que todos los desarrolladores de Java tengan algunas habilidades para trabajar con bases de datos. A lo largo de los años, la API de conectividad de bases de datos Java (JDBC) ha evolucionado bastante, y en las últimas dos versiones, ha habido algunos avances importantes.

Este capítulo cubre los conceptos básicos del uso de JDBC para trabajar con bases de datos. Aprendes a realizar todas las operaciones estándar de la base de datos y algunas técnicas avanzadas para manipular datos.

## Instalación de MYSQL

## Creación de la base de datos de muestra

En primer lugar, definimos una base de datos simple llamada apressBooks con dos tablas:  Recipes y publications. El diseño lógico de la base de datos se muestra en la Tabla 12-1 y la Tabla 12-2, caracterizado por el nombre, la longitud, el tipo de datos y las restricciones (es decir, un campo es una clave primaria y otro no debe ser nulo).

![2123f0147378e017e40e661ac613c60c.png](_resources/2123f0147378e017e40e661ac613c60c.png)

![9a1dd321bcc3237f81a956aa4b793f02.png](_resources/9a1dd321bcc3237f81a956aa4b793f02.png)

Las sentencias SQL para la creación de la base de datos, los permisos y las tablas son las siguientes:

```sql
DROP DATABASE IF EXISTS `apressBooks`;
CREATE DATABASE `apressBooks`;
CREATE TABLE `apressBooks`.`recipes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `recipe_number` varchar(10) NOT NULL,
  `recipe_name` varchar(100) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ;
 
insert into apressBooks.recipes values(1, 
'12-1',
'Installing MySQL',
'Downloading and installation of a MySQL Database'); 
 
insert into apressBooks.recipes values(2, 
'12-2',
'Connecting to a Database',
'DriverManager and DataSource Implementations');

insert into apressBooks.recipes values(3,
'12-3',
'Handling SQL Exceptions',
'Using SQLException');

insert into apressBooks.recipes values(4,
'12-4',
'Querying a Database and Retrieving Results',
'Obtaining and using data from a DBMS');

CREATE TABLE `apressBooks`.`publication` (
  `id` int NOT NULL AUTO_INCREMENT,
  `book_title` varchar(500) NOT NULL,
  `publish_date` date DEFAULT NULL,
  `publish_co` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

insert into apressBooks.publication values (
1,
'Java 17 Recipes',
date('2021-12-01'),
'APRESS');

insert into apressBooks.publication values (
2,
'Beginning Jakarta EE Web Development',
date('2021-04-03'),
'APRESS');


CREATE USER 'usuario3PAW'@'localhost' IDENTIFIED BY 'P@ssw0rd';

GRANT ALL PRIVILEGES ON apressBooks.* TO 'usuario3PAW'@'localhost';

FLUSH PRIVILEGES;
```

## Pasos para la conectividad entre el programa Java y la base de datos

1. Importar los paquetes

Para la conexión de Java con la base de datos usamos un objeto de conexión JDBC para obtener la conexión. 

Crear una conexión JDBC implica unos pocos pasos. En primer lugar, debe determinar qué controlador de base de datos necesita. Después de determinar qué controlador necesita, descargue el archivo JAR que contiene ese controlador y colóquelo en su ruta de clase. Cada proveedor de bases de datos proporciona diferentes controladores JDBC empaquetados en archivos JAR con diferentes nombres; consulte

La documentación de su base de datos para obtener más información. Para nuestro caso (Mysql) , se puede descargar el conector en https://dev.mysql.com/downloads/connector/j/. Asegúrese de que la plataforma seleccionada sea "Independiente de la plataforma": esto es importante para distribuir el software en diferentes sistemas. 

![0a9cb084e7d0c06a1627ac0f46548ba8.png](_resources/0a9cb084e7d0c06a1627ac0f46548ba8.png)

En la siguiente ventana pinchamos en el texto: 
> No thanks, just start my download.

![290f0636ce0022ea657df0cb4d059025.png](_resources/290f0636ce0022ea657df0cb4d059025.png)

obtenemos un archivo comprimido que incluye el archivo "mysql-connector-j-8.0.32.jar", la librería que vamos a importar a nuestro proyecto.

Este archivo lo copiamos a la carpeta "lib" de nuestro proyecto, si estamos en VSC.

![edbe536a56974839f8dd66d64f2613e7.png](_resources/edbe536a56974839f8dd66d64f2613e7.png)

A continuación, seguimos los siguientes pasos para usar una clase JDBC DriverManager para obtener una conexión a la base de datos.

2. Cargamos los controladores mediante el método forName().
3. Registramos los controladores mediante DriverManager.
4. Establecemo una conexión mediante el objeto de clase Connection.
5. Creamos una instrucción.
6. Ejecutamos la consulta.
7. Cerramos las conexiones.

Analicemos estos pasos brevemente antes de implementarlos escribiendo código adecuado para ilustrar los pasos de conectividad para JDBC.

Paso 2: Carga de los controladores

Para empezar, primero debe cargar el controlador o registrarlo antes de usarlo en el programa. El registro debe hacerse una vez en su programa. Puede registrar un controlador de una de las dos formas que se mencionan a continuación de la siguiente manera:

`Class.forName()


El código quedaría así:

```java
import java.io.*;
import java.sql.*;

class App {
    public static void main(String[] args) throws Exception {
        // detalles de la conexión (servidor y base de datos)
        String url = "jdbc:mysql://localhost:3306/apressBooks";
        // Credenciales MySQL
        String username = "usuario3PAW";
        String password = "P@ssw0rd";
        // Consulta que vamos a ejecutar
        String query = "select * from publication";
        // Driver que vamos a usar
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, username, password);
        System.out.println("Conexión Establecida con éxito");
        Statement st = con.createStatement();
        // Ejecutamos la consulta
        ResultSet rs = st.executeQuery(query);
        rs.next();
        // Recuperamos los datos de la tabla
        String name = rs.getString("book_title");
        // Mostramos los resultados por consola
        System.out.println(name);
        // Cerramos la consulta
        st.close();
        // Cerramos la conexión
        con.close();
        System.out.println("Conexión Cerrada....");
    }
}
```

