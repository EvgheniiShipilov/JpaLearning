package md.endava;

import md.endava.domain.Department;
import md.endava.domain.User;
import md.endava.persistence.EntityDao;

import java.time.Instant;

/**
 * Created by Clon on 24.04.2017.
 */
public class DepartmentService {

    EntityDao dao = new EntityDao();

    public Department createDepartment(String name){
        Department department = new Department();
        department.setName(name);
        dao.persist(department);
        return department;
    }

}
