package com.jayden.server;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class FileResponse implements Response
{
    private String directory;

    public FileResponse(String directory)
    {
        this.directory = directory;
    }

    public String getResponse(HashMap<String, String> request)
    {
        byte[] encoded = null;

        try
        {
            String filePath = System.getProperty("user.dir") + directory + request.get("URI");
            encoded = Files.readAllBytes(Paths.get(filePath));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return new String(encoded);
    }
}
