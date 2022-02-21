package ru.job4j.dream.store;

import org.junit.Test;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;


public class DbStoreTest {
    @Test
    public void whenCreatePost() {
        Store store = DbStore.instOf();
        Post post = new Post(0, "Java Job", "", LocalDateTime.now());
        store.save(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName(), is(post.getName()));
    }

    @Test
    public void whenUpdatePost() {
        Store store = DbStore.instOf();
        Post post = new Post(0, "Java Job", "", LocalDateTime.now());
        Post postNew = new Post(1, "Java Work", "", LocalDateTime.now());
        store.save(post);
        store.save(postNew);
        Post postInDb = store.findById(postNew.getId());
        assertThat(postInDb.getName(), is(postNew.getName()));
    }

    @Test
    public void whenFindAllPost() {
        Store store = DbStore.instOf();
        List<Post> listOld = (List<Post>) store.findAllPosts();
        Post post = new Post(0, "Java Job", "", LocalDateTime.now());
        Post postNew = new Post(0, "Java Work", "", LocalDateTime.now());
        store.save(post);
        store.save(postNew);
        List<Post> list = (List<Post>) store.findAllPosts();
        assertEquals(list.size() - listOld.size(), 2);
    }

    @Test
    public void whenCreateCan() {
        Store store = DbStore.instOf();
        Candidate candidate = new Candidate(0, "Java Job");
        store.save(candidate);
        Candidate canInDb = store.findCanById(candidate.getId());
        assertThat(canInDb.getName(), is(candidate.getName()));
    }

    @Test
    public void whenUpdateCandidate() {
        Store store = DbStore.instOf();
        Candidate candidate = new Candidate(0, "Java Job");
        Candidate candidateNew = new Candidate(1, "Java Work");
        store.save(candidate);
        store.save(candidateNew);
        Post postInDb = store.findById(candidateNew.getId());
        assertThat(postInDb.getName(), is(candidateNew.getName()));
    }

    @Test
    public void whenFindAllPCandidate() {
        Store store = DbStore.instOf();
        List<Candidate> listOld = (List<Candidate>) store.findAllCandidates();
        Candidate candidate = new Candidate(0, "Java Job");
        Candidate candidateNew = new Candidate(0, "Java Work");
        store.save(candidate);
        store.save(candidateNew);
        List<Candidate> list = (List<Candidate>) store.findAllCandidates();
        assertEquals(list.size() - listOld.size(), 2);
    }
}