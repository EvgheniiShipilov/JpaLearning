package md.endava.domain;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by esipilov on 4/5/2017.
 */
@Embeddable
public class EmbeddedInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String bankId;

    private boolean hasCriminalRecord;

    private InnerInfo innerInfo;

    public InnerInfo getInnerInfo() {
        return innerInfo;
    }

    public void setInnerInfo(InnerInfo innerInfo) {
        this.innerInfo = innerInfo;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public boolean isHasCriminalRecord() {
        return hasCriminalRecord;
    }

    public void setHasCriminalRecord(boolean hasCriminalRecord) {
        this.hasCriminalRecord = hasCriminalRecord;
    }

    @Embeddable
    public static class InnerInfo implements Serializable {

        private static long serialVersionUID = 2L;

        private String previousName;

        public String getPreviousName() {
            return previousName;
        }

        public void setPreviousName(String previousName) {
            this.previousName = previousName;
        }
    }

}
