package md.endava.service;

import md.endava.domain.JavaProject;
import md.endava.domain.Project;
import md.endava.domain.ScalaProject;
import md.endava.persistence.EntityDao;
import md.endava.persistence.PersistenceUtils;

import javax.persistence.EntityManager;

/**
 * Created by Clon on 24.04.2017.
 */
public class ProjectService {

    EntityDao dao = new EntityDao();

    public Project getProjectByName(String projectName) {
        EntityManager em = PersistenceUtils.getEntityManager();
        return em.createQuery("FROM Project p WHERE p.name = :name", Project.class)
                .setParameter("name", projectName)
                .getSingleResult();
    }

    public JavaProject createJavaProject(String name, String javaVersion) {
        JavaProject project = new JavaProject();
        project.setName(name);
        project.setJavaVersion(javaVersion);
        dao.persist(project);
        return project;
    }

    public ScalaProject createScalaProject(String name, String scalaVersion) {
        ScalaProject project = new ScalaProject();
        project.setName(name);
        project.setScalaVersion(scalaVersion);
        dao.persist(project);
        return project;
    }
}
