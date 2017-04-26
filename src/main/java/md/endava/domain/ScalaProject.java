package md.endava.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by esipilov on 4/26/2017.
 */
@Entity
@DiscriminatorValue("scala")
public class ScalaProject extends Project {

    private String scalaVersion;

    public String getScalaVersion() {
        return scalaVersion;
    }

    public void setScalaVersion(String scalaVersion) {
        this.scalaVersion = scalaVersion;
    }
}
