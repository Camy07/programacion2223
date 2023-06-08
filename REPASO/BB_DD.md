## Ejercicio Bases de Datos

Crea una aplicación que gestione los artículos de una tienda. Para ello hay que crear una tabla llamada `articulos` con los siguientes campos: 

  - id (integer)(clave primaria - autoincremento)
  - nombre (string).
  - precio (intenger) (es el precio sin IVA).
  - cuantosQuedan (integer) (representa cuantos quedan en el almacén).

Además habrá otra tabla de promociones con los siguientes campos:

  - id (integer)(clave primaria - autoincremento)
  - id_producto (integer) (referencia al producto en promoción).
  - precio_final (intenger) (precio final una vez aplicacdo el iva y el descuento)

La aplicación deberá implementar las siguientes funcionalidades:

  - Listado de todos los artículos por pantalla.
  - Listado de artículos en promoción.
  - Búsqueda de un artículo en concreto y si tiene o no una promoción.
  - Creación, modificación borrado de artículos.
  - Creación, modificación borrado de promociones.
