package ru.job4j.dream.servlet;

import ru.job4j.dream.model.User;
import ru.job4j.dream.store.DbStore;
import ru.job4j.dream.store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        if (email.equals("") || password.equals("") || name.equals("")) {
            req.setAttribute("error", "Введены не все данные");
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
        } else {
            User user = new User(0, name, email, password);
            Store store = DbStore.instOf();
            store.save(user);
            resp.sendRedirect(req.getContextPath() + "/auth.do");
        }
    }
}