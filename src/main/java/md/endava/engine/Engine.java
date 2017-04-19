package md.endava.engine;

import md.endava.domain.EmbeddedInfo;
import md.endava.domain.User;
import md.endava.persistence.EntityDao;

import java.time.Instant;

/**
 * Created by esipilov on 3/31/2017.
 */
public class Engine {
    private static final String PERSISTENCE_UNIT_NAME = "test";

    public static void main(String[] args) {
        EntityDao dao = new EntityDao();
        User user = new User();
        user.setName("Baz");
        user.setLastLogin(Instant.now());
        EmbeddedInfo privateInfo = new EmbeddedInfo();
        privateInfo.setBankId("ABC1001");
        EmbeddedInfo.InnerInfo innerInfo = new EmbeddedInfo.InnerInfo();
        innerInfo.setPreviousName("Bar");
        privateInfo.setInnerInfo(innerInfo);
        user.setPrivateInfo(privateInfo);

        dao.persist(user);
    }
}
