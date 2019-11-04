package no.unicornis.altinn;

import javax.activation.DataHandler;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import no.altinn.brokerserviceexternalec.*;
import no.altinn.brokerserviceexternalecstreamed.IBrokerServiceExternalECStreamedDownloadFileStreamedECAltinnFaultFaultFaultMessage;
import no.altinn.brokerserviceexternalecstreamed.ReceiptExternalStreamedBE;
import no.unicornis.altinn.exceptions.NotFoundException;
import no.unicornis.altinn.exceptions.WebServiceException;
import no.unicornis.altinn.models.AltinnFile;
import no.unicornis.altinn.models.response.*;
import no.unicornis.altinn.service.DownloadService;
import no.unicornis.altinn.service.UploadService;
import no.unicornis.altinn.soap.config.ECClientConfig;
import no.unicornis.altinn.soap.client.*;

import org.apache.catalina.filters.RemoteAddrFilter;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;


@RestController
@EnableAutoConfiguration
@RequestMapping("/api")
public class BrokerServiceToolkit extends SpringBootServletInitializer {

    private static final Logger log = Logger.getLogger(BrokerServiceToolkit.class);

    private UploadService uploadService = new UploadService();
    private DownloadService downloadService = new DownloadService();

    @Value("${broker.service.external_service_code}")
    private String externalServiceCode;

    @Value("${broker.service.external_service_edition_code}")
    private int externalServiceEditionCode;

    @Value("${formidling.altinn.recipient}")
    private String recipient;

    @Value("${api_whitelist}")
    private List<String> apiWhitelist;

    @Value("${monitor_whitelist}")
    private List<String> monitorWhitelist;

    @RequestMapping("/getAvailableFiles")
    @ResponseBody
    private ArrayList<AltinnFile> getAvailableFiles(@RequestParam(value = "status", required = false, defaultValue = "uploaded") String status) throws NotFoundException, WebServiceException {
        log.info("Checking to see if there are new files available for download.");
        BrokerServiceAvailableFileList availableFiles = downloadService.getAvailableFiles(status);

        log.debug("Checking response state.");
        if (availableFiles.getBrokerServiceAvailableFile().isEmpty()) {
            log.info("No files available for download, returning HTTP 404.");
            throw new NotFoundException("No files available.");
        }
        log.info("Returning result as JSON.");
        return new AvailableFilesResponse(availableFiles.getBrokerServiceAvailableFile()).getFileList();
    }

    @RequestMapping(value="/uploadFile", method=RequestMethod.POST, produces="application/json")
    @ResponseBody
    private UploadFileResponse uploadFile(@RequestParam("file_name") String fileName,
                                          @RequestParam("sender_reference") String sendersReference,
                                          @RequestParam("file_data") String fileData,
                                          @RequestParam("mime_type") String mimeType) throws WebServiceException {
        log.info("Initiating file upload.");
        log.info(String.format("Parameters: file_name=%s, sender_reference=%s, mime_type=%s", fileName, sendersReference, mimeType));
        String reference = uploadService.initiateUpload(fileName, sendersReference, externalServiceCode,
                externalServiceEditionCode, recipient);

        ReceiptExternalStreamedBE result = uploadService.uploadFile(reference, fileData, fileName, mimeType);
        UploadFileResponse response = new UploadFileResponse();
        response.setReceiptID(result.getReceiptId());
        response.setReceiptStatusCode(result.getReceiptStatusCode().getValue());
        response.setReceiptText(result.getReceiptText().getValue());
        response.setReference(reference);
        log.info(String.format("Upload response: receiptID=%d, receiptStatusCode=%s, receiptText=%s, reference=%s",
                response.getReceiptID(), response.getReceiptStatusCode(), response.getReceiptText(), response.getReference()));
        return response;
    }

    @RequestMapping(value="downloadFile", method=RequestMethod.POST, produces="application/json")
    @ResponseBody
    private DownloadFileResponse downloadFile(@RequestParam("reference") String reference) throws WebServiceException {
        log.info("Initiating file download.");
        log.info("File reference: " + reference);
        log.debug("Setting up application context");
        ApplicationContext context = new AnnotationConfigApplicationContext(ECClientConfig.class);
        final BrokerECStreamedClientImpl streamedClient = context.getBean(BrokerECStreamedClientImpl.class);
        log.debug("Streamed client set up.");
        log.debug("Starting file download.");
        DataHandler dh;
        try {
            dh = streamedClient.downloadFile(reference);
        } catch (IBrokerServiceExternalECStreamedDownloadFileStreamedECAltinnFaultFaultFaultMessage exception) {
            log.error("Error occurred while downloading file with reference " + reference, exception);
            throw new WebServiceException("Error occurred while downloading file with reference " + reference, exception);
        }

        log.debug("Converting file data");
        byte[] fileData;
        try {
            final InputStream in = dh.getInputStream();
            fileData = IOUtils.toByteArray(in);
        } catch (IOException exception) {
            log.error("Error when converting file data.", exception);
            throw new WebServiceException("Error when converting file data.", exception);
        }
        log.debug("Assembling response object.");
        DownloadFileResponse response = new DownloadFileResponse();
        response.setFileData(fileData);
        response.setReference(reference);
        log.info("Returning file data for file reference: " + reference);
        return response;
    }

    @RequestMapping(value="confirmDownload", method=RequestMethod.POST, produces="application/json")
    @ResponseBody
    private ConfirmDownloadResponse confirmDownload(@RequestParam("reference") String reference) throws WebServiceException {
        log.info("Confirming download.");
        log.info("File reference: " + reference);
        log.debug("Setting up application context");
        ApplicationContext context = new AnnotationConfigApplicationContext(ECClientConfig.class);
        final BrokerECClientImpl client = context.getBean(BrokerECClientImpl.class);
        log.debug("Broker service client set up.");
        try {
            client.confirmDownloaded(reference);
        } catch (IBrokerServiceExternalECConfirmDownloadedECAltinnFaultFaultFaultMessage exception) {
            log.error("Error occured when trying to confirm download with reference " + reference, exception);
            throw new WebServiceException("Error occured when trying to confirm download with reference " + reference,
                                          exception);
        }
        log.debug("Confirmation successful.");
        ConfirmDownloadResponse response = new ConfirmDownloadResponse();
        response.setConfirmed(true);
        response.setStatusMessage("Download with reference " + reference + " confirmed.");
        log.info("Returning response");
        return response;
    }

    @RequestMapping(value="initiateUpload", method=RequestMethod.POST, produces="application/json")
    @ResponseBody
    private InitiateUploadResponse inititateUpload(@RequestParam("file_name") String fileName,
                                                   @RequestParam("sender_reference") String sendersReference) throws WebServiceException {
        String reference = uploadService.initiateUpload(fileName, sendersReference, externalServiceCode,
                externalServiceEditionCode, recipient);
        InitiateUploadResponse response = new InitiateUploadResponse();
        response.setReference(reference);
        return response;
    }


    @Bean
    public FilterRegistrationBean apiAddressFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setName("API IP whitelist");

        RemoteAddrFilter filter = new RemoteAddrFilter();
        apiWhitelist.forEach(filter::setAllow);

        filterRegistrationBean.setFilter(filter);
        filterRegistrationBean.addUrlPatterns("/api/*");
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean monitorAddressFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setName("Monitoring IP whitelist");

        RemoteAddrFilter filter = new RemoteAddrFilter();
        monitorWhitelist.forEach(filter::setAllow);

        filterRegistrationBean.setFilter(filter);
        filterRegistrationBean.addUrlPatterns("/health");
        return filterRegistrationBean;
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BrokerServiceToolkit.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(BrokerServiceToolkit.class, args);
    }
}