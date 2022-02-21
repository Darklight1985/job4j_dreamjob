package ru.job4j.dream.servlet;

import ru.job4j.dream.model.User;
import ru.job4j.dream.store.DbStore;
import ru.job4j.dream.store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class RegServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        if (nonNull(DbStore.instOf().findUserByEmail(email))) {
            req.setAttribute("error", "Пользователь с данным паролем уже зарегистрирован");
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
        }
            User user = new User(0, name, email, password);
            Store store = DbStore.instOf();
            store.save(user);
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
        }
}