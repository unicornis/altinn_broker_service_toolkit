package no.unicornis.altinn.soap.client.callback;

import org.springframework.beans.factory.annotation.Value;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by taldev on 14/10/16.
 *
 * Originally written by tba @ brreg.
 */
public class ClientKeystorePasswordCallback implements CallbackHandler {

    @SuppressWarnings({"WeakerAccess", "unused"})
    @Value("${formidling.altinn.security.password}")
    protected String password;

    @SuppressWarnings({"FieldCanBeLocal", "CanBeFinal", "MismatchedQueryAndUpdateOfCollection"})
    private Map<String, String> passwords = new HashMap<>();

    public ClientKeystorePasswordCallback() {
        passwords.put("BRREG", password);
    }

    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        /*for (int i = 0; i < callbacks.length; i++) {
            WSPasswordCallback pc = (WSPasswordCallback)callbacks[i];

            String pass = passwords.get(pc.getIdentifier());
            if (pass != null) {
                pc.setPassword(pass);
                return;
            }
        }*/
    }
}