package md.endava.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by esipilov on 4/3/2017.
 */
public class EntityDao {

    private EntityManagerFactory emf;

    protected EntityManagerFactory getEntityManagerFactory (){
        if (emf == null){
            emf = Persistence.createEntityManagerFactory("test");
        }
        return emf;
    }

    protected EntityManager getEntityManager(){
        return getEntityManagerFactory().createEntityManager();
    }

    public void persist(Object entity){
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void closeEntityManagerFactory(){
        emf.close();
    }

}
