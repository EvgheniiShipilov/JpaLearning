package md.endava.test.jpql;

import md.endava.domain.User;
import md.endava.test.AbstractTest;
import org.junit.jupiter.api.Test;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Created by esipilov on 4/4/2017.
 */
public class T1_BasicQueries extends AbstractTest {

    /*
    * SELECT u.id
    * FROM user u
    * JOIN department d
    * ON u.department_id = d.id
    * WHERE d.name = 'department 1'
     */
    @Test
    public void testFieldFiltering() {
        TypedQuery<User> query = em.createQuery("from User u where u.department.name = :name", User.class);
        query.setParameter("name", "department 1");
        List<User> result = query.getResultList();
        assertFalse(result.isEmpty());
    }

    @Test
    public void testProjectResults() {
        Query query = em.createQuery("select u.name, u.lastLogin from User u where u.department.name = :name");
        query.setParameter("name", "department 1");
        query.getResultList().forEach(user -> {
            System.out.println(((Object[]) user)[0] + ", last login: " + ((Object[]) user)[1]);
        });
    }

    @Test
    public void testAggregateResults() {
        Query query = em.createQuery("select count(u.id), max(u.lastLogin) from User u");
        Object[] result = (Object[]) query.getSingleResult();
        System.out.println(result[0] + " different users, last activity: " + result[1]);
        assertEquals(result[0], new Long(2));
    }
}
