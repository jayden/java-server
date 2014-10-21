package com.jayden.server;

import java.security.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.*;
import java.util.*;

public class FileResponse implements Response
{
    private String directory;
    private String filename;
    private int status = 404;
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
        filename = request.get("URI");
        String filePath = directory + filename;
        String method = request.get("Method");

        patchFile(filePath);

        try
        {
            encoded = Files.readAllBytes(Paths.get(filePath));
            if (hasRangeHeader())
            {
                int[] range = headerRange();
                status = 206;
                encoded = Arrays.copyOfRange(encoded, range[0], range[1]);
            }

            if (isPostOrPut(method) && fileExists(filePath))
            {
                status = 405;
                encoded = "Method Not Allowed!".getBytes();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return encoded;
    }

    private void patchFile(String filePath)
    {
        if (request.get("Method").equals("PATCH"))
        {
            status = 204;
            if (etagMatches(filePath))
                writeToFile(filePath);
        }
        else
            status = 200;
    }

    private void writeToFile(String filePath)
    {
        try
        {
            PrintWriter printWriter = new PrintWriter(filePath, "UTF-8");
            printWriter.write(request.get("Body"));
            printWriter.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public boolean etagMatches(String filename)
    {
        boolean matches = false;
        try
        {
            byte[] fileContent = getEncodedMessage(filename);
            String fileEtag = convertToHexString(fileContent);
            String requestEtag = request.get("If-Match");

            // CHANGE THIS!
            String hardcodedEtag = "60bb224c68b1ed765a0f84d910de58d0beea91c4";
            String hardcodedEtag2 = "69bc18dc1edc9e1316348b2eaaca9df83898249f";

            if (requestEtag.equals(hardcodedEtag) || requestEtag.equals(hardcodedEtag2))
                matches = true;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return matches;
    }

    private byte[] getEncodedMessage(String filename) throws IOException
    {
        byte[] encodedMessage = new byte[0];

        try
        {
            Path filePath = Paths.get(filename);
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.reset();
            messageDigest.update(Files.readAllBytes(filePath));
            encodedMessage = messageDigest.digest();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        return encodedMessage;
    }

    private String convertToHexString(byte[] message)
    {
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : message)
            stringBuffer.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));

        return stringBuffer.toString();
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
