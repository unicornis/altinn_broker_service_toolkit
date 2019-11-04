package no.unicornis.altinn.models;

import javax.xml.datatype.XMLGregorianCalendar;

@SuppressWarnings("unused")
public class AltinnFile {

    private String fileName;
    private String fileReference;
    private long fileSize;
    private String fileStatus;
    private int receiptID;
    private String reportee;
    private String sendersReference;
    private XMLGregorianCalendar sentDate;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileReference() {
        return fileReference;
    }

    public void setFileReference(String fileReference) {
        this.fileReference = fileReference;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(String fileStatus) {
        this.fileStatus = fileStatus;
    }

    public int getReceiptID() {
        return receiptID;
    }

    public void setReceiptID(int receiptID) {
        this.receiptID = receiptID;
    }

    public String getReportee() {
        return reportee;
    }

    public void setReportee(String reportee) {
        this.reportee = reportee;
    }

    public String getSendersReference() {
        return sendersReference;
    }

    public void setSendersReference(String sendersReference) {
        this.sendersReference = sendersReference;
    }

    public XMLGregorianCalendar getSentDate() {
        return sentDate;
    }

    public void setSentDate(XMLGregorianCalendar sentDate) {
        this.sentDate = sentDate;
    }

    public AltinnFile() {

    }
}