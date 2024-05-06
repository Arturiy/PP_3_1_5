# Приложение запускается по URL

#### GET http://localhost:8080

который обрабатывает специальный StartController, возвращающий стартовую страницу. Дальнейшая работа происходит в формате запросов к REST контроллерам и обработки результатов на клиенте. Валидация данных выполняется на сервере.

***

## Первый запуск

При первом запуске, необходимо выполнить скрипт  `src/main/resources/`**init.SQL** для инициализации схемы базы данных и
создания дефолтного пользователя для начала работы:

login: **root**

password: **root**

roles: **ROLE_USER, ROLE_ADMIN**

***

## Параметры БД

spring.datasource.url=**jdbc:mysql://localhost:3306/kataWorkDB**

spring.datasource.username=**root**

spring.datasource.password=**root**

spring.jpa.properties.hibernate.dialect=**org.hibernate.dialect.MySQL8Dialect**

