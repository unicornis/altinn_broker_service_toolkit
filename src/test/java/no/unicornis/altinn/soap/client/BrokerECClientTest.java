package no.unicornis.altinn.soap.client;

import no.altinn.brokerserviceexternalec.BrokerServiceAvailableFileList;
import no.altinn.brokerserviceexternalec.BrokerServiceAvailableFileStatus;
import no.altinn.brokerserviceexternalec.IBrokerServiceExternalECGetAvailableFilesECAltinnFaultFaultFaultMessage;
import no.altinn.brokerserviceexternalec.IBrokerServiceExternalECInitiateBrokerServiceECAltinnFaultFaultFaultMessage;
import no.altinn.brokerserviceexternalecstreamed.IBrokerServiceExternalECStreamedDownloadFileStreamedECAltinnFaultFaultFaultMessage;
import no.altinn.brokerserviceexternalecstreamed.IBrokerServiceExternalECStreamedUploadFileStreamedECAltinnFaultFaultFaultMessage;
import no.altinn.brokerserviceexternalecstreamed.ReceiptExternalStreamedBE;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import no.unicornis.altinn.soap.config.ECClientConfig;
import no.unicornis.altinn.soap.models.GetAvailableFiles;
import no.unicornis.altinn.soap.models.UploadFile;


import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;


@ContextConfiguration(classes = {ECClientConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class BrokerECClientTest {

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @Autowired
    BrokerECClient brokerECClient;

    @Test
    public void getAvailableFilesTest() throws IBrokerServiceExternalECGetAvailableFilesECAltinnFaultFaultFaultMessage {
        GetAvailableFiles getAvailableFiles = new GetAvailableFiles();
        getAvailableFiles.setExternalServiceCode("4202");
        getAvailableFiles.setExternalServiceEditionCode(1);
        getAvailableFiles.setBrokerServiceAvailableFileStatus(BrokerServiceAvailableFileStatus.UPLOADED);
        BrokerServiceAvailableFileList brokerServiceAvailableFileList = brokerECClient.getAvailableFiles(getAvailableFiles);
        assertThat(brokerServiceAvailableFileList.getBrokerServiceAvailableFile(), is(not(nullValue())));
    }

    @Ignore
    @Test (expected = IBrokerServiceExternalECGetAvailableFilesECAltinnFaultFaultFaultMessage.class)
    public void getAvailableFilesTestNoServiceEditionCode() throws IBrokerServiceExternalECGetAvailableFilesECAltinnFaultFaultFaultMessage {
        GetAvailableFiles getAvailableFiles = new GetAvailableFiles();
        getAvailableFiles.setExternalServiceCode("4202");
        getAvailableFiles.setBrokerServiceAvailableFileStatus(BrokerServiceAvailableFileStatus.UPLOADED);
        BrokerServiceAvailableFileList brokerServiceAvailableFileList = brokerECClient.getAvailableFiles(getAvailableFiles);
        assertThat(brokerServiceAvailableFileList.getBrokerServiceAvailableFile(), is(not(nullValue())));
    }



    @Ignore
    @Test
    public void uploadFileTest() throws IBrokerServiceExternalECStreamedUploadFileStreamedECAltinnFaultFaultFaultMessage, IBrokerServiceExternalECInitiateBrokerServiceECAltinnFaultFaultFaultMessage, IOException {
        UploadFile uploadFile = new UploadFile();
        uploadFile.setExternalServiceCode("4202");
        uploadFile.setExternalServiceEditionCode(1);
        uploadFile.addFile("henvendelse.xml");
        uploadFile.addFile("Test.txt");
        uploadFile.setSendersReference("TestReference4");
        uploadFile.addReceiver("910514350");

        uploadFile.setZipFileName("Verdensbeste_NIF.zip");
        ClassPathResource classPathResource = new ClassPathResource("Verdensbeste_NIF.zip");
        FileDataSource fileDataSource = new FileDataSource(classPathResource.getFile());
        DataHandler dataHandler = new DataHandler(fileDataSource);
        ReceiptExternalStreamedBE receiptExternalStreamedBE = brokerECClient.uploadFile(uploadFile, dataHandler);

        System.out.println(receiptExternalStreamedBE.getReceiptText().getValue());

        assertThat(receiptExternalStreamedBE.getReceiptId(), is(notNullValue()));
    }


    @Ignore
    @Test
    public void downloadFileTest() throws IBrokerServiceExternalECStreamedDownloadFileStreamedECAltinnFaultFaultFaultMessage, IOException {
        DataHandler dataHandler = brokerECClient.downloadFile("89dc7566-900c-4147-bc1f-e95c6e4c04dd");
        ByteArrayOutputStream buffOS= new ByteArrayOutputStream();
        dataHandler.writeTo(buffOS);
        FileOutputStream fos = new FileOutputStream("/tmp/broker/" + "89dc7566-900c-4147-bc1f-e95c6e4c04dd" + ".zip");
        fos.write(buffOS.toByteArray());
        fos.close();

        assertThat(dataHandler.getContent(), is(notNullValue()));
    }

    @Ignore
    @Test
    public void purgeItem() throws Exception {
        brokerECClient.confirmDownloaded("8a219d6f-b927-4d21-8880-d23d20a4d907");
    }
}
