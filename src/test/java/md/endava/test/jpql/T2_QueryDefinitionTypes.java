package md.endava.test.jpql;

import md.endava.domain.User;
import md.endava.persistence.PersistenceUtils;
import md.endava.test.AbstractTest;
import org.junit.jupiter.api.Test;

import javax.persistence.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Created by esipilov on 4/4/2017.
 */
public class T2_QueryDefinitionTypes extends AbstractTest {

    final private String userNameParam = "User 1.1";

    @Test
    public void testDynamicQuery_ConcatParam() {
        String queryString = "from User u where u.name = '" + userNameParam + "'";
        TypedQuery<User> query = em.createQuery(queryString, User.class);
        List<User> result = query.getResultList();
        assertEquals(result.size(), 1);
    }

    //User entity has @NamedQuery(name="findUserByName", query = "from User u where u.name = :name")
    @Test
    public void testNamedQuery() {
        TypedQuery<User> query = em.createNamedQuery("findUserByName", User.class);
        query.setParameter("name", userNameParam);
        List<User> result = query.getResultList();
        assertEquals(result.size(), 1);
    }

    @Test
    public void testDynamicNamedQuery() {
        EntityManagerFactory emf = PersistenceUtils.getEntityManagerFactory();

        String queryString = "from User u where u.name = '" + userNameParam + "'";
        TypedQuery<User> query = em.createQuery(queryString, User.class);
        emf.addNamedQuery("findUserByName2", query);
        List<User> result = query.getResultList();
        assertEquals(result.size(), 1);

        TypedQuery<User> query2 = em.createNamedQuery("findUserByName2", User.class);
        List<User> result2 = query2.getResultList();
        assertEquals(result2.size(), 1);
    }
}
