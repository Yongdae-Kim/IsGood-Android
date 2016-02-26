package com.scratchback.isgood.application;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.orm.SugarApp;

import java.net.URISyntaxException;

/**
 * Created by useruser on 16. 2. 19..
 */
public class ChatApplication extends SugarApp {
    private Socket socket;
    {
        try {
            socket = IO.socket("http://10.10.1.147:8080/");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return socket;
    }
}
