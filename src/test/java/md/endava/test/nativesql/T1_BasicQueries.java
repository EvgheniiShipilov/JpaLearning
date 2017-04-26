package md.endava.test.nativesql;

import md.endava.domain.User;
import md.endava.test.AbstractTest;
import org.junit.jupiter.api.Test;

import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by esipilov on 4/4/2017.
 */
public class T1_BasicQueries extends AbstractTest {

    /*
    * As opposed to JPQL queries, Native queries cannot be explicitly typed.
    * However EntityManager does the mapping if a class is specified.
    *
    * Note that an index parameter is used. Named parameter support is not required by the specification.
     */
    @Test
    public void testTypedNativeQuery() {
        Query query = em.createNativeQuery("SELECT u.id FROM user u JOIN department d ON u.department_id = d.id WHERE d.name = ?1", User.class);
        query.setParameter(1, "department 1");
        List<User> result = query.getResultList();
        assertEquals(result.size(), 3);
    }

    @Test
    public void testUntypedNativeQuery() {
        Query query = em.createQuery("select u.name, u.lastLogin from User u where u.department.name = :name");
        query.setParameter("name", "department 1");
        query.getResultList().forEach(user -> {
            assertNotNull(((Object[]) user)[0]);
            assertNotNull(((Object[]) user)[1]);
            System.out.println(((Object[]) user)[0] + ", last login: " + ((Object[]) user)[1]);
        });
    }

    /*
    * TODO check if this is JPA 2.0 only relevant
    * JQPL SUM (for example) does not support inner expressions.
    * Native queries can be a way to tackle this.
    * */
    @Test
    public void testAggregateResults() {
        Query query = em.createNativeQuery("SELECT SUM(2*u.id) FROM user u");
        BigDecimal result = (BigDecimal) query.getSingleResult();
        assertTrue(result.intValue() >= 12); // min id's are 1, 2, 3; (1+2+3)*2=12
    }
}
