package md.endava.test.jpql;

import md.endava.domain.EmbeddedInfo;
import md.endava.domain.User;
import md.endava.test.AbstractTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

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
        user = em.merge(user);
        em.refresh(user);
        assertNotNull(user.getPrivateInfo().getBankId());
    }


    /*
    * TODO consider deleting this example
    * */
    @Test
    public void testUpdate_ExtendedContext(){
        User user = em.createQuery("FROM User u WHERE u.name = :name", User.class)
                .setParameter("name", "User 1.1")
                .getSingleResult();

        // update bank id = 100
        user.setPrivateInfo(new EmbeddedInfo());
        user.getPrivateInfo().setBankId("100");
        em.merge(user);

        // update bank id = 200
        em.getTransaction().begin();
        em.createQuery("UPDATE User u SET u.privateInfo.bankId = :bankId where u.name = :name")
                .setParameter("bankId", "200")
                .setParameter("name", "User 1.1")
                .executeUpdate();
        em.getTransaction().commit();

        user = userService.getUserByName("User 1.1");
        assertEquals(user.getPrivateInfo().getBankId(), "200");
    }

}
