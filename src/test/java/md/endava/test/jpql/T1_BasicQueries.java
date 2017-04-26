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
    * JPQL queries are usually more compact than plain SQL.
    *
    * For example joins and subqueries are often implicit.
    *
    * Here's the SQL code for this example:
    * SELECT u.id
    * FROM user u
    * JOIN department d
    * ON u.department_id = d.id
    * WHERE d.name = 'department 1'
     */
    @Test
    public void testFieldFiltering() {
        TypedQuery<User> query = em.createQuery("FROM User u WHERE u.department.name = :name", User.class);
        query.setParameter("name", "department 1");
        List<User> result = query.getResultList();
        assertFalse(result.isEmpty());
    }

    /*
    * Queries can also return separate entity fields as object arrays.
    * */
    @Test
    public void testProjectResults() {
        Query query = em.createQuery("SELECT u.name, u.lastLogin FROM User u WHERE u.department.name = :name");
        query.setParameter("name", "department 1");
        query.getResultList().forEach(user -> {
            System.out.println(((Object[]) user)[0] + ", last login: " + ((Object[]) user)[1]);
        });
    }

    /*
    * JPQL supports a big chunk of the SQL syntax, aggregate expressions are an example.
    * */
    @Test
    public void testAggregateResults() {
        Query query = em.createQuery("SELECT COUNT(u.id), MAX(u.lastLogin) FROM User u");
        Object[] result = (Object[]) query.getSingleResult();
        System.out.println(result[0] + " different users, last activity: " + result[1]);
        assertEquals(result[0], new Long(3)); // TODO get rid of the magic number
    }
}
