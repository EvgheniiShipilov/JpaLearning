package md.endava.domain;

import javax.persistence.*;
import java.time.Instant;

/**
 * Created by esipilov on 3/31/2017.
 */
@Entity
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Instant lastLogin;

    private String name;

    private EmbeddedInfo privateInfo;

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
}
