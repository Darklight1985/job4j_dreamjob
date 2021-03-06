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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PostServletTest {
    @Test
    public void whenCreatePost() throws IOException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        Store store = DbStore.instOf();
        when(req.getParameter("id")).thenReturn("0");
        when(req.getParameter("name")).thenReturn("name of new post");
        when(req.getParameter("description")).thenReturn("something");
        try {
            new PostServlet().doPost(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        }
        Post post = store
                .findAllPosts()
                .stream()
                .filter(s -> s.getName()
                        .equals("name of new post"))
                .findFirst()
                .get();
        verify(resp).sendRedirect(String.format("%s/posts.do", req.getContextPath()));
        assertThat(post.getDescription(), is("something"));
    }

    @Test
    public void whenCreateGet() throws IOException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession sc = mock(HttpSession.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        Mockito.when(req.getSession()).thenReturn(sc);
        Mockito.when(req.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        try {
            new PostServlet().doGet(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        }
        verify(req).getRequestDispatcher("posts.jsp");
    }
}