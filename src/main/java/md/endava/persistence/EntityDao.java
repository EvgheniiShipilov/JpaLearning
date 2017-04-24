package md.endava.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by esipilov on 4/3/2017.
 */
public class EntityDao {

    public void persist(Object entity){
        EntityManager em = PersistenceUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

}
