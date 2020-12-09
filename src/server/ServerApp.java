package server;

import java.io.IOException;


public class ServerApp {

	public static void main(String[] args) {

		DataServer dataServer = new DataServer();

		dataServer.startServer(8888);
	}
}
