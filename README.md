# AirePur-BackEnd 
## Descripción

Este repositorio contiene el código fuente del backend de AirePur, una aplicación dedicada a proporcionar información actualizada sobre la calidad del aire y recomendaciones de salud asociadas a las condiciones ambientales. Este repositorio es parte de un proyecto más grande que también incluye un frontend, alojado en otro repositorio. Toda la información de la calidad del aire es de la web de la generalidad de dades obertes: https://analisi.transparenciacatalunya.cat/Medi-Ambient/Qualitat-de-l-aire-als-punts-de-mesurament-autom-t/tasf-thgu/about_data

## Estructura del Repositorio

Dentro del repositorio, encontrarás varias carpetas que organizan el proyecto de la siguiente manera:

- **client/**: Contiene algunas de las llamadas básicas a la API de la generalitat https://analisi.transparenciacatalunya.cat/resource/tasf-thgu
- **controller/**: Contiene los endpoints de nuestra API-REST, donde llama nuestro frontend.
- **domain/**: Contiene las clases objeto de nuestro proyecto.
- **repository/**: Contiene las llamadas a nuestra base de datos.
- **service/**: Contiene las llamadas al repository.
- **utils/**: Contiene la configuración de la BD y una clase que contiene una función para comparar ciudades en función de su distancia y su ICA
- **test/**: Contiene los tests unitarios de todas las clases.
  
## Tecnologías Utilizadas

- **Java**: Lenguaje de programación usado para desarrollar la lógica de la aplicación.
- **Spring Boot**: Utilizado para el desarrollo del backend. Es un framework que simplifica el desarollo mediante su configuración inteligente e integración eficiente con la base de datos PostgreSQL.

## Conexión con la Base de Datos

Este backend se comunica con una base de datos. Asegúrate de configurar las variables de entorno y las direcciones IP correctas para las llamadas a la BD.

## Configuración del Entorno

Para configurar el entorno de desarrollo necesario para contribuir a este proyecto, sigue estos pasos:
1. Clona este repositorio.
2. 
3. 
4. 

## Soporte

Para obtener soporte, puedes abrir un issue en este repositorio o contactar a los administradores del proyecto.

---

Esperamos que esta guía te ayude a comenzar rápidamente con el desarrollo y contribución al proyecto AirePur. ¡Gracias por tu interés y contribuciones!

[![Quality gate](http://nattech.fib.upc.edu:40472/api/project_badges/quality_gate?project=SonarQube4AirePur&token=sqb_f6d39a36a337f33a23819487719f3d4a21a90437)](http://nattech.fib.upc.edu:40472/dashboard?id=SonarQube4AirePur)

**Autores:** Albert Comas, Victor De Lamo, Miquel Gibert, Lluc Gracia, Sergi Jaume, Antoni Roca, Jose Miguel Santos i Hugo Tienza
