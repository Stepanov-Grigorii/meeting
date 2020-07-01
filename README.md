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
