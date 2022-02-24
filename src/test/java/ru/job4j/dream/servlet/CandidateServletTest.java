package ru.job4j.dream.servlet;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.store.DbStore;
import ru.job4j.dream.store.Store;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CandidateServletTest {

    @Ignore
    @Test
    public void whenCreateCandidate() throws IOException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        Store store = DbStore.instOf();
        when(req.getParameter("id")).thenReturn("0");
        when(req.getParameter("name")).thenReturn("name of new candidate");
        try {
            new CandidateServlet().doPost(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        }
        Candidate can = store
                .findAllCandidates()
                .stream()
                .filter(s -> s
                        .getName()
                        .equals("name of new candidate"))
                .findFirst()
                .get();
        verify(resp).sendRedirect(String.format("%s/candidates.do", req.getContextPath()));
        assertThat(can.getName(), is("name of new candidate"));
    }

    @Test
    public void whenGetCandidate() throws IOException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession sc = mock(HttpSession.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        Mockito.when(req.getSession()).thenReturn(sc);
        Mockito.when(req.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        try {
            new CandidateServlet().doGet(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        }
        verify(req).getRequestDispatcher("candidates.jsp");
    }
}