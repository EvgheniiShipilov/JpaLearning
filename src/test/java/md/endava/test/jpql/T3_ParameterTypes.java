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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Created by esipilov on 4/4/2017.
 */
public class T3_ParameterTypes extends AbstractTest {

    @Test
    public void testIndexParameters() {
        TypedQuery<User> query = em.createQuery("from User u where u.name = ?1 and u.department.name = ?2", User.class);
        query.setParameter(1, "User 1.2");
        query.setParameter(2, "department 1");
        List<User> result = query.getResultList();
        assertEquals(result.size(), 1);
    }

    @Test
    public void testDateParams() {
        // TODO add a date field to some class and fix test
        java.util.Date dateTo = Date.from(Instant.now());
        java.util.Date dateFrom = Date.from(Instant.now().minus(Duration.ofDays(1)));

        TypedQuery<User> query = em.createQuery("FROM User u WHERE u.lastLogin BETWEEN :dateFrom and :dateTo", User.class);
        query.setParameter("dateFrom", dateFrom, TemporalType.DATE);
        query.setParameter("dateTo", dateTo, TemporalType.DATE);
        List<User> result = query.getResultList();
        assertFalse(result.isEmpty());
    }

}
