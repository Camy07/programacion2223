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

Hemos creado el método toString para generar los String que vamos a pasar al controlador y los Resultset para pasar a la conexión con la BBDD.

### 1.3 El Repositorio

Si un POJO representa un elemento de la tabla, necesitamos otra estructura que represente toda la tabla, es decir la colección de registros completa. Para ello entra en acción el repositorio, una estructura que organiza la colección de todos los registro de manera transparente al controlador. 

Existen diferentes frameworks que implementan repositorios, pero aquí lo vamos a diseñar 'ad-hoc' para el proyecto actual. Nuestro repositorio va a manejar listas de objetos personas que se comunicarán con la base de datos mediante la clase 'conexion' creada previamente y con el controlador que veremos más adelante.

Asi, nuestro repositorio crearía un ArrayList de objetos `Publication` y guardaría en cada uno de ellos un registro de la base de datos. Todos los cambios que se hagan en la App, se deberán volcar luego a la base de datos.

La clase `PublicationRepository` debe implementar todas las acciones necesarias para poder hacer un CRUD de la base de datos con garantías.

Un ejemplo sin terminar es el que tenemos a continuación:


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

    //Id del proximo elemento a insertar
    private int proximoId;

    // nos conectamos a la base de datos
    CrearConexion miConexion = new CrearConexion();

    public PublicationRepository() {        
        try (Connection conexion = miConexion.hazConnection();
        Statement sentencia = conexion.createStatement();) {
            //sentencia e ejecutar
            String query = "select * from publication";
            //ejecuto la sentencia y guardo el resultado en rs
            ResultSet rs = sentencia.executeQuery(query);
            Integer id=0;
            while (rs.next()) {
                //obtengo los datos del resultado
                String bookTitle = rs.getString("book_title");
                String publishDate = rs.getString("publish_date");
                String publishCo = rs.getString("publish_co");
                id = rs.getInt("id");

                //Lo asigno a un objeto Publication
                Publication p = new Publication(
                    id , bookTitle, publishDate,
                    publishCo
                );
                //Lo agregamos a la colección
                repositorio.add(p);
            }
			  // obtengo el id del proximo elemento que inserte
            proximoId = id + 1;

        } catch (SQLException e) {
            System.out.println("Error al conectar");
            e.printStackTrace();
        }
    }


    public int getProximoId() {
        return proximoId;
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


    public ArrayList<Publication> getRepositorio() {
        return repositorio;
    }
    
    public void insertar(Publication libro) {
        repositorio.add(libro);
        proximoId++;
    }


	public void modificar(Integer id , Publication libro) {
    	// POR HACER
    }
    
    


    public void grabarLibro(Publication libro) {

        String query = "";

        try (Connection conexion = miConexion.hazConnection();
        Statement sentencia = conexion.createStatement();) {
            query = "INSERT INTO publication VALUES (0 , '" 
            + libro.getBookTitle() + "' , '" + 
            libro.getPublishDate()+ "' , '" +
            libro.getPublishCo() + "')";
            sentencia.executeUpdate(query);
            // close connection
            sentencia.close();            
        }
        catch (Exception e){
            System.out.println("Error->" + query);
        }

    }

    public void grabarRepositorio () {
        try (Connection conexion = miConexion.hazConnection();
        Statement sentencia = conexion.createStatement();) {
            String borrado = "TRUNCATE TABLE publication";
            sentencia.executeUpdate(borrado);
            // close connection
            sentencia.close();
        }
        catch (Exception e){
            System.out.println("Error al borrar");
        }
        for (Publication libro : repositorio) {
            grabarLibro(libro);
        }
    }

    

}
```

Para mejorar la ejecución, hemos aprovechado la declaración `try-with-resources`, que permite descargar la gestión de recursos a Java, en lugar de realizar cierres manuales. 

## 2. EL CONTROLADOR
El controlador se va a encargar de la comunicación entre el modelo y la vista, y en este caso se realizará mediante el siguiente flujo:

- Con el modelo(repositorio) se comunica usando objetos `Publication` o ArrayList de objetos `Publication`.
- Con la vista (App) se comunica usando `String` o ArrayList de `String`.

Por tanto el controlador debe hacer las conversiones necesarias, asi como la comprobación de los datos recibidos, sin son adecuados y/o válidos y mandar los mensajes correspondientes a la App para que los muestre por pantalla.

Con esta filosofía en mente, el controlador (sin completar) podría tener la siguiente estructura:

```java
package controlador;

import java.util.ArrayList;

import modelo.Publication;
import modelo.PublicationRepository;

public class Publicaciones {

    private PublicationRepository repoLibros = new PublicationRepository();

    public String buscarPorId (Integer id) {
        Publication libro = repoLibros.buscarPorId(id);

        if (libro != null) {
            return "Libro encontrado -> \n" +
                libro.toString();
        } else {
           return "No se encontró el libro";
        }
    }

    public ArrayList<String> buscarPorTitulo(String titulo) {
        ArrayList<String> lista = new ArrayList<>();

        for (Publication libro: repoLibros.getRepositorio()) {
            if (libro.getBookTitle().contains(titulo)) {
                
                lista.add(libro.toString());
            }
        }

        return lista;
    }

    public void insertaLibro(String titulo , String fecha , String editorial) {
        int id = repoLibros.getProximoId();
        var libro = new Publication( id ,titulo, 
        fecha , editorial);
        repoLibros.insertar(libro);
        repoLibros.grabarRepositorio();
    }


    public String modificaLibro( Integer id , String titulo , String fecha , String editorial) {
        Publication libro_original = repoLibros.buscarPorId(id);

        if (libro_original != null) {
            var libro = new Publication( id ,titulo,
                    fecha , editorial);
            if (repoLibros.modificar(id, libro)) {
                return "Libro modificado -> \n" +
                        libro.toString();
            }
            else {
                return "Error al modificar";
            }

        } else {
            return "No se encontró el libro";
        }

    }

    public String borraLibro( Integer id) {
        Publication libro = repoLibros.buscarPorId(id);

        if (libro != null) {
            var res = repoLibros.borrar(id);
            if (res) return "Libro borrado -> \n" +  libro.toString();
            else return "Error al borrar";
        } else {
            return "No se encontró el libro";
        }

    }
}
```

## 3. LA VISTA
La vista, como sabemos, se comunicará con el controlador para mostrar los datos por pantalla y recibirlos por teclado.

Hará las llamadas correspondientes al controlador enviado y recibiendo  Strings y/o colecciones de Strings.

La estructura del archivo podría ser la siguiente:

```java
import java.util.ArrayList;
import java.util.Scanner;

import Vista.Menu;
import controlador.Publicaciones;

class App {
    static Scanner sc = new Scanner(System.in);

    static Menu miMenu;

    static int opcion;

    static Publicaciones biblioteca = new Publicaciones();
    public static void main(String[] args) throws Exception {
        creaMenu();
        do {
            miMenu.verMenu("GESTIÓN DE LIBROS");
            opcion = miMenu.leerOpcion();
            switch (opcion) {
                case 1:
                    listar();
                    break;
                case 2:
                    buscarPorTitulo();
                    break;
                case 3:
                    insertar();
                    break;
                case 4:
                    modificar();
                    break;
                case 5:
                    borrar();
                    break;
                default:
                    break;
            }
        } while (opcion!=0);
    }

    public static void listar () {
        var listado = biblioteca.obtenerListado();
        for (String linea: listado) {
            System.out.println(linea);
        }

    }
    public static void buscarporId() {
        System.out.print("Escribe el id a buscar: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.println(biblioteca.buscarPorId(id));
    }

    public static void buscarPorTitulo() {
        System.out.print("Escribe el titulo a buscar: ");
        String titulo = sc.nextLine();

        var resultado = biblioteca.buscarPorTitulo(titulo);

        if (resultado.size()>0) {
            System.out.println("Encontrados " + 
                resultado.size() + 
                " resultados:");
            for (String libro : resultado) {
                System.out.println(libro);
            }
        }
        else {
            System.out.println("No se encontró ningún resultado");
        }
    }

    public static void insertar() {
        System.out.println("Escribe el titulo a insertar: ");
        String tituloL = sc.nextLine();

        System.out.print("Escribe la fecha: ");
        String fechaL = sc.nextLine();

        System.out.print("Escribe la editorial: ");
        String editorialL = sc.nextLine();

        biblioteca.insertaLibro(tituloL, fechaL, editorialL);
    }


    public static void borrar () {
        System.out.print("Escribe el id del libro a borrar: ");
        Integer id = sc.nextInt();
        sc.nextLine();

        System.out.println(biblioteca.borraLibro(id));
    }
    public static void modificar () {
        System.out.print("Escribe el id del libro a modificar: ");
        Integer id = sc.nextInt();
        sc.nextLine();

        System.out.print("Escribe nuevo titulo: ");
        String titulo = sc.nextLine();

        System.out.print("Escribe la fecha a modificar: ");
        String fecha = sc.nextLine();

        System.out.print("Escribe la editorial a modificar: ");
        String editorial = sc.nextLine();

        System.out.println(biblioteca.modificaLibro(id , titulo, fecha, editorial));
    }

    private static void creaMenu() {
        var listaOpciones = new ArrayList<String>();
        listaOpciones.add("VER LISTADO LIBROS");
        listaOpciones.add("BUSCAR LIBRO");
        listaOpciones.add("AÑADIR LIBRO");
        listaOpciones.add("MODIFICAR LIBRO");
        listaOpciones.add("BORRAR LIBRO");
        miMenu = new Menu(listaOpciones);

    }
}
```

Para mostrar el menú hemos usado la clase Menu siguiente:

```java
package Vista;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    ArrayList<String> listaOpciones;
    private final String linea="****************************************************************************";

    public Menu(ArrayList<String> listaOpciones) {
        this.listaOpciones = listaOpciones;
    }

    public void verMenu(String titulo) {
        Integer huecos = linea.length() - titulo.length();
        Integer mitadA = (huecos/2)-1;
        Integer mitadB= mitadA;
        if ( huecos%2 > 0 ) {
             mitadB = mitadA+1;
        }
        System.out.println(linea);
        System.out.print("*");
        for (int i=0; i<mitadA ; i++) {
            System.out.print(" ");
        }
        System.out.print(titulo);
        for (int i=0; i<mitadB ; i++) {
            System.out.print(" ");
        }
        System.out.println("*");
        System.out.println(linea);
        int contador = 1;
        for (String opcion : listaOpciones) {
            System.out.println(contador + ": " + opcion);
            contador++;
        }
        System.out.println("0: SALIR");
    }

    public int leerOpcion() {
        System.out.println("------------------------------");
        System.out.print("ELIGE TU OPCIÓN: ");
        Scanner sc = new Scanner(System.in);
        int opcion = -1;
        try {
            var leido = sc.nextLine();
            opcion = Integer.parseInt(leido);
        }
        catch (Exception e) {
            System.out.println("VALOR NO VÁLIDO");
        }
        return opcion;
    }
}
```


