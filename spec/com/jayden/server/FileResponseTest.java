package com.jayden.server;

import junit.framework.TestCase;
import java.util.*;

public class FileResponseTest extends TestCase
{
    public void testFileResponse()
    {
        HashMap<String, String> request = new HashMap<String, String>();
        String directory = "/public";
        request.put("URI", "/file1");

        FileResponse fileResponse = new FileResponse(directory);
        assertEquals(fileResponse.getResponse(request), "file1 contents");
    }
}