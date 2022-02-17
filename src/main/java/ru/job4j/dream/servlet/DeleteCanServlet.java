package ru.job4j.dream.servlet;

import ru.job4j.dream.store.DbStore;
import ru.job4j.dream.store.SourcePath;
import ru.job4j.dream.store.MemStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DeleteCanServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println(SourcePath.give());
        for (File name : new File(SourcePath.give()).listFiles()) {
            if (name.getName().split("\\.")[0].equals(req.getParameter("name"))) {
                Files.deleteIfExists(Paths.get((name.getAbsolutePath())));
            }
        }
        DbStore.instOf().deleteCan(Integer.parseInt(req.getParameter("name")));
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }
}
