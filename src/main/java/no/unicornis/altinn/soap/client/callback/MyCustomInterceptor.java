package no.unicornis.altinn.soap.client.callback;

import org.apache.cxf.binding.soap.saaj.SAAJOutInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by taldev on 14/10/16.
 *
 * Originally writter by tba @ brreg.
 */
public class MyCustomInterceptor extends AbstractPhaseInterceptor {

    public MyCustomInterceptor() {
        super(Phase.PRE_PROTOCOL_ENDING);
        //noinspection unchecked
        getAfter().add(SAAJOutInterceptor.SAAJOutEndingInterceptor.class.getName());
    }

    public void handleMessage(Message message) throws Fault {
        @SuppressWarnings("unchecked") Map<String, List> headers = (Map<String, List>) message.get(Message.PROTOCOL_HEADERS);
        headers.put("Connection", Collections.singletonList("Keep-Alive"));

    }

}
