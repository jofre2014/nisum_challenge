# Nisum - User Api

Es un servicio que permite la creación, actualización, busqueda y desactivación de **Usuarios**.
Esta Api utiliza Seguridad a traves de Spring Security y JWT token.

## Diagrama de Funcionamiento
Cuando se crea el usuario, se genera el JWT y se almacena en la BD junto con los datos del Usuario

![Create_User.png](images/Create_User.png)

Para cada petición HTTP, se necesita enviar el JWT, en caso de que el token no sea válido, se retorna un HTTP CODE 403.

![peticiones_http.png](images/peticiones_http.png)

## Funcionamiento

## Levantar la aplicación

### 1 - Docker
**Requisito:** Tener instalado `docker` y `docker-compose`

Para levantar la aplicación ejecutamos:

    `docker-compose up -d`

### 2 - Sin Docker

- Compilar el proyecto    
        `mvn clean install`


- Ejecutar la aplicación    
        `mvn spring-boot:run`


## Swagger
http://localhost:8080/swagger-ui/index.html

## Probando la aplicación

Una vez que tengamos la aplicación levantada y podamos acceder al `swagger`, procedemos a realizar las pruebas.

En la siguiente imagen vemos los métodos que tenemos disponibles

![swagger_general.png](images/swagger_general.png)

Empezamos creando un usuario con el método **POST**

Body de ejemplo:

```
{
    "name": "Jose",
    "email": "jose@rodriguez.cl",
    "password": "hunter6",
    "phones": [
        {
            "number": "1234567",
            "city_code": "1",
            "country_code": "57"
        },
         {
            "number": "222333344",
            "city_code": "2",
            "country_code": "58"
        }
    ]
}

```

Obtenemos una respuesta como la siguiente:
```
{
  "id": "4da10e8c-b293-44f7-bd67-b02d0f9ee56b",
  "name": "Jose",
  "email": "jose@rodriguez.cl",
  "password": "hunter6",
  "phones": [
    {
      "number": "1234567",
      "city_code": "1",
      "country_code": "57"
    },
    {
      "number": "222333344",
      "city_code": "2",
      "country_code": "58"
    }
  ],
  "created": "10-07-2024T04:11:59",
  "modified": "10-07-2024T04:11:59",
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb3NlQHJvZHJpZ3Vlei5jbCIsImlhdCI6MTcyMDU4NDcxOSwiZXhwIjoxNzIwNjIwNzE5fQ.PIiTb0PRHemJ6VHNn-ULkN7-9ktpMmufub1ys3N4Tqg",
  "last_login": "10-07-2024T04:11:59",
  "is_active": true
}
```

De esta respuesta, necesitamos obtener el `token` para poder utilizar los otros endpoints.

Para agregar el token, hacemos click en el boon `Authorize` y colocamos el token en el popup.

![swagger_authorized.png](images/swagger_authorized.png)

Esto nos habilita para poder utilizar los otros Enpoints.

Podemos:
- Buscar todos los usuarios
- Buscar usuario por ID
- Actualizar un usuario
- Desactivar un usuario

En caso de que el token sea incorrecto, no va a retornar un codigo de error 403 `Forbiden`