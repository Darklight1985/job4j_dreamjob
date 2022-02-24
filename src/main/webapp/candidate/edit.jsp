<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="ru.job4j.dream.store.Store" %>
<%@ page import="ru.job4j.dream.model.Post" %>
<%@ page import="ru.job4j.dream.model.Candidate" %>
<%@ page import="ru.job4j.dream.store.MemStore" %>
<%@ page import="ru.job4j.dream.store.DbStore" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="ru.job4j.dream.model.City" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    function addCan() {
      let name = $('#name').val();
      let messeng = "Не заполнены следующие данные: \n"
      let warning = "";
      if (name === "") {
        warning += "Имя \n";
      }
      if (warning !== "") {
        alert(messeng + warning);
      }
    }
  </script>
  <script>
    $(document).ready(function () {
      $.ajax({
        type: "GET",
        url: "http://localhost:8080/dreamjob/cities",
        dataType: "json",
        success: function (data) {
          let cities = "";
          for (let i = 0; i < data.length; i++) {
            cities = "<option value=" + data[i]['id'] + ">" + data[i]['name'] + "</option>";
            $('#city').append(cities)
          }
        }
      })
    })
  </script>
  <title>Работа мечты</title>
</head>
<body>
<%
  String id = request.getParameter("id");
  Candidate can = new Candidate(0, "", 1, LocalDateTime.now());
  if (id != null) {
    can = DbStore.instOf().findCanById(Integer.valueOf(id));
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
        <a class="nav-link" href="<%=request.getContextPath()%>/candidate/edit.jsp">Добавить кандидата</a>
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
        Новый кандидат.
        <% } else { %>
        Редактирование кандидата.
        <% } %>
      </div>
      <div class="card-body">
        <form action="<%=request.getContextPath()%>/candidates.do?id=<%=can.getId()%>" method="post">
          <div class="form-group">
            <label>Имя</label>
            <input type="text" class="form-control" name="name" id="name" value="<%=can.getName()%>" required>
          </div>
          <div class="form-group">
            <label for="city">Выберите город</label>
            <select id="city" name="city" class="form-control">
              <option>value="<c:out value="${can.cityId}"/></option>
            </select>
          </div>
          <button type="submit" class="btn btn-primary" onclick="addCan()">Сохранить</button>
        </form>
      </div>
    </div>
  </div>
</div>
</body>
</html>
