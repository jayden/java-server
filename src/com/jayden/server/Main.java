package com.jayden.server;

public class Main
{
    private static final String PORT_FLAG = "-p";
    private static final String DIRECTORY_FLAG = "-d";
    private static int port = 5000;
    private static String directory = "/public";

    public static void main(String args[])
    {
        parseArguments(args);
        Server server = new Server(port);

        server.addRoute("/", new FileDirectoryResponse());
        server.addRoute("/echo", new TimeResponse());

        server.start();
    }

    private static void parseArguments(String[] args)
    {
        for(int i=0; i < args.length; i++ )
        {
            if (args[i].equals(PORT_FLAG))
            {
                port = Integer.parseInt(args[i+1]);
            }
            else if (args[i].equals(DIRECTORY_FLAG))
            {
                directory = args[i+1];
            }
        }
    }
}
