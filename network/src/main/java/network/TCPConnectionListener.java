package network;

public interface TCPConnectionListener {
    void onConnectionReady(TCPConnection tcpConnection); // готовое соединенение TCPConnection для источника события
    void onReceiveString(TCPConnection tcpConnection, String value); // принять строку можем TCPConnection для события
    void onDisconnect(TCPConnection tcpConnection); // если Disconnect
    void onException(TCPConnection tcpConnection , Exception e ); // на Exeption

}
