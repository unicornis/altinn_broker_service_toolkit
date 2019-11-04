package no.unicornis.altinn.soap.models;

import no.altinn.brokerserviceexternalec.BrokerServiceAvailableFileStatus;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class GetAvailableFilesTest {

    @Test
    public void testSetGet() {
        GetAvailableFiles getAvailableFiles = new GetAvailableFiles();
        getAvailableFiles.setExternalServiceCode("666");
        getAvailableFiles.setExternalServiceEditionCode(666);
        getAvailableFiles.setBrokerServiceAvailableFileStatus(BrokerServiceAvailableFileStatus.DOWNLOADED);
        assertEquals(getAvailableFiles.getExternalServiceCode(), "666");
        assertEquals(getAvailableFiles.getExternalServiceEditionCode(), 666);
        assertEquals(getAvailableFiles.getBrokerServiceAvailableFileStatus(), BrokerServiceAvailableFileStatus.DOWNLOADED);
    }

    @Test
    public void testToString() {
        GetAvailableFiles getAvailableFiles = new GetAvailableFiles();
        getAvailableFiles.setExternalServiceCode("666");
        getAvailableFiles.setExternalServiceEditionCode(666);
        getAvailableFiles.setBrokerServiceAvailableFileStatus(BrokerServiceAvailableFileStatus.DOWNLOADED);
        assertThat(getAvailableFiles.toString(), containsString("brokerServiceAvailableFileStatus"));
    }

}