package ru.job4j.dream.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import ru.job4j.dream.store.Store;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PhotoUploadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<String> images = new ArrayList<>();
        for (File name : new File("c:\\images\\").listFiles()) {
            images.add(name.getName());
        }
        req.setAttribute("images", images);
        req.setAttribute("candidates", Store.instOf().findAllCandidates());
        req.getRequestDispatcher("candidates.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        String name = req.getParameter("name");
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            List<FileItem> items = upload.parseRequest(req);
            File folder = new File("c:\\images\\");
            if (!folder.exists()) {
                folder.mkdir();
            }
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    String[] strings  = item.getName().split("\\.");
                    File file = new File(folder + File.separator + name + "." + strings[1]);
                    try (FileOutputStream out = new FileOutputStream(file)) {
                        out.write(item.getInputStream().readAllBytes());
                    }
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        doGet(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        for (File name : new File("c:\\images\\").listFiles()) {
            System.out.println(name);
            System.out.println(req.getParameter("name"));
            if (name.getName().equals(req.getParameter("name"))) {
                name.delete();
            }
        }
        doGet(req, resp);
    }
}
