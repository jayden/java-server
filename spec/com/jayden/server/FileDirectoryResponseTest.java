package com.jayden.server;

import junit.framework.*;
import java.io.File;


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
       String expectedResponse = "";
       for(String file : directory.list())
       {
           expectedResponse += file + "\n";
       }

       assertEquals(fileDirectoryResponse.getResponse(), expectedResponse);
    }
}