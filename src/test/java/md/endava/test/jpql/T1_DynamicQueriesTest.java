package md.endava.test.jpql;

import md.endava.domain.User;
import md.endava.persistence.EntityDao;
import org.junit.jupiter.api.*;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by esipilov on 4/4/2017.
 */
public class T1_DynamicQueriesTest {

    private static EntityDao dao;
    private static EntityManagerFactory emf;
    private EntityManager em;

    @BeforeAll
    public static void setUp(){
        emf = Persistence.createEntityManagerFactory("test");

        dao = new EntityDao();
        User user = new User();
        user.setName("user");
        user.setLastLogin(Instant.now());
        dao.persist(user);
    }

    @BeforeEach
    public void setUpEach(){
        em = emf.createEntityManager();
    }

    @AfterEach
    public void tearDownEach(){
        em.close();
        em = null;
    }

    @Test
    public void testJpql_whenSelect_thenReturnValue(){
        TypedQuery<User> query = em.createQuery("from User u where u.name = :name", User.class);
        query.setParameter("name", "user");
        List<User> result = query.getResultList();
        System.out.print(result);
    }

}
