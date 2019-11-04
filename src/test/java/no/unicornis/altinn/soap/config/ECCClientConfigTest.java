package no.unicornis.altinn.soap.config;

import no.altinn.brokerserviceexternalec.BrokerServiceExternalECSF;
import no.altinn.brokerserviceexternalecstreamed.BrokerServiceExternalECStreamedSF;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

@RunWith(MockitoJUnitRunner.class)
public class ECCClientConfigTest {

    private ECClientConfig ecClientConfig;

    @Before
    public void setUp() {
        ecClientConfig = new ECClientConfig();
    }

    @Test
    public void testGetBrokerServiceExternalECStreamedSFFactory(){
        BrokerServiceExternalECStreamedSF brokerServiceExternalECStreamedSF = ecClientConfig.getBrokerServiceExternalECStreamedSFFactory();
        assertThat(brokerServiceExternalECStreamedSF.getCustomBindingIBrokerServiceExternalECStreamed(), is(not(nullValue())));
    }

    @Test
    public void testGetBrokerServiceExternalECSFFactory(){
        BrokerServiceExternalECSF brokerServiceExternalECSF = ecClientConfig.getBrokerServiceExternalECSFFactory();
        assertThat(brokerServiceExternalECSF.getCustomBindingIBrokerServiceExternalEC(), is(not(nullValue())));
    }
}
