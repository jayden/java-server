package com.jayden.server;

import junit.framework.TestCase;
import java.util.*;

public class FileResponseTest extends TestCase
{
    private FileResponse fileResponse;
    private static HashMap<String, String> request;
    static
    {
        request = new HashMap<String, String>();
        request.put("URI", "/file1");
    }

    public void setUp()
    {
        fileResponse = new FileResponse("/public");
    }

    public void testFileResponse()
    {
        request.put("Method", "GET");
        assertEquals(new String(fileResponse.getResponse(request)), "file1 contents");
        assertEquals(fileResponse.getStatus(), 200);
    }

    public void test405Response()
    {
        request.put("Method", "POST");
        assertEquals(new String(fileResponse.getResponse(request)), "Method Not Allowed!");
        assertEquals(fileResponse.getStatus(), 405);

        request.put("Method", "PUT");
        assertEquals(new String(fileResponse.getResponse(request)), "Method Not Allowed!");
        assertEquals(fileResponse.getStatus(), 405);
    }

    public void test206Response()
    {
        request.put("Method", "GET");
        request.put("URI", "/partial_content.txt");
        request.put("Range:", "bytes=0-4");

        assertEquals(new String(fileResponse.getResponse(request)), "This ");
        assertEquals(fileResponse.getStatus(), 206);
    }
}