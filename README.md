# PatitasGal 🐾
Plataforma web de gestión y adopción de animales para protectoras de Galicia.

PatitasGal permite a las protectoras gestionar su catálogo de animales y a los 
adoptantes buscar, guardar favoritos y enviar solicitudes formales de adopción, 
con seguimiento del estado del proceso para ambas partes.

## Stack tecnológico

- **Backend:** Java 17, Spring Boot 4.0.4, Spring Security, Spring Data JPA / Hibernate
- **Base de datos:** PostgreSQL
- **Frontend:** Thymeleaf, Bootstrap 5, Bootstrap Icons
- **Imágenes:** Cloudinary
- **Build:** Maven

## Requisitos previos

- Java 17 o superior
- Maven
- PostgreSQL
- Cuenta gratuita en [Cloudinary](https://cloudinary.com)

## Instalación

1. Clona el repositorio:
```bash
   git clone https://github.com/vanesaprz/PFC-DAM.git
```

2. Crea la base de datos en PostgreSQL:
```sql
   CREATE DATABASE protectapp;
```

3. Copia el archivo de configuración de ejemplo:
```bash
   cp src/main/resources/application.properties.example src/main/resources/application.properties
```

4. Edita `application.properties` y rellena tus credenciales:
```properties
   spring.datasource.username=tu_usuario
   spring.datasource.password=tu_contrasena
   cloudinary.cloud-name=tu_cloud_name
   cloudinary.api-key=tu_api_key
   cloudinary.api-secret=tu_api_secret
```

5. Ejecuta la aplicación:
```bash
   mvn spring-boot:run
```

6. Accede en el navegador: [http://localhost:8080](http://localhost:8080)

> La primera vez que arranque, Hibernate creará automáticamente las tablas 
> gracias a `spring.jpa.hibernate.ddl-auto=update`.

## Credenciales de prueba

Si quieres probar la aplicación sin registrarte, puedes usar estas cuentas 
de ejemplo:

| Rol | Email | Contraseña |
|-----|-------|------------|
| Protectora | protectora@test.com | 12345 |
| Adoptante | adoptante@test.com | 12345 |

## Estructura del proyecto
```
src/main/java/com/example/PFC_DAM/
├── controller/    # Controladores Spring MVC
├── model/         # Entidades JPA y DTOs
│   └── DTO/       # Clases de transferencia de datos
├── repos/         # Repositorios Spring Data JPA
└── service/       # Capa de servicios

src/main/resources/
├── templates/     # Plantillas Thymeleaf
│   ├── adoptante/
│   ├── protectora/
│   └── fragments/
└── static/        # CSS y recursos estáticos
```

## Autora

Vanesa Pérez Mc'Intosh  
Proyecto Final de Ciclo — Desarrollo de Aplicaciones Multiplataforma  
Curso 2025-2026




