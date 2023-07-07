# MegaLoginApp Android

Este proyecto de Android es un ejemplo de una app de inicio de sesión que consta de varias pantallas, incluyendo la vista de inicio de sesión, registro, recuperación de contraseña y la pantalla de inicio.

## Características y tecnologías utilizadas:

- **Clean Architecture**: El proyecto sigue los principios SOLID y utiliza Clean Architecture para una estructura de código modular y mantenible, se compone principalmente de 3 capas.
  - Capa de datos (data): En esta capa se encontraria todo lo relaciona a la fuente de datos, en este caso la conexion con la base de datos local (Room) para almacenar la informacion de los usuario. 
  - Capa de dominio (domain): En esta capa alojamos toda la logica de negocio principalmente contenida en los casos de uso.
  - Capa de presentacion (presentation): En esta capa tenemos todos los elementos visuales que se muestran en la UI, en este caso diseñada con Jetpack Compose.  
- **MVVM**: Se implementa el patrón de arquitectura Modelo-Vista-VistaModelo para una separación clara de responsabilidades y un flujo de datos eficiente.
- **Room**: Se utiliza la biblioteca Room para el almacenamiento local de datos, proporcionando una capa de abstracción para interactuar con la base de datos SQLite.
- **Compose**: La interfaz de usuario se ha desarrollado utilizando Jetpack Compose, el kit de herramientas moderno y declarativo para la creación de interfaces de usuario en Android.
- **Hilt**: Se utiliza Hilt como framework para la inyección de dependencias, facilitando la gestión y la creación de instancias de objetos en la app.
- **MockK**: La biblioteca MockK se utiliza para escribir pruebas unitarias y crear objetos simulados, permitiendo una mejor cobertura de pruebas y garantizando la calidad del código.
- **Media3 de ExoPlayer**: Se implementa Media3 de ExoPlayer para mostrar un video de fondo en la pantalla de inicio de sesión, brindando una experiencia visual atractiva y personalizada.
- **Lottie**: Lottie se utiliza para agregar animaciones a la interfaz de usuario, lo que mejora la experiencia del usuario y hace que la app sea más interactiva y atractiva.
- **Gradle Kotlin DSL**: El archivo de configuración Gradle está escrito en Kotlin DSL, lo que proporciona una sintaxis más concisa y segura para la configuración y la compilación del proyecto.

## Requisitos del proyecto:

- Android Studio Flamingo.
- Dispositivo o emulador con Android 7.0 (API nivel 24) o superior.

## Instrucciones de instalación y ejecución:

1. Clona este repositorio en tu máquina local.
2. Abre Android Studio y selecciona "Abrir un proyecto existente".
3. Navega hasta la carpeta clonada y selecciona el archivo de proyecto `build.gradle` en la raíz del proyecto.
4. Android Studio importará automáticamente el proyecto.
5. Conecta tu dispositivo Android o inicia un emulador.
6. Haz clic en el botón "Ejecutar" en Android Studio y selecciona tu dispositivo/emulador.
7. La app se instalará y se ejecutará en tu dispositivo/emulador.

## Como probar:

Si bien en la aplicacion se puede crear una cuenta para realizar las respectivas pruebas, tambien se crearon unos objetos de prueba.

- Usuario de prueba:
(id = 0
name = "Usuario de prueba"
email = "usuario@prueba.com"
password = "megalogin")

- Email de prueba para errores: error@error.com

## Evidencias:
<img src="https://github.com/DavidMerchan93/MegaLoginApp/assets/33288144/9db6c47b-d571-45a1-ba75-59700fbd11e6" width="200">
<img src="https://github.com/DavidMerchan93/MegaLoginApp/assets/33288144/dc029c04-cd47-41df-962d-1b2ce17b88f2" width="200">
<img src="https://github.com/DavidMerchan93/MegaLoginApp/assets/33288144/bd9c32bb-f0e6-42e7-8f0f-a851f94b36fa" width="200">
<img src="https://github.com/DavidMerchan93/MegaLoginApp/assets/33288144/05c504f7-5883-4832-8f76-4c132390966d" width="200">

<img src="https://github.com/DavidMerchan93/MegaLoginApp/assets/33288144/6adfc6dc-a02d-42d4-9dc5-722207bb3c4a" width="200">



## Licencia:

Este proyecto está licenciado bajo la [Licencia MIT](https://opensource.org/licenses/MIT).
