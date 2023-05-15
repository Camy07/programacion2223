## EJEMPLOS CON FLUTTER

### EJEMPLO01: EJEMPLO BÁSICO
´´´java
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
´´´

### EJEMPLO 02: USANDO Material Design con Scaffold

´´´java
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
´´´







