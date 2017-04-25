package md.endava.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Clon on 24.04.2017.
 */
public class PersistenceUtils {

    private static final String PERSISTENCE_UNIT_NAME = "test";

    private static volatile EntityManagerFactory emf;

    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            synchronized (PersistenceUtils.class) {
                if (emf == null) {
                    emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
                }
            }
        }
        return emf;
    }

    public static EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

    @Override
    protected void finalize() throws Throwable {
        if (emf != null && emf.isOpen()) {
            System.out.print("Closing entity manager factory!");
            emf.close();
        }
        super.finalize();
    }

}
