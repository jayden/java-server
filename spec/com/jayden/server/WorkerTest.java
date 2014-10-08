package com.jayden.server;

import junit.framework.*;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class WorkerTest extends TestCase
{
    private Worker mockWorker = null;
    private static final String TEST_REQUEST = "GET /wat HTTP/1.1";
    private static final String TEST_RESPONSE =
            "HTTP/1.1 200 OK\r\n" +
            "Content-Length: 7\r\n" +
            "Content-Type: text/html;charset=utf-8\r\n" +
            "Server: Jayden\r\n\r\n" +
            "wat bro";

    private static final HashMap<String, Response> routeMap;
    static
    {
        routeMap = new HashMap<String, Response>();
        routeMap.put("/wat", new Response() {
            @Override
            public String getResponse(HashMap<String, String> request) {
                return "wat bro";
            }
        });
    }

    public void setUp() throws IOException
    {
        mockWorker = new MockWorker(new Socket(), routeMap);
    }

    public void testOutputStream() throws IOException
    {
        mockWorker.run();
        assertEquals(TEST_RESPONSE, mockWorker.getOutputStream().toString());
    }

    class MockWorker extends Worker
    {
        public ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        public MockWorker(Socket clientSocket, HashMap<String, Response> routes)
        {
            super(clientSocket, routes);
        }

        public InputStream getInputStream()
        {
            return new ByteArrayInputStream(TEST_REQUEST.getBytes());
        }

        public OutputStream getOutputStream()
        {
            return outputStream;
        }
    }
}