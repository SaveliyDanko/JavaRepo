package com.savadanko.entitymanager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public User create(String name) {
        User user = new User();
        user.setName(name);
        em.persist(user);           // New -> Managed
        return user;
    }

    @Transactional(readOnly = true)
    public User find(Long id) {
        return em.find(User.class, id);
    }

    @Transactional
    public User updateName(Long id, String name) {
        User user = em.find(User.class, id);  // Managed
        user.setName(name);                   // UPDATE при commit
        return user;
    }

    @Transactional
    public void delete(Long id) {
        User user = em.find(User.class, id);
        if (user != null) {
            em.remove(user);                 // DELETE при commit
        }
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }
}

