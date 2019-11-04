package no.unicornis.altinn.models.response;

public class ConfirmDownloadResponse {
    private boolean confirmed;
    private String statusMessage;

    public boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
