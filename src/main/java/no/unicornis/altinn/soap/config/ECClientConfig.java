package no.unicornis.altinn.soap.config;

import no.altinn.brokerserviceexternalec.BrokerServiceExternalECSF;
import no.altinn.brokerserviceexternalec.IBrokerServiceExternalEC;
import no.altinn.brokerserviceexternalecstreamed.BrokerServiceExternalECStreamedSF;
import no.altinn.brokerserviceexternalecstreamed.IBrokerServiceExternalECStreamed;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.MTOMFeature;
import javax.xml.ws.soap.SOAPBinding;

/**
 * Created by taldev on 14/10/16.
 *
 * Originally written by tba @ brreg.
 *
 * This class defines the configuration for the Altinn SOAP client.
 */
@Configuration
@ImportResource("classpath:cxf.xml")
@ComponentScan(basePackages = "no.unicornis.altinn.soap.client")
@PropertySource(value = "classpath:config/application.properties")
public class ECClientConfig {

    @SuppressWarnings({"CanBeFinal", "unused"})
    @Value("${broker.service.external.ec}")
    private String BROKER_SERVICE_EXTERNAL_EC;


    @SuppressWarnings({"CanBeFinal", "unused"})
    @Value("${broker.service.external.ec.streamed}")
    private String BROKER_SERVICE_EXTERNAL_EC_STREAMED;

    @Bean
    public BrokerServiceExternalECStreamedSF getBrokerServiceExternalECStreamedSFFactory() {
        return new BrokerServiceExternalECStreamedSF();
    }

    @Bean
    public BrokerServiceExternalECSF getBrokerServiceExternalECSFFactory() {
        return new BrokerServiceExternalECSF();
    }

    @SuppressWarnings("unused")
    @Bean
    public IBrokerServiceExternalEC getBrokerServiceExternalECClient() {
        final IBrokerServiceExternalEC client = getBrokerServiceExternalECSFFactory().getCustomBindingIBrokerServiceExternalEC(new MTOMFeature());
        BindingProvider bp = (BindingProvider) client;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, BROKER_SERVICE_EXTERNAL_EC);
        SOAPBinding binding = ((SOAPBinding) bp.getBinding());
        binding.setMTOMEnabled(false);
        return client;
    }

    @SuppressWarnings("unused")
    @Bean
    public IBrokerServiceExternalECStreamed getBrokerClient() {
        final IBrokerServiceExternalECStreamed client = getBrokerServiceExternalECStreamedSFFactory().getCustomBindingIBrokerServiceExternalECStreamed(new MTOMFeature());
        BindingProvider bp = (BindingProvider) client;

        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, BROKER_SERVICE_EXTERNAL_EC_STREAMED);
        SOAPBinding binding = ((SOAPBinding) bp.getBinding());
        binding.setMTOMEnabled(true);


        return client;
    }
}
