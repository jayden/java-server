package com.jayden.server;

import junit.framework.*;
import java.io.File;
import java.util.HashMap;

public class FileDirectoryResponseTest extends TestCase
{
    private FileDirectoryResponse fileDirectoryResponse;

    public void setUp()
    {
        fileDirectoryResponse = new FileDirectoryResponse();
    }

    public void testGetResponse()
    {
        File directory = new File(System.getProperty("user.dir") + "/public");
        HashMap<String, String> response = new HashMap<String, String>();
        response.put("URI", "/");
        String expectedResponse = "<html><body>";
        for(String fileName : directory.list())
        {
            expectedResponse += "<a href=\"" + "/" + fileName + "\">" + fileName + "</a><br />\r\n";

        }
        expectedResponse += "</body></html>";

       assertEquals(new String(fileDirectoryResponse.getResponse(response)), expectedResponse);
    }
}