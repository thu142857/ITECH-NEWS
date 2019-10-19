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

<h6>#Pagination API Guide</h6>
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
<h6>#Tips for using Eclipse IDE</h6>