package com.jayden.server;

import junit.framework.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class MockServer extends Server
{
    private int port;
    private int connectionsCount = 0;
    private ServerSocket serverSocket;

    public MockServer(int port)
    {
        super(port);
        this.port = port;
    }

    public void run()
    {
        try
        {
            serverSocket = new ServerSocket(port);
            ExecutorService executor = Executors.newFixedThreadPool(50);

            while(true)
            {
                Socket socket = serverSocket.accept();
                connectionsCount++;
                Worker worker = new Worker(socket, getRoutes());
                executor.execute(worker);
            }
        }
        catch (IOException e)
        {
        }
    }

    public int getConnectionsCount()
    {
        return connectionsCount;
    }

    public void shutDown()
    {
        try
        {
            serverSocket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}


public class ServerTest extends TestCase
{
    private MockServer server;
    private int port = 8000;

    public void setUp()
    {
        server = new MockServer(port);
    }

    public void tearDown()
    {
        server.shutDown();
    }

    public void testAddRoutes()
    {
        String route = "/";
        Response response = new FileDirectoryResponse();
        server.addRoute(route, response);

        assertTrue(server.getRoutes().containsKey(route));
    }

    public void testSingleConnection()
    {
        server.start();
        connect(port);

        assertEquals(1, server.getConnectionsCount());
    }

    public void testSimultaneousConnections() throws IOException
    {
        server.start();
        connect(port);
        connect(port);

        assertEquals(2, server.getConnectionsCount());
    }

    public void testManyConnections()
    {
        server.start();
        for(int i = 0; i < 50; i++)
            connect(port);

        assertEquals(50, server.getConnectionsCount());
    }

    private void connect(int port)
    {
        try
        {
            Socket socket = new Socket("localhost", port);
            socket.getOutputStream().write("this is a request".getBytes());

            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
            }

            socket.close();
        }
        catch (IOException e)
        {
            fail("could not connect");
        }
    }
}
