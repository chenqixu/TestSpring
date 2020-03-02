package com.cqx.testspring.webservice.common.util.session;

import org.apache.ws.security.WSPasswordCallback;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * KeystorePasswordHandler
 *
 * @author chenqixu
 */
public class KeystorePasswordHandler implements CallbackHandler {
    private Map<String, String> passwords = new HashMap();

    public KeystorePasswordHandler(String clientId, String password) {
        this.passwords.put(clientId, password);
    }

    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (int i = 0; i < callbacks.length; ++i) {
            WSPasswordCallback pc = (WSPasswordCallback) callbacks[i];
            String pass = (String) this.passwords.get(pc.getIdentifer());
            if (pass != null) {
                pc.setPassword(pass);
                return;
            }
        }

    }

    public void setAliasPassword(String alias, String password) {
        this.passwords.put(alias, password);
    }
}
