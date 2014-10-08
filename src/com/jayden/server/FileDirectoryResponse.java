package com.jayden.server;

import java.io.File;
import java.util.HashMap;

public class FileDirectoryResponse implements Response
{
    public String getResponse(HashMap<String, String> request)
    {
        File directory = getFile("/public");
        String directoryString = "<html><body>";
        for(String fileName : directory.list())
        {
            directoryString += "<a href=\"" + request.get("URI") + fileName + "\">" + fileName + "</a><br />\r\n";

        }
        directoryString += "</body></html>";
        return directoryString;
    }

    public File getFile(String relativePath)
    {
        return new File(System.getProperty("user.dir") + relativePath);
    }
}
