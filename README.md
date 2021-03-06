# Pika Mail Service

>Pika mail service - это сервис позволяющий рассылать топ комментарии из историй с Пикабу на почту, каждые 5 минут.

## Установка:

	 1. Клонируйте репозиторий git clone https://github.com/stfluffy/pika_mail_service.git
	 2. Настройте проект. (Ниже будет подробное описание настроек проекта)
	 3. Запустите команду: mvn clean install
	 4. Запустите приложение: mvn spring-boot:run

## Stack:
 - Java 8
 - Spring boot 2.3.4 RELEASE
 - Maven 3.6.3
 - PostgreSQL 12
 - Swagger 
 - Liquibase
 - Jsoup

## Настройка проекта

#### В файле application.properties содержаться следующие настройки
##### Настройка базы данных:
- spring.datasource.url - url базы данных. (По умолчанию это: jdbc:postgresql://localhost:5432/pikaMailService)
- spring.datasource.username - имя пользователя БД (По умолчанию это: Jack)
- spring.datasource.password - пароль пользователя БД. (По умолчанию он пустой)
- spring.datasource.platform - платформа БД. (По умолчанию это: postgres)

##### Настройка почтового сервиса:

- spring.mail.username - адрес электронной почты, с которого будут отправляться письма (По умолчанию он пустой)
- spring.mail.password - пароль от электронной почты (По умолчанию он пустой)

##### Далее идут настройки для SMTP сервера. (Конкретные настройки в разделе для разработчиков вашей почты, в данном примере это Яндекс)
- spring.mail.host=smtp.yandex.ru
- spring.mail.port=465
- spring.mail.protocol=smtps


##### Настройка времени промежутка отправления email рассылки(Время указываться в кронах):

- scheduling.email.distribution=0 0/2 * * * ? (По умолчанию сервис рассылки отправляет каждые 5 минут) 

> Сервсис рассылки почты запускаеться вместе с приложением, и работает до того, как приложение остановаиться.

#### В файле messages.properties содержаться настройки языка.

> Важно: в файле есть параметр **mail.page.url.unsubscribe** отвечающий за страницу отписки от рассылке в письме. При изменение порта загрузки сайта, этот параметр тоже нужно сменить.

#### В файле parser.properties содержаться следующий параметры: 

- get.page.by.url.timeout - Время ожидания получения страницы (По умолчанию он равен 20000)

- page.pikabu.best.url - Ссылка на страницу с лучшими историями (По умолчанию ссылка ведет на "https://pikabu.ru/best")

- parse.page.stories.limit - Количество историй (По умолчанию их 10)

- parse.page.comments.limit -  Количество комментариев (По умолчанию их 10)


##  Созданные страницы: 

> Для того чтобы **оформить** подписку на рассылку на почту, перейдите по ссылке "https://localhost:8080/". И следуете указаниям на странице.

> Для того чтобы **отменить** подписку на рассылку почты, перейдите по ссылке "https://localhost:8080/unsubscribe/". И следуете указаниям на странице.

## REST API / SWAGGER

##### У приложения есть свой api, просмотреть и попробовать вы его сможете c помощью Swagger перейдя по ссылке "http://localhost:8080/swagger-ui/".

