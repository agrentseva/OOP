package ru.nsu.ga.grentseva.primenumbers.worker;

import ru.nsu.ga.grentseva.primenumbers.common.DistributedLogger;

public class WorkerMain {

    private WorkerMain() {}

    public static void main(String[] args) {
        if (args.length != 1) {
            DistributedLogger.error("Usage: WorkerMain <port>");
            return;
        }

        try {
            int port = Integer.parseInt(args[0]);
            if (port < 1 || port > 65535) {
                DistributedLogger.error("Port must be in range 1-65535");
                return;
            }

            WorkerNode worker = new WorkerNode(port);
            worker.start();
        } catch (NumberFormatException e) {
            DistributedLogger.error("Invalid port format");
        }
    }
}