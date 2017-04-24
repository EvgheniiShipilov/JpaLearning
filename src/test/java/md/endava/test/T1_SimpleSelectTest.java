package md.endava.test;

import md.endava.DepartmentService;
import md.endava.UserService;
import md.endava.domain.Department;
import md.endava.domain.User;
import md.endava.persistence.EntityDao;
import md.endava.persistence.PersistenceUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
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
    private static UserService userService = new UserService();
    private static DepartmentService departmentService = new DepartmentService();

    private EntityManager em;

    @BeforeAll
    public static void setUp() {
        Department department = departmentService.createDepartment("department 1");
        userService.createUser("User 1.1", department);
        userService.createUser("User 1.2", department);
    }


    @Test
    public void testCriteria_whenSelect_thenReturnValue() {
        CriteriaBuilder criteriaBuilder = PersistenceUtils.getEntityManagerFactory().createEntityManager().getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        CriteriaQuery<User> criteriaQuery = query.select(root);

        List<User> resultList = getResultList(criteriaQuery);
        assertTrue(resultList.size() == 2);
    }

    private <T> List<T> getResultList(CriteriaQuery<T> criteriaQuery) {
        TypedQuery<T> typedQuery = em.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    @BeforeEach
    public void setUpEach() {
        em = PersistenceUtils.getEntityManager();
    }

    @AfterEach
    public void tearDownEach() {
        if (em != null && em.isOpen()) {
            em.close();
            em = null;
        }
    }
}
