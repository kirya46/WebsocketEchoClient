import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFrame;

import java.util.*;

/**
 * Created by Kirill Stoianov on 28.11.17.
 */
public class SocketListener extends WebSocketAdapter {

    private static final int PING_INTERVAL = 1000;

    private Timer timer = new Timer();

    @Override
    public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
        super.onConnected(websocket, headers);
        System.err.println("Connected " + new Date(System.currentTimeMillis()).toString());

        //send message
        websocket.sendText("Hello from client");

        //init ping task
        PingTimerTask pinger = new PingTimerTask() {
            @Override
            void invoke() {
                System.out.println("ping");
                websocket.sendPing(UUID.randomUUID().toString());
            }
        };


        timer.schedule(pinger,0,PING_INTERVAL);
    }


    @Override
    public void onConnectError(WebSocket websocket, WebSocketException exception) throws Exception {
        super.onConnectError(websocket, exception);
        System.out.println("Connection error: " + exception.getMessage());
    }

    @Override
    public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
        super.onDisconnected(websocket, serverCloseFrame, clientCloseFrame, closedByServer);
        System.err.println("Disconnect " + new Date(System.currentTimeMillis()).toString());

        //stop sending ping
        timer.cancel();
    }

    @Override
    public void onError(WebSocket websocket, WebSocketException cause) throws Exception {
        super.onError(websocket, cause);
        System.out.println("WebSocket error " + cause.getMessage());
    }

    @Override
    public void onTextMessage(WebSocket websocket, String message) {
        System.out.println(message);
    }


    @Override
    public void onPingFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
        super.onPingFrame(websocket, frame);
        System.out.println("onPingFrame: " + frame.getPayloadText());
    }

    @Override
    public void onPongFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
        super.onPongFrame(websocket, frame);
        System.out.println("pong: " + frame.getPayloadText());

    }
}
