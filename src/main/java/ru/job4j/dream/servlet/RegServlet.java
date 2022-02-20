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

public class RegServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        User user = new User(0, name, email, password);
        if (isNull(user)) {
            req.setAttribute("error", "Введены не все данные");
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
        } else {
            Store store = DbStore.instOf();
            store.save(user);
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
        }
    }
}