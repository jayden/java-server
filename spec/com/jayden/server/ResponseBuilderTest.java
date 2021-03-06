package com.jayden.server;

import junit.framework.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ResponseBuilderTest extends TestCase
{
    private ByteArrayOutputStream output = null;
    private ResponseBuilder responseBuilder;
    private final String CRLF = "\r\n";
    private final String OK_RESPONSE = "HTTP/1.1 200 OK\r\n";

    private static final HashMap<String, String> requestMap;
    static
    {
        requestMap = new HashMap<String, String>();
        requestMap.put("Method", "GET");
        requestMap.put("URI", "/");
        requestMap.put("Protocol", "HTTP/1.1");
    }

    private static final HashMap<String, Response> routeMap;
    static
    {
         routeMap = new HashMap<String, Response>();
        routeMap.put("/echo", new TimeResponse());
        routeMap.put("/", new FileDirectoryResponse());
    }

    public void setUp() throws IOException
    {
        responseBuilder = new ResponseBuilder(requestMap, routeMap);
    }

    public void testGetServerResponse() throws Exception
    {
        responseBuilder.setStatus(200);
        assertEquals(OK_RESPONSE, responseBuilder.statusLine());
    }

    public void testSetStatusCodes() throws Exception
    {
        String[][] expectations = new String[][]
                {
                        {"200", "OK"},
                        {"404", "Not Found"}
                };

        for(String[] expectation : expectations)
        {
            int status = Integer.parseInt(expectation[0]);
            String reason = expectation[1];
            responseBuilder.setStatus(status);
            String expectedStatus = "HTTP/1.1 " + status + " " + reason + CRLF;
            assertEquals(expectedStatus, responseBuilder.statusLine());
        }
    }

    public void testSetResponseHeader()
    {
        responseBuilder.setHeader("Server", "Jayden");
        assertTrue(responseBuilder.getHeader().contains("Server: Jayden"));
    }

    public void testSetResponseContent()
    {
        responseBuilder.setContent("watbro".getBytes());
        assertEquals("watbro", new String(responseBuilder.getContent()));
    }

    public void testFileDirectoryResponse() throws IOException
    {
        byte[] expectedContent = new FileDirectoryResponse().getResponse(requestMap);
        mockWriter().write(responseBuilder.getResponse());
        assertTrue(output.toString().contains(OK_RESPONSE));
        String header = OK_RESPONSE + responseBuilder.getHeader() + CRLF;
        assertEquals(output.toString(),  header + new String(expectedContent));
    }

    public void test404WriteResponse() throws IOException
    {
        String badResponse = "HTTP/1.1 404 Not Found\r\n";
        HashMap<String, String> badResponseMap = new HashMap<String, String>();
        badResponseMap.put("Method", "GET");
        badResponseMap.put("URI", "/wat");
        badResponseMap.put("Protocol", "HTTP/1.1");

        ResponseBuilder badResponseBuilder = new ResponseBuilder(badResponseMap, routeMap);
        mockWriter().write(badResponseBuilder.getResponse());
        assertTrue(output.toString().contains(badResponse));
    }

    public void testTimeResponse() throws IOException
    {
        HashMap<String, String> timeResponseMap = new HashMap<String, String>();
        timeResponseMap.put("Method", "GET");
        timeResponseMap.put("URI", "/echo");
        timeResponseMap.put("Protocol", "HTTP/1.1");

        ResponseBuilder timeResponseBuilder = new ResponseBuilder(timeResponseMap, routeMap);

        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        String expectedTime = dateFormat.format(date);

        mockWriter().write(timeResponseBuilder.getResponse());
        assertTrue(output.toString().contains(OK_RESPONSE));
        assertTrue(output.toString().contains(expectedTime));
    }

    public PrintStream mockWriter()
    {
        output = new ByteArrayOutputStream();
        return new PrintStream(output);
    }

}