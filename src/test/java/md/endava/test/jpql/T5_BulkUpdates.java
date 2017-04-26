package md.endava.test.jpql;

import md.endava.domain.EmbeddedInfo;
import md.endava.domain.User;
import md.endava.test.AbstractTest;
import org.junit.jupiter.api.Test;

import javax.persistence.NoResultException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by esipilov on 25/4/2017.
 */
public class T5_BulkUpdates extends AbstractTest {

    /*
    * Update a User entity
    *
    * The changes will not be reflected in the cache
    * The changes will be persisted to the DB and will require a refresh
    * */
    @Test
    public void testUpdate_CacheBypass() {
        // check that the user does not have private info
        User user = userService.getUserByName("User 1.1");
        assertNull(user.getPrivateInfo());

        // add some private info
        em.getTransaction().begin();
        em.createQuery("UPDATE User u SET u.privateInfo.bankId = :bankId where u.name = :name")
                .setParameter("bankId", "100")
                .setParameter("name", "User 1.1")
                .executeUpdate();
        em.getTransaction().commit();

        // check the cached entity after update - should not have private info
        assertNull(user.getPrivateInfo());

        // check the DB entity after update - should have private info with updated bank id
        user = em.find(User.class, user.getId());
        assertNotNull(user.getPrivateInfo().getBankId());
    }

    @Test
    public void testUpdate_CacheBypass2() {

        em.getTransaction().begin();
        User user = em.createQuery("FROM User u WHERE u.name = :name", User.class)
                .setParameter("name", "User 1.2")
                .getSingleResult();

        // update bank id = 100
        user.setPrivateInfo(new EmbeddedInfo());
        user.getPrivateInfo().setBankId("100");
        em.merge(user);

        // bulk update bank id = 200, bypasses the context
        em.createQuery("UPDATE User u SET u.privateInfo.bankId = :bankId where u.name = :name")
                .setParameter("bankId", "200")
                .setParameter("name", "User 1.2")
                .executeUpdate();
        em.getTransaction().commit();

        // get the entity from EntityManager, the bulk updated changes are not reflected there
        user = em.find(User.class, user.getId());
        assertEquals(user.getPrivateInfo().getBankId(), "100");
    }

    @Test
    public void testDelete() {
        // check that the user is present
        User user = userService.getUserByName("User 1.3");
        assertNotNull(user);

        // delete the user
        em.getTransaction().begin();
        em.createQuery("DELETE FROM User u WHERE u.name = :name")
                .setParameter("name", "User 1.3")
                .executeUpdate();
        em.getTransaction().commit();

        // check that user has been deleted from DB and remained in Cache
        assertThrows(NoResultException.class, () -> userService.getUserByName("User 1.3"));

        // NOTE: the EntityManager may not be aware that the entity is removed,
        // testing em.find would return inconsistent results depending on JPA implementation.
        // Calling em.remove() would be an option in case if it persists in cache.
    }

}
