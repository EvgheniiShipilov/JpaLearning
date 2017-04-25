package md.endava.test;

import md.endava.domain.Department;
import md.endava.persistence.EntityDao;
import md.endava.persistence.PersistenceUtils;
import md.endava.service.DepartmentService;
import md.endava.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/**
 * Created by esipilov on 4/4/2017.
 */
public class AbstractTest {

    protected static EntityDao dao;
    protected static UserService userService = new UserService();
    protected static DepartmentService departmentService = new DepartmentService();

    private static Boolean isInitialized = false;

    protected EntityManager em;

    @BeforeAll
    public static void setUp() {
        synchronized (isInitialized) {
            if (!isInitialized) {
                Department department = departmentService.createDepartment("department 1");
                userService.createUser("User 1.1", department);
                userService.createUser("User 1.2", department);
                isInitialized = true;
            }
        }
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

    protected <T> List<T> getResultList(CriteriaQuery<T> criteriaQuery) {
        TypedQuery<T> typedQuery = em.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }
}
