package com.jayden.server;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class FileResponse implements Response
{
    private String directory;
    private String filename;

    private static final ArrayList<String> imageFileExtensions = new ArrayList<String>();
    static
    {
        imageFileExtensions.add("jpeg");
        imageFileExtensions.add("gif");
        imageFileExtensions.add("png");
    }

    public FileResponse(String directory)
    {
        this.directory = directory;
    }

    public byte[] getResponse(HashMap<String, String> request)
    {
        byte[] encoded = null;
        filename = request.get("URI");
        try
        {
            String filePath = System.getProperty("user.dir") + directory + filename;
            encoded = Files.readAllBytes(Paths.get(filePath));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return encoded;
    }

    public String getContentType()
    {
        if (imageFileExtensions.contains(getFileExtension(filename)))
            return "image/" + getFileExtension(filename);
        return "text/plain";
    }

    public String getFileExtension(String file)
    {
        String extension = "";

        int i = file.lastIndexOf('.');
        if (i > 0) {
            extension = file.substring(i+1);
        }

        return extension;
    }
}
