package no.unicornis.altinn.service;

import no.altinn.brokerserviceexternalec.IBrokerServiceExternalECInitiateBrokerServiceECAltinnFaultFaultFaultMessage;
import no.altinn.brokerserviceexternalecstreamed.IBrokerServiceExternalECStreamedUploadFileStreamedECAltinnFaultFaultFaultMessage;
import no.altinn.brokerserviceexternalecstreamed.ReceiptExternalStreamedBE;
import no.unicornis.altinn.exceptions.WebServiceException;
import no.unicornis.altinn.soap.client.BrokerECClientImpl;
import no.unicornis.altinn.soap.client.BrokerECStreamedClientImpl;
import no.unicornis.altinn.soap.config.ECClientConfig;
import no.unicornis.altinn.soap.models.UploadFile;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.mail.util.ByteArrayDataSource;
import java.util.Base64;


@Service
public class UploadService {


    private static final Logger log = Logger.getLogger(UploadService.class);

    public String initiateUpload(String fileName,
                                 String sendersReference,
                                 String externalServiceCode,
                                 int externalServiceEditionCode,
                                 String recipient) throws WebServiceException {
        log.info("Initiating upload");
        log.info(String.format("Parameters: file_name=%s, sender_reference=%s", fileName, sendersReference));

        log.debug("Setting up application context.");
        ApplicationContext context = new AnnotationConfigApplicationContext(ECClientConfig.class);
        final BrokerECClientImpl client = context.getBean(BrokerECClientImpl.class);
        log.debug("Broker client set up.");
        log.debug("Assembling upload package.");
        UploadFile uploadFile = new UploadFile();
        uploadFile.setExternalServiceCode(externalServiceCode);
        uploadFile.setExternalServiceEditionCode(externalServiceEditionCode);
        uploadFile.setZipFileName(fileName);
        uploadFile.setSendersReference(sendersReference);
        uploadFile.addReceiver(recipient);

        log.debug("Initiating broker service.");
        String reference;
        try {
            reference = client.initiateService(uploadFile);
            log.info("Broker service reference: " + reference);
        } catch (IBrokerServiceExternalECInitiateBrokerServiceECAltinnFaultFaultFaultMessage exception) {
            log.error("Error in initiating broker service.", exception);
            throw new WebServiceException("Error in initiating broker service.", exception);
        }
        return reference;
    }

    public ReceiptExternalStreamedBE uploadFile(String reference,
                                                String fileData,
                                                String fileName,
                                                String mimeType) throws WebServiceException {
        ApplicationContext context = new AnnotationConfigApplicationContext(ECClientConfig.class);
        final BrokerECStreamedClientImpl streamedClient = context.getBean(BrokerECStreamedClientImpl.class);
        log.debug("Streamed client set up.");

        log.debug("Decoding and packaging file data.");
        ByteArrayDataSource ds = new ByteArrayDataSource(Base64.getDecoder().decode(fileData), mimeType);
        DataHandler dataHandler = new DataHandler(ds);
        ReceiptExternalStreamedBE result;
        log.debug("Starting file upload.");
        try {
            result = streamedClient.uploadFile(reference, fileName, dataHandler);
        } catch (IBrokerServiceExternalECStreamedUploadFileStreamedECAltinnFaultFaultFaultMessage exception) {
            log.error("Error occurred while performing file upload.", exception);
            throw new WebServiceException("Error occurred while performing file upload.", exception);
        }
        return result;
    }
}
