package md.endava.test.jpql;

import md.endava.domain.Department;
import md.endava.domain.User;
import md.endava.test.AbstractTest;
import org.junit.jupiter.api.Test;

import javax.persistence.TypedQuery;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by esipilov on 26/4/2017.
 */
public class T6_PathSelectExpressions extends AbstractTest {

    /*
    * Field chains can be accessed using '.' operation.
    * In this example 'u.department.name' creates a join implicitly
    * between User and Department.
    * */
    @Test
    public void testLongPathExpressions() {
        TypedQuery<User> query = em.createQuery("FROM User u WHERE u.department.name = :name", User.class);
        query.setParameter("name", "department 1");
        List<User> result = query.getResultList();
        assertFalse(result.isEmpty());
    }

    /*
    * Embedded field navigation example.
    * User has an embedded privateInfo field which has an embedded privateInfo field which has a previousName field.
    * */
    @Test
    public void testLongPathExpressions2() {
        TypedQuery<User> query = em.createQuery("FROM User u WHERE u.privateInfo.innerInfo.previousName = :name", User.class);
        query.setParameter("name", "department 1");
        List<User> result = query.getResultList();
        assertTrue(result.isEmpty());
    }

    /*
    * Lists are automatically handled by JPA.
    *
    * We are trying to select all departments that have users.
    * Using SQL we would do a join or a subquery, ex:
    *
    * SELECT d.id, d.name
    * FROM Department d
    * WHERE (
    *   (SELECT COUNT(u.id)
    *    FROM User u
    *    WHERE (u.department_id = d.id)) > 0
    * )
    *
    * JPQL allows to specify the NOT EMPTY expression for the users field.
    * */
    @Test
    public void testListPathExpressions() {
        TypedQuery<Department> query = em.createQuery("FROM Department d WHERE d.users IS NOT EMPTY", Department.class);
        List<Department> result = query.getResultList();
        assertFalse(result.isEmpty());
    }

    /*
    * JPQL only allows list fields to be last in the path chain.
    *
    * Using the example above we will try to select departments with non null usernames for their users.
    * This will trigger an IllegalArgumentException exception with message stating that
    * field path 'd.users.name' cannot be resolved to a valid type.
    * */
    @Test
    public void testListPathExpressions_FailsIfNotLast() {
        assertThrows(IllegalArgumentException.class, () -> {
            TypedQuery<Department> query = em.createQuery("FROM Department d WHERE d.users.name IS NOT NULL", Department.class);
            query.getResultList();
        });
    }

}
