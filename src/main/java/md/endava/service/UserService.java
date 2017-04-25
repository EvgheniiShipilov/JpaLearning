package md.endava.service;

import md.endava.domain.Department;
import md.endava.domain.User;
import md.endava.persistence.EntityDao;

import java.time.Instant;
import java.util.Date;

/**
 * Created by Clon on 24.04.2017.
 */
public class UserService {

    EntityDao dao = new EntityDao();

    public User createUser(String name){
        return createUser(name, null);

    }

    public User createUser(String name, Department department){
        User user = new User();
        user.setName(name);
        user.setDepartment(department);
        user.setLastLogin(Instant.now());
        user.setAccountCreationDate(new Date());
        dao.persist(user);
        return user;
    }

}