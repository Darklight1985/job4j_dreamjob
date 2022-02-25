package ru.job4j.dream.servlet;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.City;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.store.DbStore;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Collection<Post> posts = DbStore.instOf().findAllPostsDay();
        Collection<Candidate> candidates = DbStore.instOf().findAllCanDay();
        req.setAttribute("postsForDay", posts);
        req.setAttribute("cansForDay", candidates);
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
