package com.jayden.server;

import junit.framework.*;

import java.io.File;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class FileDirectoryResponseTest extends TestCase
{
    private FileDirectoryResponse fileDirectoryResponse;

    public void setUp()
    {
        fileDirectoryResponse = new FileDirectoryResponse();
    }


    public void testInstanceOfResponse()
    {
        assertThat(fileDirectoryResponse, instanceOf(Response.class));
    }

    public void testGetResponse()
    {
       File directory = new File(System.getProperty("user.dir") + "/");
       String expectedResponse = "";
       for(String file : directory.list())
       {
           expectedResponse += file + "\n";
       }

       assertEquals(fileDirectoryResponse.getResponse(), expectedResponse);
    }
}