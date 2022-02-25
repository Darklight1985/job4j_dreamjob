<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="ru.job4j.dream.store.Store" %>
<%@ page import="ru.job4j.dream.model.Post" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="ru.job4j.dream.store.MemStore" %>
<%@ page import="ru.job4j.dream.store.DbStore" %>
<!doctype html>
<html lang="en">
<head>
  <!-- Required meta tags -->
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

  <!-- Bootstrap CSS -->
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
        integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
  <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
          integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
          integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
          integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
  <script>
    function addPost() {
      let name = $('#name').val();
      let description = $('#descr').val();
      let messeng = "Не заполнены следующие данные: \n"
      let warning = "";
      if (name === "") {
        warning += "Имя \n";
      }
      if (description === "") {
        warning += "Описание \n";
      }
      if (warning !== "") {
        alert(messeng + warning);
      }
    }
  </script>

  <title>Работа мечты</title>
</head>
<body>
<%
  String id = request.getParameter("id");
  Post post = new Post(0, "", "", LocalDateTime.now());
  if (id != null) {
    post = DbStore.instOf().findById(Integer.valueOf(id));
  }
%>
<div class="container pt-3">
  <div class="row">
    <ul class="nav">
      <li class="nav-item">
        <a class="nav-link" href="<%=request.getContextPath()%>/posts.do">Вакансии</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="<%=request.getContextPath()%>/candidates.do">Кандидаты</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="<%=request.getContextPath()%>/post/edit.jsp">Добавить вакансию</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="<%=request.getContextPath()%>/cities.do">Добавить кандидата</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp"> <c:out value="${user.name}"/> | Выйти</a>
      </li>
    </ul>
  </div>

  <div class="row">
    <div class="card" style="width: 100%">
      <div class="card-header">
        <% if (id == null) { %>
        Новая вакансия.
        <% } else { %>
        Редактирование вакансии.
        <% } %>
      </div>
      <div class="card-body">
        <form action="<%=request.getContextPath()%>/posts.do?id=<%=post.getId()%>" method="post">
          <div class="form-group">
            <label>Имя</label>
            <input type="text" class="form-control" name="name" id="name" value="<%=post.getName()%>" required>
          </div>
          <div class="form-group">
            <label>Описание</label>
            <input type="text" class="form-control" name="description" id="descr" value="<%=post.getDescription()%>" required>
          </div>
          <button type="submit" class="btn btn-primary" onclick="addPost()">Сохранить</button>
        </form>
      </div>
    </div>
  </div>
</div>
</body>
</html>
