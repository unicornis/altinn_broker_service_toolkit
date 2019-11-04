package no.unicornis.altinn.soap.client.callback;

import no.unicornis.altinn.BrokerServiceToolkit;
import no.unicornis.altinn.soap.client.cookie.CookieStore;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by taldev on 14/10/16.
 *
 * Originally written by tba @ brreg.
 */
public class CookiesOutInterceptor extends AbstractPhaseInterceptor {

    private final Logger log = Logger.getLogger(BrokerServiceToolkit.class);

    public CookiesOutInterceptor() {
        super(Phase.PRE_PROTOCOL);
    }

    public void handleMessage(Message message) throws Fault {
        @SuppressWarnings("unchecked") Map<String, List> headers = (Map<String, List>) message.get(Message.PROTOCOL_HEADERS);
        if (CookieStore.getCookie() != null) {
            log.debug("CookiesOUTInterceptor");
            log.debug("Cookie: " + CookieStore.getCookie());
            headers.put("Cookie", Collections.singletonList(CookieStore.getCookie()));

        } else {
            log.debug("CookiesOUTInterceptor");
            log.debug("CookiesOUTInterceptor CookieStore.getCookie() is null");
        }
    }
}
