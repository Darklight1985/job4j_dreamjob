package ru.job4j.dream.servlet;

import org.junit.Ignore;
import org.junit.Test;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.store.DbStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostServletTest {
    @Test
    public void whenCreatePost() throws IOException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("id")).thenReturn("1");
        when(req.getParameter("name")).thenReturn("name of new post");
        when(req.getParameter("description")).thenReturn("d");
        try {
            new PostServlet().doPost(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        }
        Post post = DbStore.instOf().findById(1);
        assertThat(post, notNullValue());
    }
}