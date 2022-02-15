<%@ page language="java" pageEncoding="UTF-8" session="true"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Upload</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <table class="table">
        <thead>
        <tr>
            <th>URL</th>
        </tr>
        </thead>
        <tbody>
        <form action="<%=request.getContextPath()%>/photoupload?name=<%=request.getParameter("name")%>" method="post" enctype="multipart/form-data">
            <div class="checkbox">
                <input type="file" name="file">
            </div>
            <button type="submit" class="btn btn-default">Submit</button>
        </form>
        </tbody>
    </table>
</div>
</body>
</html>
