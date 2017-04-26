package md.endava.domain;

import javax.persistence.*;

/**
 * Created by esipilov on 4/26/2017.
 */
@Entity
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "project_type")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Project {

    @Id
    @GeneratedValue

    protected Integer id;

    protected String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
