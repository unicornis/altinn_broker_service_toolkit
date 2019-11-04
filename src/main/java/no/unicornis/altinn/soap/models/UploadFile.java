package no.unicornis.altinn.soap.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by taldev on 14/10/16.
 *
 * Originally created by tba @ brreg.
 *
 * Helper class to perform a file upload.
 */
public class UploadFile {
    private String externalServiceCode;
    private int externalServiceEditionCode;
    private ArrayList<String> receiverList;
    private ArrayList<String> fileList;
    private String zipFileName;
    private String sendersReference;
    @SuppressWarnings("CanBeFinal")
    private Map<String, String> properties;

    public UploadFile() {

        receiverList = new ArrayList<>();
        fileList = new ArrayList<>();
        properties = new HashMap<>();
    }

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

    public String getZipFileName() {
        return zipFileName;
    }

    public void setZipFileName(String fileName) {
        this.zipFileName = fileName;
    }

    public String getSendersReference() {
        return sendersReference;
    }

    public void setSendersReference(String sendersReference) {
        this.sendersReference = sendersReference;
    }

    public ArrayList<String> getFileList() {
        return fileList;
    }

    @SuppressWarnings("unused")
    public void setFileList(ArrayList<String> fileList) {
        this.fileList = fileList;
    }

    public void addFile(String file) {
        this.fileList.add(file);
    }

    public ArrayList<String> getReceiverList() {
        return receiverList;
    }

    @SuppressWarnings("unused")
    public void setReceiverList(ArrayList<String> receiverList) {
        this.receiverList = receiverList;
    }

    public void addReceiver(String receiver) {
        this.receiverList.add(receiver);
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    @SuppressWarnings("unused")
    public void addProperty(String key, String value) {
        this.properties.put(key, value);
    }

    @Override
    public String toString() {
        String receiverListString;
        String fileListString;
        if (!receiverList.isEmpty()) {
            receiverListString = receiverList.get(0);
            for (int i = 1; i < receiverList.size(); i++) {
                receiverListString += ", " + receiverList.get(i);
            }
        } else {
            receiverListString = "";
        }
        if (!fileList.isEmpty()) {
            fileListString = fileList.get(0);
            for (int i = 1; i < fileList.size(); i++) {
                fileListString += ", " + fileList.get(i);
            }
        } else {
            fileListString = "";
        }
        return "Initiate {" + '\'' +
                "externalServiceCode: " + externalServiceCode + '\'' +
                ", externalServiceEditionCode: " + externalServiceEditionCode + '\'' +
                ", filename(s): " + fileListString + '\'' +
                ", zipFileName: " + zipFileName + '\'' +
                ", sendersReference: " + sendersReference + '\'' +
                ", receiver(s): " + receiverListString;
    }
}
