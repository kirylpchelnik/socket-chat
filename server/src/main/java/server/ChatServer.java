package server;

import network.TCPConnectionListener;
import network.TCPConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ChatServer  implements TCPConnectionListener {

    private  final ArrayList<TCPConnection> connections = new ArrayList<>();
    public static void main(String[] args) {
        new ChatServer();
    }

    private ChatServer(){
        System.out.printf("Server running...");
        try(ServerSocket serverSocket = new ServerSocket(8080))
        {
            while (true){
                try {
                    new TCPConnection(this , serverSocket.accept());
                }catch (IOException e ){
                    System.out.println("TCPConnection"  + e);
                }
            }
        }catch (IOException e ){
            throw  new RuntimeException(e);
        }
    }

    private void sentToAllConnection(String value){
        System.out.println(value);
        for (TCPConnection o : connections) o.SendString(value);
    }

    @Override
    public synchronized void onConnectionReady(TCPConnection tcpConnection) {
        connections.add(tcpConnection);
        sentToAllConnection("Client connected" + tcpConnection.toString());
    }

    @Override
    public synchronized void onReceiveString(TCPConnection tcpConnection, String value) {
        sentToAllConnection(value);
    }

    @Override
    public synchronized void onDisconnect(TCPConnection tcpConnection) {
        connections.remove(tcpConnection);
        sentToAllConnection("Client  disconnect" + tcpConnection.toString());
    }

    @Override
    public synchronized void onException(TCPConnection tcpConnection, Exception e) {
        System.out.println("TCPConnection exception" + e);
    }


}
