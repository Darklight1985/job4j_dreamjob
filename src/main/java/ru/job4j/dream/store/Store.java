package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.City;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.util.Collection;

public interface Store {
    Collection<Post> findAllPosts();

    Collection<Candidate> findAllCandidates();

    Collection<User> findAllUsers();

    public Collection<City> findAllCity();

    void save(Post post);

    void save(Candidate candidate);

    void save(User user);

   void save(City city);

    Post findById(int id);

     Candidate findCanById(int id);

    User findUserById(int id);

    public User findUserByEmail(String email);

    public City findCityByName(String nameCity);

    public City findCityById(int id);

    void deletePost(int id);

     void deleteCan(int id);

    void deleteUser(int id);

    public void deleteAllCandidates();
}
