package md.endava.test.criteria;

import md.endava.domain.User;
import md.endava.persistence.PersistenceUtils;
import md.endava.test.AbstractTest;
import org.junit.jupiter.api.Test;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by esipilov on 25/4/2017.
 */
public class T1_BasicQueries extends AbstractTest {

    @Test
    public void testCriteria_whenSelect_thenReturnValue() {
        CriteriaBuilder criteriaBuilder = PersistenceUtils.getEntityManagerFactory().createEntityManager().getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        CriteriaQuery<User> criteriaQuery = query.select(root);

        List<User> resultList = getResultList(criteriaQuery);
        assertTrue(resultList.size() == 2);
    }

}
