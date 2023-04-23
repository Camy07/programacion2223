# IMPLEMENTANDO EL PATRÓN MVC CON JBDC


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

    /**
     * Método para conectarnos a la base de datos
     * @return el ID de la conexión
     * @throws SQLException
     */
    public Connection hazConnection() throws SQLException {
        Connection con = null;

        // Creamos la url para la conexión
        String urlConexion = driver + host + ":" +
        port + "/" + database;

        // conectamos a la base de datos
        con = DriverManager.getConnection(urlConexion, user, password);

		 //devolvemos el identificador
        return con;
    }
}

```
Como vemos, el método creado lanza una excepción en caso de que se produzca un error al conectarse. Un bloque `try-catch` estándar puede detectar excepciones de java.sql.SQLException. Su código no se compilará si no se manejan estas excepciones. Es una buena idea manejarlos correctamente para evitar que nuestra aplicación se bloquee si se lanza una de estas excepciones. 

Es muy recomendable que cuando manejemos objetos java.sql.Connection gestionemos los errores para garantizar que las excepciones de la base de datos se manejen correctamente. A menudo se requieren bloques de `try-catch` anidados para manejar todas las posibles excepciones. Debe asegurarse de que las conexiones se cierren una vez que se haya realizado el trabajo y el objeto Connection ya no se utilice. Del mismo modo, también es una buena idea cerrar los objetos java.sql.Statement para la limpieza de la asignación de memoria.

Debido a que los objetos Statement y Connection deben cerrarse, es común ver que los bloques `try-catch-finally` aseguran que todos los recursos se hayan atendido según sea necesario. Es probable que veas un código JDBC más antiguo que se asemeja al siguiente estilo.

Podemos aprovechar la declaración `try-with-resources`, que permite descargar la gestión de recursos a Java, en lugar de realizar cierres manuales. El siguiente código muestra cómo usar los recursos `try-with-resources` para abrir una conexión, crear una declaración y luego cerrar tanto la conexión como la instrucción cuando haya terminado.

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

