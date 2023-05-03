# IMPLEMENTANDO EL PATRÓN MVC CON JBDC

## 1. EL MODELO
### 1.1 El archivo de conexión
Si queremos seguir el patron MVC, podemos crear una clase específica para la conexión a la base de datos que quedaría así:


```java
package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CrearConexion {

    //Parámetros de la conexión
    private String driver = "jdbc:mysql://";
    private String host="localhost";
    private String port="3306";
    private String database="apressBooks";
    private String user = "usuario3PAW";
    private String password = "P@ssw0rd";
    private Connection con;

    /**
     * Método para conectarnos a la base de datos
     * @return el ID de la conexión
     * @throws SQLException
     */
    public CrearConexion() throws SQLException {
        Connection con = null;

        // Creamos la url para la conexión
        String urlConexion = driver + host + ":" + port + "/" + database;

        // conectamos a la base de datos
        con = DriverManager.getConnection(urlConexion, user, password);

    }
}

```
Como vemos, el método creado lanza una excepción en caso de que se produzca un error al conectarse. Un bloque `try-catch` estándar puede detectar excepciones de java.sql.SQLException. Su código no se compilará si no se manejan estas excepciones. Es una buena idea manejarlos correctamente para evitar que nuestra aplicación se bloquee si se lanza una de estas excepciones. 

Es muy recomendable que cuando manejemos objetos java.sql.Connection gestionemos los errores para garantizar que las excepciones de la base de datos se manejen correctamente. A menudo se requieren bloques de `try-catch` anidados para manejar todas las posibles excepciones. Debemos asegurarnos que las conexiones se cierren una vez que se haya realizado el trabajo y el objeto Connection ya no se utiliza. Del mismo modo, también es una buena idea cerrar los objetos java.sql.Statement para la limpieza de la asignación de memoria.



### 1.2. El POJO

Un POJO, acrónimo de Plain Old Data Object se usa en java para modelizar un registro de una tabla de manera sencilla, evitando el atacar directamente a la base de datos y los problemas que eso conlleva. Puedes encontrar más información sobre este tipo de estructura en Internet, esppecialmente en [este enlace](https://www.javatpoint.com/pojo-in-java).

Para el ejemplo actual, vamos a representar un registro de la tabla 'publication'. Usaremos como atributos los campos de la tabla y añadiremos los getter y setter correspondientes, además de los métodos de conversión necesarios. El código quedaría de la siguiente manera:

```java
package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Publication {
    private final Integer id;
    private String bookTitle;
    private String publishDate;
    private String publishCo;

    
    public Publication(Integer id) {
        this.id = id;
    }    

    public Publication(Integer id, String bookTitle, String publishDate, String publishCo) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.publishDate = publishDate;
        this.publishCo = publishCo;
    }

    public Integer getId() {
        return id;
    }
    public String getBookTitle() {
        return bookTitle;
    }
    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }
    public String getPublishDate() {
        return publishDate;
    }
    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }
    public String getPublishCo() {
        return publishCo;
    }
    public void setPublishCo(String publishCo) {
        this.publishCo = publishCo;
    }

    @Override
    public String toString() {
        return "Publication [id=" + id + 
        ", Título=" + bookTitle + 
        ", fechaPublicación=" + publishDate + 
        ", Editorial=" + publishCo + "]";
    }

    public static Publication fromResulSet (ResultSet rs) throws SQLException {
        //obtengo los datos del resultado
        String bookTitle = rs.getString("book_title");
        String publishDate = rs.getString("publish_date");
        String publishCo = rs.getString("publish_co");
        Integer id = rs.getInt("id");

        //Lo asigno a un objeto Publication
        Publication p = new Publication(
            id , bookTitle, publishDate,
            publishCo
        );

        return p;
    }
    
}
```

Como se puede observar, no hay nada referente a conexiones a base de datos, sólo un objeto plano.

Hemos creado el método toString para generar los String que vamos a pasar al controlador y los resulset para pasar a la conexión con la BBDD.

### 1.3 El Repositorio

Si un POJO representa un elemento de la tabla, necesitamos otra estructura que represente toda la tabla, es decir la colecciión de registros completa. Para ello entra en acción el repositorio, una estructura que organiza la colección de todos los registro de manera transparente al controlador. 

Existen diferentes frameworks que implementan repositorios, pero aquí lo vamos a diseñar 'ad-hoc' para el proyecto actual. Nuestro repositorio va a manejar listas de objetos personas que se cominicarán con la base de datos mendiante la clase 'conexion' creada previamente y con el controlador que veremos más adelante.

Asi, nuestro repositorio crearía un 

```java
package modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PublicationRepository {
    
    //La colección de libros de la base de datos
    private ArrayList<Publication> repositorio = 
        new ArrayList<Publication>();
    
    private CrearConexion miConexion;

    public PublicationRepository() {
        
        try (Connection conexion = miConexion.hazConnection();
        Statement sentencia = conexion.createStatement();) {
            //sentencia e ejecutar
            String query = "select * from publication";
            //ejecuto la sentencia y guardo el resultado en rs
            ResultSet rs = sentencia.executeQuery(query);

            while (rs.next()) {
                //Creo un objeto con el resultado actual
                Publication p = Publication.fromResulSet(rs);
                //Lo agregamos a la colección
                repositorio.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Error al conectar");
        }
    }


    public Publication buscarPorId(Integer id) {
        Publication p = null;
        for (Publication pub : repositorio) {
            if (pub.getId() == id) {
                p = pub;
                break;
            }
        }
        return p;
    }

    public ArrayList<Publication> buscarPorTitulo (String titulo) {
        
        var devolver = new ArrayList<Publication>();

        try (Connection conexion = miConexion.hazConnection();
        Statement sentencia = conexion.createStatement();) {
            //sentencia e ejecutar
            String query = "select * from publication where " +
                "book_title like %" + titulo + "%";
            //ejecuto la sentencia y guardo el resultado en rs
            ResultSet rs = sentencia.executeQuery(query);

            while (rs.next()) {
                //Creo un objeto con el resultado actual
                Publication p = Publication.fromResulSet(rs);
                //Lo agregamos a la colección
                devolver.add(p);
            }
            

        } catch (SQLException e) {
            System.out.println("Error al conectar");
        }
        
        return devolver;

    }
}
```









Para mejorar la ejecución dePodemos aprovechar la declaración `try-with-resources`, que permite descargar la gestión de recursos a Java, en lugar de realizar cierres manuales. El siguiente código muestra cómo usar los recursos `try-with-resources` para abrir una conexión, crear una declaración y luego cerrar tanto la conexión como la instrucción cuando haya terminado.

```java
try (Connection conn = createConn.getConnection();
          Statement stmt = conn.createStatement();) {
    ResultSet rs = stmt.executeQuery(qry);
    while (rs.next()) {
        // ACCIONES CON LOS RESULTADOs
    }
} catch (SQLException e) {
    e.printStackTrace();
}
```


