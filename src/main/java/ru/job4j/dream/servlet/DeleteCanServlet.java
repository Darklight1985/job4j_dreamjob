package ru.job4j.dream.servlet;

import ru.job4j.dream.SourcePath;
import ru.job4j.dream.store.MemStore;
import ru.job4j.dream.store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class DeleteCanServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        for (File name : new File(SourcePath.StringImages.give()).listFiles()) {
            if (name.getName().split("\\.")[0].equals(req.getParameter("name"))) {
                name.delete();
            }
        }
        MemStore.instOf().deleteCan(Integer.parseInt(req.getParameter("name")));
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }
}
