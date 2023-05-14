<h3>Дипломный проект факультета Java-разработки университета GeekBrains.</h3>
<h4>Тема проекта:</h4>
"Сервис подбора подарков".
<h4>Выполняется группой студентов с применением следующего стека технологий:</h4>

<ul>
<li>БД: MariaDB, Hibernate, Flyway.</li>
<li>Web-server: Apache TomCat в составе Java Spring Boot.</li>
<li>Front-end: HTML, Bootstrap, Thymeleaf.</li>
<li>Back-end: Maven, Spring Framework, Spring Data, Spring Security, Lombok</li>
</ul>

<h4>Состав команды и роли:</h4>

<ul>
<li><a href="https://github.com/BigElmo">Андрей</a>: Project-owner, SCRUM-master, архитектура, dev-ops, back-end.</li>
<li><a href="https://github.com/SalnikovAleksey">Алексей</a>: Бэкенд + фронтенд, тестирование.</li>
<li><a href="https://github.com/starmanSN">Самбу</a>: Бэкенд</li>
<li><a href="https://github.com/kvv-9209">Валентин</a>: Бэк, фронт на thymeleaf</li>
<li><a href="https://github.com/Novikova-EY">Елена</a>: Product-owner, Front-end (AngularJS), Back-end, дизайн.</li>
<li><a href="https://github.com/Egor-Khaziev">Егор</a>: Back-end, БД(возможно)</li>
</ul>

<h4>Запуск проекта:</h4>


Клонировать проект к себе. 
<h5>Важно ! Для корректного запуска в корневую папку проекта необходимо поместить файл .env в котором нужно указать:</h5>

<ul>
<li>MARIADB_ROOT_PASSWORD='Здесь указать пароль'</li>
<li>MARIADB_DATABASE='gift_service'</li>
</ul>

Из корня проекта выполнить команды:

<ul>
<li>mvn clean</li>
<li>mvn install</li>
</ul>

После выполнения команд в корне проекта создалась папка target и в этой папке выполняем команды:

<ul>
<li>docker-compose build</li>
<li>docker-compose up</li>
</ul>
Проект запущен ! 
Тесты можно запустить вручную если уже запущен проект или контейнер БД.

<hr>
Подробное описание проекта: <a href="https://docs.google.com/document/d/1JalT0d-NrFb3reD91P5e70R5gDLTBG3z/edit?usp=sharing&ouid=106094704088529691884&rtpof=true&sd=true">в гугл-доках</a>

