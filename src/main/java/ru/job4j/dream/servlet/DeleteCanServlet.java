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
import java.io.InputStream;
import java.util.Properties;

public class DeleteCanServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Properties properties = new Properties();
        InputStream in = DeleteCanServlet.class.getClassLoader()
                .getResourceAsStream("Resource.properties");
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (File name : new File(properties.getProperty("pathToDir")).listFiles()) {
            if (name.getName().split("\\.")[0].equals(req.getParameter("name"))) {
                name.delete();
            }
        }
        MemStore.instOf().deleteCan(Integer.parseInt(req.getParameter("name")));
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }
}
