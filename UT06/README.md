# UT06 : FICHEROS EN JAVA

La pieza más básica para poder operar con archivos, independientemente de su tipo, en un programa Java es la clase `File`. Esta clase pertenece al package `java.io`. Por lo tanto, será necesario importarla antes de poder usarla.

```java
import java.io.File;
```

Esta clase permite manipular cualquier aspecto vinculado al sistema de ficheros. Su nombre ("archivo", en inglés) es un poco engañoso, ya que no se refiere exactamente a un archivo.

> La clase File representa una ruta dentro del sistema de archivos.

Sirve para realizar operaciones tanto sobre rutas al sistema de archivos que ya existan como no existentes. Además, se puede usar tanto para manipular archivos como directorios.

Como cualquier otra clase hay que instanciarla para que sea posible invocar sus métodos. El constructor de File recibe como argumento una cadena de texto correspondiente a la ruta sobre la que se quieren llevar a cabo las operaciones.
```java
File f = new File (String ruta);
```

Una ruta es la forma general de un nombre de archivo o carpeta, por lo que identifica únicamente su localización en el sistema de archivos.

Cada uno de los elementos de la ruta pueden existir realmente o no, pero esto no impide en modo poder inicializar File. En realidad, su comportamiento es como una declaración de intenciones sobre qué ruta del sistema de archivos se quiere interactuar. No es hasta que se llaman los diferentes métodos definidos en File, o hasta que se escriben o se leen datos, que realmente se accede al sistema de ficheros y se procesa la información.

Es importante entender que un objeto representa una única ruta del sistema de ficheros. Para operar con diferentes rutas vez habrá que crear y manipular varios objetos. Por ejemplo, en el siguiente código se instancian tres objetos File diferentes.

File carpetaFotos = new File("C:/Fotos");
File unaFoto = new File("C:/Fotos/Foto1.png");
File otraFoto = new File("C:/Fotos/Foto2.png");

2.2. Rutas absolutas y relativas
En los ejemplos empleados hasta el momento para crear objetos del tipo File se han usado rutas absolutas, ya que es la manera de dejar más claro a qué elemento dentro del sistema de archivos, ya sea archivo o carpeta, se está haciendo referencia.

> Una ruta absoluta es aquella que se refiere a un elemento a partir del raíz del sistema de ficheros. Por ejemplo “C:/Fotos/Foto1.png”

> Una ruta relativa es aquella que no incluye el raíz y por ello se considera que parte desde el directorio de trabajo de la aplicación. Esta carpeta puede ser diferente cada vez que se ejecuta el programa.

Una ruta relativa siempre incluye el directorio de trabajo de la aplicación como parte inicial a pesar de no haberse escrito. El rasgo distintivo es que el directorio de trabajo puede variar. Por ejemplo, el elemento al que se refiere el siguiente objeto File varía según el directorio de trabajo.

File f = new File ("Unidad11/apartado1/Actividades.txt");

Este mecanismo permite facilitar la portabilidad del software entre distintos ordenadores y sistemas operativos, ya que solo es necesario que los archivos y carpetas permanezcan en la misma ruta relativa al directorio de trabajo. Veámoslo con un ejemplo:

File f = new File ("Activdades.txt");

Dada esta ruta relativa, basta garantizar que el fichero "Activdades.txt" esté siempre en el mismo directorio de trabajo de la aplicación, cualquiera que sea éste e independientemente del sistema operativo utilizado (en un ordenador puede ser "C:\Programas" y en otro "/Java"). En cualquiera de todos estos casos, la ruta siempre será correcta. De hecho, aún más. Nótese como las rutas relativas a Java permiten crear código independiente del sistema operativo, ya que no es necesario especificar un formato de raíz ligada a un sistema de archivos concreto ( "C:", "D:", "/", etc.) .


## 1. Lectura de ficheros:

La clase que permite llevar a cabo la lectura de datos desde un fichero orientado a carácter es exactamente la misma que permite leer datos desde el teclado: Scanner. Al fin y al cabo, los valores almacenados en los archivos de este tipo se encuentran exactamente en el mismo formato que ha usado hasta ahora para entrar información en sus programas: una secuencia de cadenas de texto. La única diferencia es que estos valores no se piden al usuario durante la ejecución, sino que se encuentran almacenados en un fichero.

Para procesar datos desde un archivo, el constructor de la clase Scanner permite como argumento un objeto de tipo File que contenga la ruta a un archivo.

Una vez instanciado el objeto Scanner podemos utilizar sus métodos exactamente igual que si leyéramos de teclado: hasNext(), next(), nextLine(), nextInt(), nextDouble(), nextBoolean(), etc. La única diferencia es que el objeto Scanner leerá secuencialmente el contenido del archivo.

Es importante entender que en el caso de un archivo, el objeto Scanner gestiona internamente un apuntador que indica sobre qué valor actuarán las operaciones de lectura. Inicialmente el apuntador se encuentra en el primer valor dentro del archivo. Cada vez que se hace una lectura el apuntador avanza automáticamente hasta el siguiente valor dentro del archivo y no hay ninguna manera de hacerlo retroceder. A medida que invocamos métodos de lectura el apuntador sigue avanzando hasta que hayamos leído tantos datos como queramos, o hasta que no podamos seguir leyendo porque hemos llegado al final del fichero.


Con estos conceptos en mente, y para resumir, el procedimiento para leer un archivo es el siguiente:
   - Primero, creamos una instancia de la clase `File` pasando la ruta del fichero como parámetro.
   - Luego,  generamos una instancia de la clase `Scanner` y se la pasamos el objeto `File` como parámetro.
   - Mediante el método `hasNextLine()` de la clase `Scanner`  y un bucle, vamos comprobando si hay más líneas en el fichero.
   - Dentro del bucle, usamos el método `nextLine()` de la clase `Scanner` para leer la siguiente línea del fichero.
   - Por último, cerramos el archivo para que deje de estar bloqueado por nuestro programa y lo liberamos para el resto de aplicaciones.

   Ejemplo:
   ```java
   import java.io.File;
   import java.io.FileNotFoundException;
   import java.util.Scanner;

   public class LecturaFichero {
       public static void main(String[] args) {
           try {
               File archivo = new File("ruta_del_fichero.txt");
               Scanner scanner = new Scanner(archivo);

               while (scanner.hasNextLine()) {
                   String linea = scanner.nextLine();
                   System.out.println(linea);
               }
               scanner.close();
           } catch (FileNotFoundException e) {
               e.printStackTrace();
           }
       }
   }
   ```

> **IMPORTANTE**: Al instanciar el objeto Scanner se lanzará una excepción de tipo `java.io.FileNotFoundException` si el fichero no existe. Siempre habrá que manejar dicha excepción mediante un try-catch. `Scanner` también puede lanzar otras excepciones, por ejemplo si se intenta leer el tipo de dato incorrecto (llamamos a `nextInt()` cuando no hay un entero, como sucede en la entrada por teclado,) o si hemos llegado al final del fichero e intentamos seguir leyendo (podemos comprobarlo mediante el método `hasNext()` de `Scanner`, que devuelve true si aún hay algún elemento que leer).

## 2. Escritura de ficheros:
Para escribir datos a un archivo la clase más sencilla de utilizar es FileWriter. Esta clase tiene dos constructores que merece la pena conocer:

- `public FileWriter(File file)`
- `public FileWriter(File file, boolean append)`

El primer constructor es muy parecido al del `Scanner`. Solo hay que pasarle un objeto `File` con la ruta al archivo. Al tratarse de escritura la ruta puede indicar un fichero que puede existir o no dentro del sistema. Si el fichero no existe, se creará uno nuevo. Pero si el fichero ya existe, su contenido se borra por completo, con tamaño igual a 0. Esto puede ser peligroso ya que si no se maneja correctamente puede producir la pérdida de datos valiosos. Hay que estar completamente seguro de que se quiere sobreescribir el fichero.

```java
import java.io.File;
import java.io.FileWriter;
...
File f = new File("C:\Programas\Unidad11\Documento.txt");
FileWriter writer = new FileWriter(f);
```

El segundo constructor tiene otro parámetro de tipo booleano llamado “append” (añadir) que nos permite indicar si queremos escribir al final del fichero o no. Es decir, si le pasamos “false” hará lo mismo que el contructor anterior (si el archivo ya existe, lo sobreescribirá), pero si le pasamos “true” abrirá el archivo para escritura en modo “append”, es decir, escribiremos al final del fichero sin borrar los datos ya existentes.

```java
import java.io.File;
import java.io.FileWriter;
...
File f = new File("C:\Programas\Unidad11\Documento.txt");
FileWriter writer = new FileWriter(f, true);
```


La escritura secuencial de datos en un fichero orientado a carácter es muy sencilla. Solo es necesario utilizar el siguiente método `void write(String str)` que escribirá la cadena str en el fichero. Si se desea agregar un final de línea se puede agregar "\n".

> Tanto el constructor de FileWriter como el método write() pueden lanzar una excepción IOException si se produce algún error inesperado.

Es importante tener en cuenta que para que el método write() escriba texto correctamente es imprescindible pasarle como argumento un `String`. Está permitido utilizar datos o variables distintas a `String`, pero se escribirá directamente su valor en bytes, no como texto. 

La escritura de datos en fichero tiene la particularidad de que una vez se ha escrito un dato ya no hay marcha atrás. No es posible escribir información antes o en medio de valores que ya están escritos.

Vamos a ver un ejemplo de escritura sencillo que utilizar la clase `FileWriter` . Los pasos que seguimos son:
   1. Creamos una instancia de la clase `FileWriter` pasando la ruta del fichero como parámetro.
   2. Utilizamo el método `write()` para escribir en el fichero.
   3. Finalmente, hemos de  **cerrar el fichero después de escribir** en él utilizando el método `close()`.

   ```java
   import java.io.FileWriter;
   import java.io.IOException;

   public class EscrituraFichero {
       public static void main(String[] args) {
           try {
               FileWriter archivo = new FileWriter("ruta_del_fichero.txt");
               archivo.write("Hola, mundo!");
               archivo.close();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
   }
   ```


Adicionalmente, podemos hacer uso de la clase `BufferedWriter`, que ofrece una mejor gestión de la escritura en el archivo. El proceso es análogo al anterior, salvo que ahora creamos el nuevo objeto y usamos sus métodos. La ventaja de este sistema es que podemos usar el método `newLine()` que genera una salto de linea en el archivo.

   ```java
   import java.io.BufferedWriter;
   import java.io.FileWriter;
   import java.io.IOException;

   public class EscrituraFichero {
       public static void main(String[] args) {
           try {
               BufferedWriter archivo = new BufferedWriter(new FileWriter("ruta_del_fichero.txt"));
               bw.write("Esto es una prueba usando Buffered");
               bw.newLine();
               bw.write("Seguimos usando Buffered");
               archivo.close();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
   }
   ```

> SIempre hay que acordarse de cerrar el archivo.

## 3. Borrado de archivos.
El borrado de archivos es similar a los anteriores, llamando claro está al método adecuado: `delete()`:

```java
import java.io.File;  

public class BorrarFichero {
  public static void main(String[] args) { 
    File myObj = new File("archivo.txt"); 
    if (myObj.delete()) { 
      System.out.println("Borrado el archivo: " + myObj.getName());
    } else {
      System.out.println("Error al borrar el archivo.");
    } 
  } 
}
```

> En este caso no hay que controlar las posibles excepciones, puesto que en caso de no existir o poder borrar el archivo, el método  devolverá `false`.

