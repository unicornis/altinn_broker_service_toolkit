package no.unicornis.altinn.models.response;

/**
 * Created by taldev on 17/10/16.
 *
 * Helper class for JSON response of an upload response from Altinn.
 */
public class UploadFileResponse {

    private int receiptID;
    private String receiptStatusCode;
    private String receiptText;
    private String reference;

    public int getReceiptID() {
        return receiptID;
    }

    public String getReceiptStatusCode() {
        return receiptStatusCode;
    }

    public String getReceiptText() {
        return receiptText;
    }

    public String getReference() {
        return reference;
    }

    public void setReceiptID(int receiptID) {
        this.receiptID = receiptID;
    }

    public void setReceiptStatusCode(String receiptStatusCode) {
        this.receiptStatusCode = receiptStatusCode;
    }

    public void setReceiptText(String receiptText) {
        this.receiptText = receiptText;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
