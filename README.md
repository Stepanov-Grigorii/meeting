# Инструкция
Для запуска приложения выполнить Start.bat


Для сборки и запуска необходима Java версии 14

# Запросы

* добавление встречи


url : http://localhost:8080/createMeet


пример: json body 
```
{
    "name": "name",
    "startDate": "2020-07-03T10:15:30",
    "logins": ["login1", "login2"]
}
```
* удаление встречи

url : http://localhost:8080/deleteUser

пример: json body
```
{
    "name": "name"
}
```

* добавление пользователя во встречу

url : http://localhost:8080/addUsers

пример: json body
```
{
    "login": "Login1",
    "name" : "Meet1"
}
```

* удаление пользователя из встречи

url : http://localhost:8080/deleteUser

пример: json body
```
{
    "login": "Login1",
    "meetName": "Meet1"
}
```

* Вывод списсок встреч с участниками

url : http://localhost:8080/getMeets
