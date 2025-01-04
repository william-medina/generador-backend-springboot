# Sistema de Monitoreo de Generador - Backend (Spring Boot API)
![Framework](https://img.shields.io/badge/Framework-Spring%20Boot-green)
![Version](https://img.shields.io/badge/version-1.0.0-green)
![Database](https://img.shields.io/badge/Database-MySQL-orange)
![Language](https://img.shields.io/badge/Language-Java%2021-white)
![API Documentation](https://img.shields.io/badge/API%20Docs-Swagger-green)

## Descripción

Esta API, desarrollada con **Spring Boot**, gestiona la recepción y almacenamiento de los datos provenientes de un sistema embebido basado en **Raspberry Pi** y **microcontrolador**, diseñado específicamente para monitorear el funcionamiento de una **planta eléctrica**. El sistema embebido, programado en **Python**, se encarga de leer los datos de los sensores y los envía a la API en tiempo real. Además, también se gestionan eventos como la pérdida de energía, que son registrados por la API.

La API almacena estos datos en una base de datos **MySQL** y proporciona una capa de seguridad mediante **API Keys**. Estas claves permiten al sistema embebido enviar y almacenar datos de manera segura, asegurando que solo dispositivos autorizados puedan interactuar con la API. Además, la API ofrece endpoints para consultar los registros históricos de eventos y acceder a los datos en tiempo real.

El frontend de la aplicación, desarrollado en **React**, se conecta a esta API para visualizar los datos en tiempo real y consultar el historial de eventos, sin necesidad de utilizar las **API Keys** para su funcionamiento.


## Características

- **Recepción en tiempo real**: La API recibe datos de sensores en tiempo real cada segundo, que son enviados por el sistema embebido (Raspberry Pi).
- **Eventos**: La API gestiona eventos generados por el sistema embebido, como la pérdida de energía, y los almacena en la base de datos.
- **Autenticación mediante API Keys**: El sistema embebido utiliza **API Keys** para autenticar y autorizar el envío de datos a la API.
- **Historial de eventos**: La API guarda un historial de todos los eventos generados para su consulta posterior.
- **Acceso al frontend**: El sistema permite que un frontend en React consuma los datos en tiempo real y consulte los registros de eventos, sin requerir autenticación mediante API Keys para la visualización.
- **Seguridad**: La API utiliza **Spring Security** para asegurar que solo las API Keys válidas puedan registrar y almacenar datos de sensores.

## API Keys

Para poder generar una **API Key**, primero debes **iniciar sesión** en el sistema. Una vez autenticado, podrás crear una nueva API Key. El proceso es el siguiente:

1. **Inicia sesión**: Asegúrate de estar autenticado en el sistema.
2. **Genera la API Key**: Una vez dentro, utiliza el endpoint correspondiente para crear la API Key.
3. **Visualización**: Al crear la API Key, el sistema te mostrará el **nombre** que le asignaste y su **clave**.

   > **Importante:** **La clave solo será visible en ese momento.** Después de este proceso, la API Key se ocultará parcialmente (por ejemplo, "dfcf...uit") por razones de seguridad y no podrás volver a verla.


## Tecnologías

El proyecto está construido utilizando las siguientes tecnologías:

- **Spring Boot**: Un potente framework basado en Java para la construcción de aplicaciones web, que proporciona una sintaxis elegante y características robustas para el desarrollo rápido.
- **MySQL**: Una base de datos relacional que se utiliza para almacenar y gestionar los datos de libros y usuarios en el sistema.
- **Hibernate**: Una herramienta de Mapeo Objeto-Relacional (ORM) utilizada para la interacción con la base de datos, facilitando el acceso a los datos mediante objetos Java en lugar de consultas SQL.
- **Spring Security**: Un módulo de seguridad en Spring que proporciona una manera de autenticar y autorizar usuarios, asegurando las aplicaciones web con control de acceso.
- **Spring Data JPA**: Proporciona integración con JPA (Java Persistence API) y facilita el uso de bases de datos en Spring mediante repositorios y entidades.
- **JWT (JSON Web Tokens)**: Utilizado para la autenticación y autorización de usuarios, generando tokens seguros para mantener las sesiones activas.
- **Swagger (Springdoc OpenAPI)**: Herramienta que genera documentación interactiva de la API de manera automática, permitiendo visualizar los endpoints, sus parámetros y respuestas.
- **Lombok**: Una biblioteca Java que reduce el boilerplate code generando automáticamente métodos como `getters`, `setters`, y `constructores` mediante anotaciones.

## Requisitos Previos

Antes de comenzar, asegúrate de haber cumplido con los siguientes requisitos:

- [Java](https://www.oracle.com/java/technologies/javase-downloads.html) (Recomendado: Java 21 o una versión compatible)
- [Maven](https://maven.apache.org/install.html) (Gestor de proyectos para Java)
- [MySQL](https://dev.mysql.com/downloads/installer/) (Instalado y funcionando localmente o usando una instancia en la nube)

## Instalación

1. **Clona el repositorio:**

    ```bash
    git clone https://github.com/william-medina/generador-backend-springboot.git
    ```

2. Navega al directorio del proyecto:

    ```bash
    cd generador-backend-springboot
    ```

3. **Instala las dependencias:**

   Asegúrate de que las dependencias del proyecto estén instaladas. Esto se puede hacer automáticamente utilizando el archivo `pom.xml` de Maven, el cual gestionará las dependencias necesarias para la aplicación.


4. **Configura las variables de entorno:**

   Agrega las siguientes variables de entorno necesarias para el funcionamiento de la aplicación. Esto puede hacerse ya sea en tu IDE o en tu sistema operativo:

    ```dotenv
    # Base de Datos
    DB_URL=jdbc:mysql://localhost:3306/your_database_name
    DB_USERNAME=your_username
    DB_PASSWORD=your_password

    # Clave Secreta para JWT
    JWT_SECRET=your_secret_jwt
    ```

   Reemplaza los valores de ejemplo con los detalles de tu configuración real.


5. **Inicia la aplicación:**

   La clase principal de la aplicación es `GeneradorApplication`. Ejecuta esta clase para iniciar el servidor.




## Arquitectura

La API sigue la arquitectura **Modelo-Vista-Controlador (MVC)**:

### 1. **Modelo**

- **Ubicación:** `src/main/java/com/williammedina/generador/models`
- **Responsabilidades:** Define la estructura de datos para la aplicación, maneja interacciones con la base de datos usando **JPA** (Java Persistence API) y **Hibernate**, e implementa la lógica de negocio relacionada con los datos.

### 2. **Vista**

- **Ubicación:** No aplicable directamente; las APIs de Spring Boot típicamente devuelven respuestas JSON, que sirven como "vista."
- **Responsabilidades:** Proporciona respuestas JSON formateadas para solicitudes API, que son consumidas por el frontend u otros servicios.

### 3. **Controlador**

- **Ubicación:** `src/main/java/com/williammedina/generador/controllers`
- **Responsabilidades:** Procesa las solicitudes entrantes, se comunica con los modelos (repositorios) para manejar los datos y devuelve respuestas al cliente (generalmente en formato JSON) utilizando anotaciones como `@RestController` y `@RequestMapping`.

### Documentación de la API
- La documentación de la API está disponible a través de [Swagger UI](http://localhost:8080/api/docs/swagger-ui/index.html) una vez que la aplicación esté en funcionamiento. Esta herramienta te permite explorar todos los endpoints disponibles y realizar pruebas directamente desde tu navegador, facilitando la interacción con la API.
   ```
   http://localhost:8080/api/docs/swagger-ui/index.html
   ```

## API Endpoints

### Autenticación

| **Endpoint**                 | Método  | Descripción                                                  |
|------------------------------|-------- |--------------------------------------------------------------|
| `/api/auth/login`            | `POST`  | Autentica a un usuario y devuelve un token JWT.              |
| `/api/auth/me`               | `GET`   | Obtiene los detalles del usuario autenticado actualmente.    |

---

### API Keys

| Endpoint                     | Método  | Descripción                         |
|------------------------------|---------|-------------------------------------|
| `/api-keys`                  | `POST`    | Crea una nueva API Key.             |
| `/api-keys`                  | `GET`     | Lista todas las API Keys.           |
| `/api-keys/{id}`             | `DELETE`  | Elimina una API Key específica.     |
| `/api-keys/{id}`             | `PATCH`   | Activa o desactiva una API Key.     |

---

### Sensores

| Endpoint                     | Método  | Descripción                                 |
|------------------------------|---------|---------------------------------------------|
| `/sensors`                   | `GET`     | Recupera una lista de todos los eventos registrados junto con los valores correspondientes de los sensores. |
| `/sensors/{api_key}`         | `POST`    | Registra un nuevo evento con los datos enviados por los sensores, asociado a una API Key válida. |

---

### Datos en Tiempo Real

| Endpoint                     | Método  | Descripción                                     |
|------------------------------|---------|-------------------------------------------------|
| `/data`                      | `GET`     | Obtiene los datos actuales en tiempo real desde los sensores registrados.  |
| `/data/{api_key}`            | `POST`    | Registra datos de los sensores en tiempo real utilizando una API Key válida. |

## Repositorios del Proyecto

### Frontend - React

El repositorio del frontend de la aplicación, desarrollado en **React**, se encuentra disponible en el siguiente enlace:

- [Repositorio Frontend (React)](https://github.com/william-medina/generador-frontend-react)

Este repositorio contiene la interfaz de usuario que interactúa con la API de Spring Boot para mostrar los datos en tiempo real y consultar el historial de eventos.

### Sistema de Monitoreo - Python (Raspberry Pi)

El repositorio del sistema de monitoreo, desarrollado en **Python** para **Raspberry Pi**, se encarga de leer los datos de los sensores y enviarlos a la API. El código se ejecuta en un sistema operativo en la Raspberry Pi.

- [Repositorio Sistema Embebido (Python)](https://github.com/william-medina/generador-monitoring-system)

Este sistema gestiona la adquisición de datos de los sensores y su transmisión a la API.


## Autor

Desarrollado y mantenido por:

**William Medina**
