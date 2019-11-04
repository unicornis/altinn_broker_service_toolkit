package no.unicornis.altinn.soap.client;

import no.altinn.brokerserviceexternalecstreamed.*;
import org.apache.cxf.headers.Header;
import org.apache.cxf.jaxb.JAXBDataBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by taldev on 14/10/16.
 *
 * Originally writter by tba @ brreg.
 */
@Component
public class BrokerECStreamedClientImpl {

    @SuppressWarnings({"CanBeFinal", "unused"})
    @Value("${formidling.altinn.security.entity}")
    private String entity;

    @SuppressWarnings({"CanBeFinal", "unused"})
    @Value("${formidling.altinn.security.entityusername}")
    private String systemUserName;

    @SuppressWarnings({"CanBeFinal", "unused"})
    @Value("${formidling.altinn.security.entitypassword}")
    private String systemPassword;

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @Autowired
    IBrokerServiceExternalECStreamed brokerServiceExternalECStreamed;

    public ReceiptExternalStreamedBE uploadFile(String reference, String file, DataHandler dataHandler) throws IBrokerServiceExternalECStreamedUploadFileStreamedECAltinnFaultFaultFaultMessage {
        List<Header> headerList = new ArrayList<>();
        Header userName = null;
        Header reportee = null;
        Header ref = null;
        Header password = null;
        Header filename = null;
        try {
            userName = new Header(new QName("http://www.altinn.no/services/ServiceEngine/Broker/2015/06", "UserName"), systemUserName, new JAXBDataBinding(String.class));
            reportee = new Header(new QName("http://www.altinn.no/services/ServiceEngine/Broker/2015/06", "Reportee"), entity, new JAXBDataBinding(String.class));
            ref = new Header(new QName("http://www.altinn.no/services/ServiceEngine/Broker/2015/06", "Reference"), reference, new JAXBDataBinding(String.class));
            password = new Header(new QName("http://www.altinn.no/services/ServiceEngine/Broker/2015/06", "Password"), systemPassword, new JAXBDataBinding(String.class));
            filename = new Header(new QName("http://www.altinn.no/services/ServiceEngine/Broker/2015/06", "FileName"), file, new JAXBDataBinding(String.class));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        headerList.add(userName);
        headerList.add(reportee);
        headerList.add(ref);
        headerList.add(password);
        headerList.add(filename);

        ObjectFactory objectFactory = new ObjectFactory();
        ((BindingProvider) brokerServiceExternalECStreamed).getRequestContext().put(Header.HEADER_LIST, headerList);
        //FileDataSource fileDataSource = new FileDataSource("D:\\devUsr\\brreg\\maven_projects\\Integration\\br-postmottak\\nuntius-formidling\\test.zip");
        //FileDataSource fileDataSource = new FileDataSource(file);
        StreamedPayloadECBE streamedPayloadECBE = objectFactory.createStreamedPayloadECBE();
        //DataHandler dataHandler = new DataHandler(fileDataSource);
        streamedPayloadECBE.setDataStream(dataHandler);
        return brokerServiceExternalECStreamed.uploadFileStreamedEC(streamedPayloadECBE);
    }

    public DataHandler downloadFile(String reference) throws IBrokerServiceExternalECStreamedDownloadFileStreamedECAltinnFaultFaultFaultMessage {
        return brokerServiceExternalECStreamed.downloadFileStreamedEC(systemUserName, systemPassword, reference, entity);
    }
}