package no.unicornis.altinn.soap.client;

import no.unicornis.altinn.soap.models.GetAvailableFiles;
import no.unicornis.altinn.soap.models.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import no.altinn.brokerserviceexternalec.*;

import java.util.Map;

/**
 * Created by taldev on 14/10/16.
 *
 * Originally written by tba @ brreg.
 */
@Component
public class BrokerECClientImpl {

    @SuppressWarnings("unused")
    @Value("${formidling.altinn.security.entity}")
    private String entity;

    @SuppressWarnings("unused")
    @Value("${formidling.altinn.security.entityusername}")
    private String systemUserName;

    @SuppressWarnings("unused")
    @Value("${formidling.altinn.security.entitypassword}")
    private String systemPassword;

    @SuppressWarnings("CanBeFinal")
    private ObjectFactory objectFactory;

    @SuppressWarnings("unused")
    @Autowired
    private IBrokerServiceExternalEC brokerServiceExternalEC;

    public BrokerECClientImpl() {
        objectFactory = new ObjectFactory();
    }

    public String initiateService(UploadFile initiateInfo) throws IBrokerServiceExternalECInitiateBrokerServiceECAltinnFaultFaultFaultMessage {
        return brokerServiceExternalEC.initiateBrokerServiceEC(systemUserName, systemPassword, getBrokerServiceInitiation(initiateInfo));
    }

    public BrokerServiceAvailableFileList getReferences(GetAvailableFiles getAvailableFiles) throws IBrokerServiceExternalECGetAvailableFilesECAltinnFaultFaultFaultMessage {
        return brokerServiceExternalEC.getAvailableFilesEC(systemUserName, systemPassword, getBrokerServiceSearch(getAvailableFiles));
    }

    public void confirmDownloaded(String fileReference) throws IBrokerServiceExternalECConfirmDownloadedECAltinnFaultFaultFaultMessage {
        brokerServiceExternalEC.confirmDownloadedEC(systemUserName, systemPassword, fileReference, entity);
    }

    private BrokerServiceInitiation getBrokerServiceInitiation(UploadFile initiateInfo) {
        BrokerServiceInitiation brokerServiceInitiation = objectFactory.createBrokerServiceInitiation();
        brokerServiceInitiation.setManifest(getManifest(initiateInfo));
        brokerServiceInitiation.setRecipientList(getArrayOfRecipient(initiateInfo));
        return brokerServiceInitiation;
    }

    private Manifest getManifest(UploadFile initiateInfo) {
        Manifest manifest = objectFactory.createManifest();
        manifest.setExternalServiceCode(initiateInfo.getExternalServiceCode());
        manifest.setExternalServiceEditionCode(initiateInfo.getExternalServiceEditionCode());
        manifest.setReportee(entity);
        manifest.setSendersReference(initiateInfo.getSendersReference());
        manifest.setFileList(objectFactory.createManifestFileList(getArrayOfFile(initiateInfo)));
        ArrayOfProperty arrayOfProperty = objectFactory.createArrayOfProperty();
        for (Map.Entry<String, String> entry : initiateInfo.getProperties().entrySet()) {
            Property property = objectFactory.createProperty();
            property.setPropertyKey(entry.getKey());
            property.setPropertyValue(entry.getValue());
            arrayOfProperty.getProperty().add(property);
        }
        if(arrayOfProperty.getProperty().size()>0) {
            manifest.setPropertyList(objectFactory.createManifestPropertyList(arrayOfProperty));
        }
        return manifest;
    }

    private ArrayOfFile getArrayOfFile(UploadFile initiateInfo) {
        ArrayOfFile arrayOfFile = objectFactory.createArrayOfFile();
        for (int i = 0; i < initiateInfo.getFileList().size(); i++) {
            File file = objectFactory.createFile();
            file.setFileName(initiateInfo.getFileList().get(i));
            arrayOfFile.getFile().add(file);
        }
        return arrayOfFile;
    }

    private ArrayOfRecipient getArrayOfRecipient(UploadFile initiateInfo) {
        ArrayOfRecipient arrayOfRecipient = objectFactory.createArrayOfRecipient();
        for (int i = 0; i < initiateInfo.getReceiverList().size(); i++) {
            Recipient recipient = objectFactory.createRecipient();
            recipient.setPartyNumber(initiateInfo.getReceiverList().get(i));
            arrayOfRecipient.getRecipient().add(recipient);
        }
        return arrayOfRecipient;
    }


    private BrokerServiceSearch getBrokerServiceSearch(GetAvailableFiles getAvailableFiles) {
        BrokerServiceSearch brokerServiceSearch = objectFactory.createBrokerServiceSearch();
        /*brokerServiceSearch.setExternalServiceCode(objectFactory.createBrokerServiceSearchExternalServiceCode(getAvailableFiles.getExternalServiceCode()));
        brokerServiceSearch.setExternalServiceEditionCode(getAvailableFiles.getExternalServiceEditionCode());*/
        brokerServiceSearch.setReportee(entity);
        brokerServiceSearch.setFileStatus(getAvailableFiles.getBrokerServiceAvailableFileStatus());
        return brokerServiceSearch;
    }
}