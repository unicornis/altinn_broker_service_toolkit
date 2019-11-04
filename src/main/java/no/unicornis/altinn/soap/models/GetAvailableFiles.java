package no.unicornis.altinn.soap.models;

import no.altinn.brokerserviceexternalec.BrokerServiceAvailableFileStatus;

/**
 * Created by taldev on 14/10/16.
 *
 * Originally written by tba @ brreg.
 *
 * Helper class to construct a request for available files.
 */
public class GetAvailableFiles {
    private String externalServiceCode;
    private int externalServiceEditionCode;
    private BrokerServiceAvailableFileStatus brokerServiceAvailableFileStatus;

    public String getExternalServiceCode() {
        return externalServiceCode;
    }

    public void setExternalServiceCode(String externalServiceCode) {
        this.externalServiceCode = externalServiceCode;
    }

    public int getExternalServiceEditionCode() {
        return externalServiceEditionCode;
    }

    public void setExternalServiceEditionCode(int externalServiceEditionCode) {
        this.externalServiceEditionCode = externalServiceEditionCode;
    }

    public BrokerServiceAvailableFileStatus getBrokerServiceAvailableFileStatus() {
        return brokerServiceAvailableFileStatus;
    }

    public void setBrokerServiceAvailableFileStatus(BrokerServiceAvailableFileStatus brokerServiceAvailableFileStatus) {
        this.brokerServiceAvailableFileStatus = brokerServiceAvailableFileStatus;
    }

    @Override
    public String toString() {
        return "GetAvailableFiles {" + '\'' +
                "externalServiceCode: " + externalServiceCode + '\'' +
                ", externalServiceEditionCode: " + externalServiceEditionCode + '\'' +
                ", brokerServiceAvailableFileStatus: " + brokerServiceAvailableFileStatus.value();
    }
}
