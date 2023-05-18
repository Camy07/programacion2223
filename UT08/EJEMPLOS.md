## EJEMPLOS CON FLUTTER

### EJEMPLO01: EJEMPLO BÁSICO
```java
import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return const Center(
      child: Text("Hola Mundo",
      textDirection: TextDirection.ltr)
    );
  }
}
```

### EJEMPLO 02: USANDO Material Design con Scaffold

```java
import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "Mi primera aplicacion",
      home: Scaffold(
        appBar: AppBar(title: Text("Mi primera Aplicacion"),
        backgroundColor: Colors.red),
        body: const Center (
          child: Text("ESTA ES MI PÁGINA"),
        )

      )
    );
  }
}
```

### EJEMPLO 03: USANDO COLUMNAS

```java
import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "Mi primera aplicacion",
      home: Scaffold(
        appBar: AppBar(title: Text("Mi primera Aplicacion"),
        backgroundColor: Colors.red),
        body: Center ( 
          child: Column (
          children: const [ 
            Text("ESTA ES MI PÁGINA" ,
              style: TextStyle(
                fontSize: 34,
                color: Colors.brown,
                fontWeight: FontWeight.bold,
                fontStyle: FontStyle.italic
              ),
            ),
            Text("SEGUNDO TEXTO")
          ])
        )
      )
    );
  }
}
```

### EJEMPLO 04: AÑADIENDO UNA IMAGEN

```java
import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "Mi primera aplicacion",
      home: Scaffold(
        appBar: AppBar(title: Text("Mi primera Aplicacion"),
        backgroundColor: Colors.red),
        body: Center ( 
          child: Column (
          children: [
            const Text("ESTA ES MI PÁGINA" ,
              style: TextStyle(
                fontSize: 34,
                color: Colors.brown,
                fontWeight: FontWeight.bold,
                fontStyle: FontStyle.italic
              ),
            ),
            const Text("SEGUNDO TEXTO"),
            Image.network('https://picsum.photos/250?image=9'),
          ])
        )
      )
    );
  }
}
```





