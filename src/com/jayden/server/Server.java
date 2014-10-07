package com.jayden.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable
{
    private int port;
    private HashMap<String, Response> routes = null;
    private ServerSocket serverSocket;

    public Server(int port)
    {
        this.port = port;
        routes = new HashMap<String, Response>();
    }

    public void addRoute(String route, Response response)
    {
        routes.put(route, response);
    }

    public HashMap<String, Response> getRoutes()
    {
        return routes;
    }

    public void run()
    {
        try
        {
            System.out.println("Server started on port 8000");
            System.out.println("*Press 'Ctrl-C' to shutdown");

            ExecutorService executor = Executors.newFixedThreadPool(100);
            serverSocket = new ServerSocket(port);

            while(true)
            {
                Socket socket = serverSocket.accept();
                Worker worker = new Worker(socket, routes);
                executor.execute(worker);
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void start()
    {
        new Thread(this).start();
    }
}
