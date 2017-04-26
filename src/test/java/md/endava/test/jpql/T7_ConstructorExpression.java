package md.endava.test.jpql;

import md.endava.domain.User;
import md.endava.test.AbstractTest;
import org.junit.jupiter.api.Test;

import javax.persistence.TypedQuery;
import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Created by esipilov on 26/4/2017.
 */
public class T7_ConstructorExpression extends AbstractTest {

    /*
    * JPQL allows to create new objects using select expressions.
    *
    * To check that it's not a real entity returned, we are asserting it's id.
    * */
    @Test
    public void testCreateEntityWithConstructor() {
        TypedQuery<User> query = em.createQuery(
                "SELECT NEW md.endava.domain.User(" +
                        "u.name, " +
                        "u.lastLogin)" +
                        " from User u", User.class);
        query.getResultList().forEach(user -> {
            assertNull(user.getId());
        });
    }

    /*
    * Any object can be created using SELECT NEW expression.
    *
    * Following example show a TypedQuery returning a list of files for each User.
    * */
    @Test
    public void testCreateObjectWithConstructor() {
        TypedQuery<File> query = em.createQuery(
                "SELECT NEW java.io.File(u.name) from User u", File.class);
        List<File> result = query.getResultList();
        assertFalse(result.isEmpty());
        result.forEach(file -> {
            System.out.println(file.exists());
        });
    }

}
