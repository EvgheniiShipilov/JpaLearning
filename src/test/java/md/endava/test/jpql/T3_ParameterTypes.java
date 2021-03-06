package md.endava.test.jpql;

import md.endava.domain.User;
import md.endava.test.AbstractTest;
import org.junit.jupiter.api.Test;

import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import java.sql.Date;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by esipilov on 4/4/2017.
 */
public class T3_ParameterTypes extends AbstractTest {

    /*
    * JPQL Query parameters can be specified in numerical form.
    * The query string would contain '?1' as a reference to that parameter.
    * */
    @Test
    public void testIndexParameters() {
        TypedQuery<User> query = em.createQuery("from User u where u.name = ?1 and u.department.name = ?2", User.class);
        query.setParameter(1, "User 1.2");
        query.setParameter(2, "department 1");
        List<User> result = query.getResultList();
        assertEquals(result.size(), 1);
    }

    /*
    * Method setParameter for java.util.Date arguments may take an additional TemporalType argument.
    * It does specify the corresponding sql type for the query.
    *
    * This example will not find any participants as this is the same date and time is not taken into account.
    * */
    @Test
    public void testDateParams_TemporalDate() {
        java.util.Date dateTo = Date.from(Instant.now());
        java.util.Date dateFrom = Date.from(Instant.now().minus(Duration.ofHours(1)));

        TypedQuery<User> query = em.createQuery("FROM User u WHERE u.accountCreationDate BETWEEN :dateFrom and :dateTo", User.class);
        query.setParameter("dateFrom", dateFrom, TemporalType.DATE);
        query.setParameter("dateTo", dateTo, TemporalType.DATE);
        List<User> result = query.getResultList();
        assertTrue(result.isEmpty());
    }

    /*
    * This example will find participants created within the last hour
    * as both date and time are taken into account.
    * */
    @Test
    public void testDateParams_TemporalDateTime() {
        java.util.Date dateTo = Date.from(Instant.now());
        java.util.Date dateFrom = Date.from(Instant.now().minus(Duration.ofHours(1)));

        TypedQuery<User> query = em.createQuery("FROM User u WHERE u.accountCreationDate BETWEEN :dateFrom and :dateTo", User.class);
        query.setParameter("dateFrom", dateFrom, TemporalType.TIMESTAMP);
        query.setParameter("dateTo", dateTo, TemporalType.TIMESTAMP);
        List<User> result = query.getResultList();
        assertFalse(result.isEmpty());
    }

    /*
    * Will find participants created within the last hour as the default mapping temporal type is timestamp
    * */
    @Test
    public void testDateParams_NoTemporalType() {
        java.util.Date dateTo = Date.from(Instant.now());
        java.util.Date dateFrom = Date.from(Instant.now().minus(Duration.ofHours(1)));

        TypedQuery<User> query = em.createQuery("FROM User u WHERE u.accountCreationDate BETWEEN :dateFrom and :dateTo", User.class);
        query.setParameter("dateFrom", dateFrom);
        query.setParameter("dateTo", dateTo);
        List<User> result = query.getResultList();
        assertFalse(result.isEmpty());
    }

}
