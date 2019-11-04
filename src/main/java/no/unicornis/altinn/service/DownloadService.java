package no.unicornis.altinn.service;


import no.altinn.brokerserviceexternalec.BrokerServiceAvailableFileList;
import no.altinn.brokerserviceexternalec.BrokerServiceAvailableFileStatus;
import no.altinn.brokerserviceexternalec.IBrokerServiceExternalECGetAvailableFilesECAltinnFaultFaultFaultMessage;
import no.unicornis.altinn.exceptions.WebServiceException;
import no.unicornis.altinn.soap.client.BrokerECClientImpl;
import no.unicornis.altinn.soap.config.ECClientConfig;
import no.unicornis.altinn.soap.models.GetAvailableFiles;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
public class DownloadService {

    private static final Logger log = Logger.getLogger(DownloadService.class);


    public BrokerServiceAvailableFileList getAvailableFiles(String status) throws WebServiceException {
        GetAvailableFiles getAvailableFiles = new GetAvailableFiles();

        BrokerServiceAvailableFileStatus fileStatus;
        switch (status.toLowerCase()) {
            case "uploaded": fileStatus = BrokerServiceAvailableFileStatus.UPLOADED; break;
            case "downloaded": fileStatus = BrokerServiceAvailableFileStatus.DOWNLOADED; break;
            default: fileStatus = BrokerServiceAvailableFileStatus.UPLOADED; break;
        }
        getAvailableFiles.setBrokerServiceAvailableFileStatus(fileStatus);
        log.debug(String.format("Querying WS for files with status %s", status));
        log.debug("Setting up application contexts.");
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ECClientConfig.class);
        final BrokerECClientImpl brokerECClientBean = applicationContext.getBean(BrokerECClientImpl.class);
        BrokerServiceAvailableFileList availableFiles;

        log.debug("Sending request.");
        try {
            availableFiles = brokerECClientBean.getReferences(getAvailableFiles);

        } catch (IBrokerServiceExternalECGetAvailableFilesECAltinnFaultFaultFaultMessage exception) {
            log.error(exception.getMessage(), exception);
            throw new WebServiceException(exception.getMessage());
        }
        return availableFiles;
    }
}
