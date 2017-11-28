import java.io.*;
import java.util.stream.IntStream;

import com.neovisionaries.ws.client.*;


public class EchoClient {
    /**
     * The echo server on websocket.org.
     */
    //private static String SERVER_URL = "ws://demos.kaazing.com/echo";


//    private static String SERVER_URL = "ws://10.1.37.77/WebSocketsApi/ChatHandler.ashx";
    private static String SERVER_URL = "ws://10.1.37.77:4649/Chat";

    /**
     * The timeout value in milliseconds for socket connection.
     */
    private static final int CONNECTION_TIMEOUT = 5000;


    /**
     * The entry point of this command line application.
     */
    public static void main(String[] args) throws Exception {


        // Connect to the echo server.
        WebSocket ws = connect();


        // The standard input via BufferedReader.
        BufferedReader in = getInput();

        // A text read from the standard input.
        String text;

        // Read lines until "exit" is entered.
        while ((text = in.readLine()) != null) {
            // If the input string is "exit".
            if (text.equals("exit")) {
                // Finish this application.
                break;
            }

            // Send the text to the server.
            ws.sendText(text);
        }

        // Close the WebSocket.
        ws.disconnect();
    }


    /**
     * Connect to the server.
     */
    private static WebSocket connect() throws IOException, WebSocketException {
        WebSocketFactory webSocketFactory = new WebSocketFactory();

        WebSocket webSocket = webSocketFactory
                .setConnectionTimeout(CONNECTION_TIMEOUT)
                .createSocket(SERVER_URL)
                .addListener(new SocketListener())
                .addExtension(WebSocketExtension.PERMESSAGE_DEFLATE);

        return webSocket.connect();
    }


    /**
     * Wrap the standard input with BufferedReader.
     */
    private static BufferedReader getInput() throws IOException {
        return new BufferedReader(new InputStreamReader(System.in));
    }
}