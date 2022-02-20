package ru.job4j.dream.servlet;

import ru.job4j.dream.model.User;
import ru.job4j.dream.store.DbStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static java.util.Objects.nonNull;

public class AuthServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User user = DbStore.instOf().findUserByEmail(email);
        if (nonNull(user)) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                HttpSession sc = req.getSession();
                User admin = DbStore.instOf().findUserByEmail(email);
                sc.setAttribute("user", admin);
                resp.sendRedirect(req.getContextPath() + "/posts.do");
            } else {
                req.setAttribute("error", "Не верный email или пароль");
                req.getRequestDispatcher("login.jsp").forward(req, resp);
            }
        }
    }
}