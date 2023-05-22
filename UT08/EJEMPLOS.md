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
### EJEMPLO 05: AÑADIENDO UN BOTÓN

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
            ElevatedButton(
              style: ButtonStyle(
                foregroundColor: MaterialStateProperty.all<Color>(Colors.lime),
              ),
              onPressed: () { },
              child: Text('Contacto'),
            )

          ])
        )
      )
    );
  }
}
```


### EJEMPLO 05  : DANDO FUNCIONALIDAD AL BOTÓN


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
      home: homePage(),
    );
  }
}

class homePage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
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
                  ElevatedButton(
                    style: ButtonStyle(
                      foregroundColor: MaterialStateProperty.all<Color>(Colors.lime),
                    ),
                    onPressed: () => showAlertDialog(context),
                    child: Text('TextButton'),
                  )
                ])
        )
    );
  }
}



showAlertDialog(BuildContext context) {
  // Create button
  Widget okButton = TextButton(
    child: Text("OK"),
    onPressed: () {
      Navigator.of(context).pop();
    },
  );

  // Create AlertDialog
  AlertDialog alert = AlertDialog(
    title: Text("Simple Alert"),
    content: Text("This is an alert message."),
    actions: [
      okButton,
    ],
  );

  // show the dialog
  showDialog(
    context: context,
    builder: (BuildContext context) {
      return alert;
    },
  );
}
```


## EJEMPLO 06 : USANDO ESTADOS

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
      home: MyHomePage(title: 'Ejemplo Aplicacion'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _counter = 0;
  String _pepe = "1492";

  void _incrementCounter() {
    setState(() {
      _counter++;
    });
  }

  void _cambiaNombre() {
    setState(() {
      if (_counter%2==0) {
        _pepe = "Juan";
      }
      else {
        _pepe="pepe";
      }
      _incrementCounter();
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(title: Text("Mi primera Aplicacion"),
            backgroundColor: Colors.red),
        body: Center (
            child: Column (
                children: [
                  const Text("PREGUNTA" ,
                    style: TextStyle(
                        fontSize: 34,
                        color: Colors.brown,
                        fontWeight: FontWeight.bold,
                        fontStyle: FontStyle.italic
                    ),
                  ),
                  const Text("¿EN QUE AÑO SE DESCUBRIÓ AMERICA?"),
                  Image.network('https://picsum.photos/250?image=9'),
                  ElevatedButton(
                    style: ButtonStyle(
                      foregroundColor: MaterialStateProperty.all<Color>(Colors.lime),
                    ),
                    onPressed: () => showAlertDialog(context, "1492"),
                    child: Text('1492'),
                  ),
                  ElevatedButton(
                    style: ButtonStyle(
                      foregroundColor: MaterialStateProperty.all<Color>(Colors.lime),
                    ),
                    onPressed: () => showAlertDialog(context, "1942"),
                    child: Text('1942'),
                  ),
                  ElevatedButton(
                    style: ButtonStyle(
                      foregroundColor: MaterialStateProperty.all<Color>(Colors.lime),
                    ),
                    onPressed: () => showAlertDialog(context, "2001"),
                    child: Text('2001'),
                  ),
                ])
        )
    );
  }


  showAlertDialog(BuildContext context, String valor) {
    // Create button
    Widget okButton = TextButton(
      child: Text("OK"),
      onPressed: () {
        Navigator.of(context).pop();
      },
    );

    String respuesta="FALLASTE";

    if (valor==_pepe) {
      respuesta="ACERTASTE";
    }
    // Create AlertDialog
    AlertDialog alert = AlertDialog(
      title: Text(valor),
      content: Text(respuesta),
      actions: [
        okButton,
      ],
    );

    // show the dialog
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return alert;
      },
    );
  }

}

```






