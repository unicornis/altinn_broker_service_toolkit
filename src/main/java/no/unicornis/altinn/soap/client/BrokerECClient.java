package no.unicornis.altinn.soap.client;


import no.unicornis.altinn.soap.models.GetAvailableFiles;
import no.unicornis.altinn.soap.models.UploadFile;

import no.altinn.brokerserviceexternalecstreamed.ReceiptExternalStreamedBE;
import no.altinn.brokerserviceexternalec.*;
import no.altinn.brokerserviceexternalecstreamed.IBrokerServiceExternalECStreamedDownloadFileStreamedECAltinnFaultFaultFaultMessage;
import no.altinn.brokerserviceexternalecstreamed.IBrokerServiceExternalECStreamedUploadFileStreamedECAltinnFaultFaultFaultMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;

/**
 * Created by taldev on 14/10/16.
 *
 * Originally written by tba @ brreg.
 */
@SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
@Component
public class BrokerECClient {

    @SuppressWarnings("unused")
    @Autowired
    BrokerECClientImpl brokerECClientBean;

    @SuppressWarnings("unused")
    @Autowired
    BrokerECStreamedClientImpl brokerECStreamedClientBean;

    public BrokerServiceAvailableFileList getAvailableFiles(GetAvailableFiles getAvailableFiles) throws IBrokerServiceExternalECGetAvailableFilesECAltinnFaultFaultFaultMessage {
        return brokerECClientBean.getReferences(getAvailableFiles);
    }

    public ReceiptExternalStreamedBE uploadFile(UploadFile uploadFile, DataHandler dataHandler) throws IBrokerServiceExternalECInitiateBrokerServiceECAltinnFaultFaultFaultMessage, IBrokerServiceExternalECStreamedUploadFileStreamedECAltinnFaultFaultFaultMessage {
        String reference = brokerECClientBean.initiateService(uploadFile);
        return brokerECStreamedClientBean.uploadFile(reference, uploadFile.getZipFileName(), dataHandler);
    }

    @SuppressWarnings("SameParameterValue")
    public DataHandler downloadFile(String reference) throws IBrokerServiceExternalECStreamedDownloadFileStreamedECAltinnFaultFaultFaultMessage {
        return brokerECStreamedClientBean.downloadFile(reference);
    }

    @SuppressWarnings("SameParameterValue")
    public void confirmDownloaded(String fileReference) throws IBrokerServiceExternalECConfirmDownloadedECAltinnFaultFaultFaultMessage {
        brokerECClientBean.confirmDownloaded(fileReference);
    }
}
