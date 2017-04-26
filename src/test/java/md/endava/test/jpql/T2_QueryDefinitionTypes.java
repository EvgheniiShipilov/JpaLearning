package md.endava.test.jpql;

import md.endava.domain.User;
import md.endava.persistence.PersistenceUtils;
import md.endava.test.AbstractTest;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by esipilov on 4/4/2017.
 */
public class T2_QueryDefinitionTypes extends AbstractTest {

    final private String userNameParam = "User 1.1";

    /*
    * Dynamic queries are created at runtime.
    *
    * It's drawback is that it's created for each call.
    * */
    @Test
    public void testDynamicQuery_ConcatParam() {
        String queryString = "FROM User u WHERE u.name = '" + userNameParam + "'";
        TypedQuery<User> query = em.createQuery(queryString, User.class);
        List<User> result = query.getResultList();
        assertEquals(result.size(), 1);
    }

    /*
    * Named queries are precompiled. As opposed to dynamic queries they are created once and then reused.
    *
    * Usually the named query declaration is placed in the class corresponding to the return type.
    *
    * In this example we are getting a User, so we placed this declaration in the user class:
    * @NamedQuery(name="findUserByName", query = "from User u where u.name = :name")
    * */
    @Test
    public void testNamedQuery() {
        TypedQuery<User> query = em.createNamedQuery("findUserByName", User.class);
        query.setParameter("name", userNameParam);
        List<User> result = query.getResultList();
        assertEquals(result.size(), 1);
    }

    /*
    * Dynamic named queries are queries created at runtime and registered in the EntityManagerFactory to be reused.
    *
    * This example registers findUserByName2 as findUserByName is already registered in this EntityManagerFactory.
    * */
    @Test
    public void testDynamicNamedQuery() {
        EntityManagerFactory emf = PersistenceUtils.getEntityManagerFactory();

        String queryString = "FROM User u WHERE u.name = '" + userNameParam + "'";
        TypedQuery<User> query = em.createQuery(queryString, User.class);
        emf.addNamedQuery("findUserByName2", query);
        List<User> result = query.getResultList();
        assertEquals(result.size(), 1);

        TypedQuery<User> query2 = em.createNamedQuery("findUserByName2", User.class);
        List<User> result2 = query2.getResultList();
        assertEquals(result2.size(), 1);
    }
}
