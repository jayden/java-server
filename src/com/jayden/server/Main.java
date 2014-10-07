package com.jayden.server;

public class Main
{
    public static void main(String args[])
    {
        Server server = new Server(8000);

        server.addRoute("/", new FileDirectoryResponse());
        server.addRoute("/echo", new TimeResponse());

        server.start();
    }
}
