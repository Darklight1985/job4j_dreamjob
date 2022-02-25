package ru.job4j.dream.servlet;

import ru.job4j.dream.model.City;
import ru.job4j.dream.store.DbStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class CityJSPServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<City> cities = (List<City>) DbStore.instOf().findAllCity();
        City city;
        String str = req.getParameter("id");
        if (isNull(req.getParameter("id"))) {
            city = DbStore.instOf().findCityById(1);
        } else {
            city = DbStore.instOf()
                    .findCityById(DbStore.instOf()
                            .findCanById(Integer.parseInt(req.getParameter("id"))).getCityId());
        }
        req.setAttribute("citycan", city);
      req.setAttribute("cities", cities);
      req.getRequestDispatcher("candidate/edit.jsp").forward(req, resp);
    }
}
