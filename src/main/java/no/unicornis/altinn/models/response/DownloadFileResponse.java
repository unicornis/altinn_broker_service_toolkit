package no.unicornis.altinn.models.response;

import org.apache.commons.codec.binary.Base64;

/**
 * Created by taldev on 17/10/16.
 *
 * Wrapper class to be used for JSON response.
 */
public class DownloadFileResponse {
    private String fileData;
    private String reference;

    @SuppressWarnings("unused")
    public String getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = Base64.encodeBase64String(fileData);
    }

    @SuppressWarnings("unused")
    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
