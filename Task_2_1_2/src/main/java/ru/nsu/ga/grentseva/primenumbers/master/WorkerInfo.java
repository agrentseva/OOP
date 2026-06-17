package ru.nsu.ga.grentseva.primenumbers.master;

public class WorkerInfo {
    private final String host;
    private final int port;

    public WorkerInfo(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return host + ":" + port;
    }
}