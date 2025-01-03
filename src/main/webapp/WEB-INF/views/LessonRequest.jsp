<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
  <meta charset="UTF-8">
  <title>Заявка на урок</title>
</head>
<body>

<h1>Заявка на урок</h1>

<form action="#" th:action="@{/submitRequest}" method="post">
  <label for="name">Имя:</label>
  <input type="text" id="name" name="name" required>

  <label for="subject">Предмет:</label>
  <input type="text" id="subject" name="subject" required>

  <label for="date">Дата:</label>
  <input type="date" id="date" name="date" required>

  <button type="submit">Отправить заявку</button>
</form>

<h2>Созданные заявки</h2>

<table>
  <thead>
  <tr>
    <th>Имя</th>
    <th>Предмет</th>
    <th>Дата</th>
  </tr>
  </thead>
  <tbody>
  <tr th:each="request : ${requests}">
    <td th:text="${request.name}"></td>
    <td th:text="${request.subject}"></td>
    <td th:text="${request.date}"></td>
  </tr>
  </tbody>
</table>

</body>
</html>
