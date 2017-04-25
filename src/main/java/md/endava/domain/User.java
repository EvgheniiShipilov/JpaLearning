package md.endava.domain;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

/**
 * Created by esipilov on 3/31/2017.
 */
@Entity
@NamedQuery(name="findUserByName", query = "from User u where u.name = :name")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    private Instant lastLogin;

    private String name;

    private EmbeddedInfo privateInfo;

    private Date accountCreationDate;

    public Instant getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Instant lastLogin) {
        this.lastLogin = lastLogin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EmbeddedInfo getPrivateInfo() {
        return privateInfo;
    }

    public void setPrivateInfo(EmbeddedInfo privateInfo) {
        this.privateInfo = privateInfo;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Date getAccountCreationDate() {
        return accountCreationDate;
    }

    public void setAccountCreationDate(Date accountCreationDate) {
        this.accountCreationDate = accountCreationDate;
    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", lastLogin=" + lastLogin +
                ", name='" + name + '\'' +
                ", privateInfo=" + privateInfo +
                '}';
    }
}