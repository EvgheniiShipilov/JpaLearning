package md.endava.test.jpql;

import md.endava.domain.User;
import md.endava.test.AbstractTest;
import org.junit.jupiter.api.Test;

import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import java.sql.Date;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by esipilov on 25/4/2017.
 */
public class T4_ResultTypes extends AbstractTest {

    /*
    * Select a list of users with a TypedQuery
    * Specify the return type as User.class
    *
    * A List<User> result will be returned
    * */
    @Test
    public void testSelect_ReturnResultList() {
        TypedQuery<User> query = em.createQuery("from User u where u.department.name = :name", User.class);
        query.setParameter("name", "department 1");
        List<User> result = query.getResultList();
        assertFalse(result.isEmpty());
    }


    /*
    * Select a single user with a TypedQuery
    * Specify the return type as User.class
    *
    * A User result will be returned
    * */
    @Test
    public void testSelect_ReturnSingleResult() {
        TypedQuery<User> query = em.createQuery("from User u where u.name = :name", User.class);
        query.setParameter("name", "User 1.1");
        User result = query.getSingleResult();
        assertNotNull(result);
    }

    /*
    * Select a list of users
    * Do not specify return type
    *
    * A List will be returned
    * This list can be casted to List<EntityType>
    * */
    @Test
    public void testSelect_ReturnUntypedResultList() {
        Query query = em.createQuery("from User u where u.department.name = :name");
        query.setParameter("name", "department 1");
        List<User> result = query.getResultList();
        assertFalse(result.isEmpty());
    }

    /*
    * Select a single user
    * Do not specify return type
    *
    * An object will be returned
    * This object must be casted manually
    * */
    @Test
    public void testSelect_ReturnUntypedSingleResult() {
        Query query = em.createQuery("from User u where u.name = :name");
        query.setParameter("name", "User 1.1");
        User result = (User)query.getSingleResult();
        assertNotNull(result);
    }

    /*
    * Write a query to return one user
    * Get the results as a List
    *
    *
    * */
    @Test
    public void testSelect_ReturnResultList_SuccessWithOneResult() {
        TypedQuery<User> query = em.createQuery("from User u where u.name = :name", User.class);
        query.setParameter("name", "User 1.1");
        List<User> result = query.getResultList();
        assertFalse(result.isEmpty());
    }

    @Test
    public void testSelect_ReturnSingleResult_FailedWithMultipleResults() {
        TypedQuery<User> query = em.createQuery("from User u where u.department.name = :name", User.class);
        query.setParameter("name", "department 1");
        assertThrows(NonUniqueResultException.class, () -> query.getSingleResult());
    }


}
