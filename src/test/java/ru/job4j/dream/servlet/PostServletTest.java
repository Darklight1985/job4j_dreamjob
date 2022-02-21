package ru.job4j.dream.servlet;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;
import ru.job4j.dream.store.DbStore;
import ru.job4j.dream.store.MemStore;
import ru.job4j.dream.store.Store;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PostServletTest {
    @Ignore
    @Test
    public void whenCreatePost() throws IOException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        Store store = DbStore.instOf();
        when(req.getParameter("id")).thenReturn("1");
        when(req.getParameter("name")).thenReturn("name of new post");
        when(req.getParameter("description")).thenReturn("something");
        try {
            new PostServlet().doPost(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        }
        Post post = store.findById(1);
        assertThat(post.getName(), is("name of new post"));
        assertThat(post.getDescription(), is("something"));
    }

    @Ignore
    @Test
    public void whenCreateGet() throws IOException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        Store store = DbStore.instOf();
        store.save(new Post(0, "Name User", "", LocalDateTime.now()));
        User user = new User(0, "Vasya", "pochta@mail.ru", "123456");
        store.save(user);
        HttpSession sc = mock(HttpSession.class);
        sc.setAttribute("user", user);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        Mockito.when(sc.getAttribute("user")).thenReturn(user);
        Mockito.when(req.getSession()).thenReturn(sc);
        Mockito.when(req.getRequestDispatcher("posts.jsp")).thenReturn(requestDispatcher);
        try {
            new PostServlet().doGet(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenCreateCandidate() throws IOException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        Store store = DbStore.instOf();
        when(req.getParameter("id")).thenReturn("1");
        when(req.getParameter("name")).thenReturn("name of new candidate");
        try {
            new CandidateServlet().doPost(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        }
        Candidate can = store.findCanById(1);
        assertThat(can.getName(), is("name of new candidate"));
    }
}