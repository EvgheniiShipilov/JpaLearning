package md.endava.test.jpql;

import md.endava.domain.Project;
import md.endava.test.AbstractTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by esipilov on 26/4/2017.
 */
public class T8_Inheritance extends AbstractTest {

    @BeforeAll
    public static void setUpInheritanceTest() {
        projectService.createJavaProject("java project 1", "Java 1.8");
        projectService.createScalaProject("scala project 1", "Scala 1.0");
    }

    /*
    * JPQL will treat correctly addressing superclass in hierarchy.
    * */
    @Test
    public void testSelectBySuperType() {
        List<Project> resultList = em.createQuery("FROM Project p", Project.class)
                .getResultList();
        assertEquals(resultList.size(), 2);
    }

    /*
    * JPQL will also treat correctly addressing any subclass in hierarchy.
    * */
    @Test
    public void testSelectBySubType_FromExpression() {
        List<Project> resultList = em.createQuery("FROM JavaProject p", Project.class)
                .getResultList();
        assertEquals(resultList.size(), 1);
    }

    /*
    * Another way to work with subtypes is to explicitly specify the subtype with a TYPE(t) = entity name.
    * */
    @Test
    public void testSelectBySubType_TypeExpression() {
        List<Project> resultList = em.createQuery("FROM Project p WHERE TYPE(p) = JavaProject", Project.class)
                .getResultList();
        assertEquals(resultList.size(), 1);
    }

}
