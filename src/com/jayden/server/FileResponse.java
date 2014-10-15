package com.jayden.server;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class FileResponse implements Response
{
    private String directory;
    private String filename;
    private int status = 200;
    private HashMap<String, String> request;

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
        this.request = request;
        byte[] encoded = null;
        int[] range = null;
        filename = request.get("URI");
        String filePath = System.getProperty("user.dir") + directory + filename;
        String method = request.get("Method");
        if (isPostOrPut(method) && fileExists(filePath))
        {
            status = 405;
            return "Method Not Allowed!".getBytes();
        }

        try
        {
            encoded = Files.readAllBytes(Paths.get(filePath));
            if (hasRangeHeader())
            {
                range = headerRange();
                status = 206;
                return Arrays.copyOfRange(encoded, range[0], range[1]);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return encoded;
    }

    private boolean hasRangeHeader()
    {
        return request.containsKey("Range");
    }

    private int[] headerRange()
    {
        String range = request.get("Range").split("=")[1];
        int startPosition = Integer.parseInt(range.split("-")[0]);
        int endPosition = Integer.parseInt(range.split("-")[1]) + 1;
        return new int[]{startPosition, endPosition};
    }

    private boolean isPostOrPut(String method)
    {
        boolean isPost = method.equals("POST");
        boolean isPut = method.equals("PUT");

        return isPost || isPut;
    }

    public int getStatus()
    {
        return status;
    }

    private boolean fileExists(String filename)
    {
        boolean exists = false;
        File file = new File(filename);

        if (file.exists())
            exists = true;

        return exists;
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
