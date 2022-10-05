[![Build Status](https://travis-ci.org/VadimShein/job4j_tracker.svg?branch=master)](https://travis-ci.org/VadimShein/job4j_tracker)

# <p align="center">Приложение для хранения заявок</p>

## Описание:
Консольное приложение для хранения заявок. 
Заявки можно создавать, редактировать, удалять или искать по имени и id.
Данные хранятся в базеданных, доступ к которым осуществляется через Hibernate или JDBC. 


## Используемые технологии:
* Java 13
* PosgreSQL, Hibernate, Liquibase
* Maven


## Скриншоты:
1. Меню выбора действий.

![ScreenShot](./images/image_1.PNG)

2. Создание заявки

![ScreenShot](./images/image_2.PNG)

3. Редактирование заявки

![ScreenShot](./images/image_3.PNG)

4. Поиск заявки по id

![ScreenShot](./images/image_4.PNG)

5. Поиск заявки по имени

![ScreenShot](./images/image_5.PNG)

6 Удаление заявки

![ScreenShot](./images/image_6.PNG)

## Запуск проекта:
1. Скопировать проект 
```
git clone https://github.com/VadimShein/job4j_tracker
```

2. Создать базу данных tracker и таблицы создаются через liquibase или вручную  из файла src/main/resources/db/create.sql


3. Собрать проект
```
mvn clean package -DskipTests
```
4. Запустить приложение
```
java -jar target/job4j_tracker-1.0.jar
```



## Контакты:
[![alt-text](https://img.shields.io/badge/-telegram-grey?style=flat&logo=telegram&logoColor=white)](https://t.me/SheinVadim)
[![alt-text](https://img.shields.io/badge/@%20email-005FED?style=flat&logo=mail&logoColor=white)](mailto:shein.v94@mail.ru)
