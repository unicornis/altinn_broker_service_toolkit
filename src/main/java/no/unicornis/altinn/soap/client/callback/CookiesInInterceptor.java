package no.unicornis.altinn.soap.client.callback;

import no.unicornis.altinn.BrokerServiceToolkit;
import no.unicornis.altinn.soap.client.cookie.CookieStore;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.Cookie;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * Created by taldev on 14/10/16.
 *
 * Originally written by tba @ brreg.
 */
public class CookiesInInterceptor extends AbstractPhaseInterceptor {

    @SuppressWarnings("CanBeFinal")
    private Logger log = Logger.getLogger(BrokerServiceToolkit.class);

    public CookiesInInterceptor() {
        super(Phase.PRE_PROTOCOL);
    }

    public void handleMessage(Message message) throws Fault {
        @SuppressWarnings("unchecked")
        Map<String, List> headers = (Map<String, List>) message.get(Message.PROTOCOL_HEADERS);
        @SuppressWarnings("unchecked")
        List<Cookie> cookies= headers.get("Set-Cookie");
        if(cookies != null)      {
            log.debug("CookiesInInterceptor");
            log.debug("Cookie: " + cookies.get(0));
            CookieStore.setCookie(cookies.get(0));
        }
    }
}