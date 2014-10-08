package com.jayden.server;

import java.io.File;

public class FileDirectoryResponse implements Response
{
    public String getResponse()
    {
        File directory = getFile("/public");
        String directoryString = "";
        for(String fileName : directory.list())
        {
            directoryString += fileName + "\n";
        }

        return directoryString;
    }

    public File getFile(String relativePath)
    {
        return new File(System.getProperty("user.dir") + relativePath);
    }
}
