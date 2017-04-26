package md.endava.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by esipilov on 4/26/2017.
 */
@Entity
@DiscriminatorValue("java")
public class JavaProject extends Project {

    private String javaVersion;

    public String getJavaVersion() {
        return javaVersion;
    }

    public void setJavaVersion(String javaVersion) {
        this.javaVersion = javaVersion;
    }
}
