package com.jayden.server;

import junit.framework.*;

import java.io.*;

public class ResponseBuilderTest extends TestCase
{
    private ByteArrayOutputStream output = null;
    private ResponseBuilder responseBuilder;
    private final String CRLF = "\r\n";
    private final String FAKE_RESPONSE = "HTTP/1.1 200 OK\r\n";
    private final String DEFAULT_HEADER = "Content-Length: 0\r\nContent-Type: text/html;charset=utf-8\r\nServer: Jayden";

    private PrintStream mockWriter()
    {
        output = new ByteArrayOutputStream();
        return new PrintStream(output);
    }

    public void setUp() throws IOException
    {
        responseBuilder = new ResponseBuilder(mockWriter());
    }

    public void testGetServerResponse() throws Exception
    {
        responseBuilder.setStatus(200);
        assertEquals(FAKE_RESPONSE, responseBuilder.statusLine());
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
        assertEquals("Server: Jayden" + CRLF, responseBuilder.getHeader());
    }

    public void testSetResponseContent()
    {
        responseBuilder.setContent("watbro");
        assertEquals("watbro", responseBuilder.getContent());
    }


    public void testWriteDefaultHeader()
    {
        responseBuilder.setDefaultHeader();
        responseBuilder.writeResponse();
        assertTrue(output.toString().contains(DEFAULT_HEADER));
    }

    public void testWriteResponse()
    {
        responseBuilder.setContent("watbro");
        responseBuilder.setStatus(200);
        String expectedResponse = FAKE_RESPONSE + DEFAULT_HEADER + CRLF + CRLF + "watbro";
        responseBuilder.writeResponse();
        assertEquals(expectedResponse, output.toString());
    }

}