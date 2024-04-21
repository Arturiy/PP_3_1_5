# Приложение работвет по URL

#### GET http://localhost:8080/admin

#### GET http://localhost:8080/user
***

## Первый запуск

При первом запуске необходимо выполнить скрипт `src/main/resources/`**init.SQL** для инициализации схемы базы данных и создания дефолного пользователя для начала работы:

login: **root**

password: **root**

roles: **ROLE_USER, ROLE_ADMIN**

***
## Параметры БД

spring.datasource.url=**jdbc:mysql://localhost:3306/kataWorkDB**

spring.datasource.username=**root**

spring.datasource.password=**root**

spring.jpa.properties.hibernate.dialect=**org.hibernate.dialect.MySQL8Dialect**






