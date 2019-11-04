package no.unicornis.altinn.soap.models;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class UploadFileTest {

    @Test
    public void testGetSet() {
        UploadFile uploadFile = new UploadFile();
        uploadFile.setExternalServiceCode("666");
        uploadFile.setExternalServiceEditionCode(666);
        uploadFile.setZipFileName("Testfil.zip");
        uploadFile.setSendersReference("BaseRef");
        uploadFile.addReceiver("123456789");
        assertEquals(uploadFile.getExternalServiceCode(), "666");
        assertEquals(uploadFile.getExternalServiceEditionCode(), 666);
        assertEquals(uploadFile.getZipFileName(), "Testfil.zip");
        assertEquals(uploadFile.getSendersReference(), "BaseRef");
        ArrayList<String> array = new ArrayList<>();
        array.add("123456789");
        assertEquals(uploadFile.getReceiverList().get(0), array.get(0));
    }

    @Test
    public void testToString() {
        UploadFile uploadFile = new UploadFile();
        uploadFile.setExternalServiceCode("666");
        uploadFile.setExternalServiceEditionCode(666);
        uploadFile.setZipFileName("Testfil.zip");
        uploadFile.setSendersReference("BaseRef");
        uploadFile.addReceiver("123456789");
        assertThat(uploadFile.toString(), containsString("sendersReference"));
    }
}
