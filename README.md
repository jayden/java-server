Java-Server
===========

A web server written in Java.

Usage
-----

To run the server, use the terminal to point to the directory of where the JAR file is located.

	java -jar java-server.jar

The server runs locally on port 8000. As of now, there are only two routes:

	http://localhost:8000/ - prints the contents of the directory where the JAR file is running.

	http://localhost:8000/echo - prints "Hello world: HH:mm:ss" in four seconds.

All responses to other routes will be greeted with the message, "Error: This page doesn't exist!".