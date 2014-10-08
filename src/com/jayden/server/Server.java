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
            System.out.println("Server started on port " + port);
            System.out.println("*Press 'Ctrl-C' to shutdown");

            ExecutorService executor = Executors.newCachedThreadPool();
            serverSocket = new ServerSocket(port, 5000);

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
