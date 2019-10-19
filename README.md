# itechnews.com
<h3><i>Thực tập công nhân 2019 - phần web</i></h3>

<h6>#Plugins setting</h6>
<p>
Project lombok:<br>
Intellij: https://projectlombok.org/setup/intellij<br>
Eclipse, Spring Tools Suite: https://projectlombok.org/setup/eclipse 
</p>

<h6>#Application properties</h6>
<p>
#email<br>
spring.mail.username=your_email_address<br>
spring.mail.password=your_email_password
<br>#mysql<br>
spring.datasource.url=jdbc:mysql://localhost:3306/itechnews?useUnicode=yes&characterEncoding=UTF-8\
  &useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
spring.datasource.username=root<br>
spring.datasource.password=
</p>

<h6>#Fixing port already use problem</h6>
<p>
change port: server.port=8082 => server.port=another_port<br>
if after port changing, still given errors: open task manager and kill JavaTM program
</p>

<h6>#Database</h6>
<p>
#migrate and seeding the database <br/>
create database "itechnews"<br>
run once to create and seeding the database then ignore seeding data and ignore create database for later running:<br>
itechnews.seeder.enalble=false<br/>
spring.jpa.hibernate.ddl-auto=none
</p>
<p>
#migrate and seeding the database <br/>
create database "itechnews"<br>
run once to create and seeding the database then ignore seeding data and ignore create database for later running:<br>
itechnews.seeder.enalble=false<br/>
spring.jpa.generate-ddl=false<br/>
spring.jpa.hibernate.ddl-auto=none<br/>
#ignore show native sql <br/>
spring.jpa.show-sql=false
</p>

<h6>#Jpa Repository</h6>
<p>
<ul>
    <li>Find 1 item: findOneBy...</li>
    <li>Find > 1 items: findBy...</li>
</ul>
</p>

<h6>#Pagination API</h6>
<p>
<ul>
    <li>data.content</li>
    <li>data.pageable.offset</li>
    <li>data.pageable.pageSize</li>
    <li>data.pageable.pageNumber</li>
    <li>data.last</li>
    <li>data.totalElements</li>
    <li>data.totalPages</li>
    <li>data.size</li>
    <li>data.numberOfElements</li>
    <li>data.first</li>
</ul>
</p>

<h6>#Tips for using Intellij Idea IDE</h6>
<p>Thymeleaf compiling on save: https://stackoverflow.com/questions/12744303/intellij-idea-java-classes-not-auto-compiling-on-save/28764957<p>
<ol>
    <li>Go to File->Settings, then to "Build,Execution,Deployment"->Compiler and enable the "Make project automatically" flag.</li>
    <li>Press Ctrl-Alt-Shift-/ and select "Registry" from the menu that appears. Enable compiler.automake.allow.when.app.running flag.</li>
    <li>Start/restart the app and observe static content reloading.</li>
    <li>Ctrl + F5 to see your changes.</li>
</ol>

<p>Add module jpa<p>
<ol>
    <li>project setting -> add module jpa</li>
    <li>view -> tool windows -> persistanse</li>
</ol>

<p>Show database<p>
<ol>
    <li>view -> tool windows -> database -> new datasource -> mysql</li>
    <li>view -> tool windows -> persistanse</li>
</ol>
<h6>#Tips for using Eclipse IDE</h6>