package md.endava.test;

import md.endava.domain.User;
import md.endava.persistence.EntityDao;
import org.eclipse.persistence.jpa.jpql.Assert;
import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by esipilov on 4/4/2017.
 */
public class T1_SimpleSelectTest {

    private static EntityDao dao;
    private static CriteriaBuilder criteriaBuilder;
    private static EntityManagerFactory emf;
    private EntityManager em;

    @BeforeAll
    public static void setUp(){
        emf = Persistence.createEntityManagerFactory("test");
        criteriaBuilder = emf.getCriteriaBuilder();

        dao = new EntityDao();
        User user = new User();
        user.setName("user");
        user.setLastLogin(Instant.now());
        dao.persist(user);
    }

    @BeforeEach
    public void setUpEach(){
        em = emf.createEntityManager();
    }

    @AfterEach
    public void tearDownEach(){
        em.close();
        em = null;
    }

    @AfterAll
    public static void tearDown(){
        dao.closeEntityManagerFactory();
    }

    @Test
    public void testCriteria_whenSelect_thenReturnValue(){
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        CriteriaQuery<User> criteriaQuery = query.select(root);

        List<User> resultList = getResultList(criteriaQuery);
        assertTrue(resultList.size()== 1);
    }

    private <T> List<T> getResultList(CriteriaQuery<T> criteriaQuery){
        TypedQuery<T> typedQuery = em.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }
}
