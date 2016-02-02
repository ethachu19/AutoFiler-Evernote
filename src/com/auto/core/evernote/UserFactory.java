/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auto.core.evernote;

import static com.auto.core.GlobalUtil.printErr;
import com.evernote.auth.EvernoteAuth;
import com.evernote.auth.EvernoteService;
import com.evernote.clients.ClientFactory;
import com.evernote.edam.error.EDAMSystemException;
import com.evernote.edam.error.EDAMUserException;
import com.evernote.thrift.TException;
import com.evernote.thrift.transport.TTransportException;

/**
 *
 * @author ethachu19
 */
public class UserFactory {

    private static final String developToken = "S=s1:U=91f41:E=159f29d1c56:C=1529aebed38:P=1cd:A=en-devtoken:V=2:H=834028c7829244efdb834209503dece6";
    private static EvernoteAuth auth;
    private static ClientFactory factory;
    private static String token;

    static {
        token = System.getenv("AUTH_TOKEN");
        if (token == null) {
            token = developToken;
            auth = new EvernoteAuth(EvernoteService.SANDBOX, token);
            factory = new ClientFactory(auth);
        } else {
            printErr("System environment variable AUTH_TOKEN is not null");
        }
    }

    public static EvernoteUser createUser() throws TTransportException, EDAMUserException, EDAMSystemException, TException {
        EvernoteUser res = new EvernoteUser(factory.createUserStoreClient(), factory.createNoteStoreClient(), null);
        return res;
    }
}
