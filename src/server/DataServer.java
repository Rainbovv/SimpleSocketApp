package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class DataServer {

	private ServerSocket serverSocket;
	private Socket clientSocket;

	public DataServer() {}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	public void startServer(int port) {

		System.out.println("Server >> Starting");

		try {
			serverSocket = new ServerSocket(port);

			System.out.println("Client >> Waiting");
			clientSocket = serverSocket.accept();

			sendData(getData());

			System.out.println("Server >> Ending");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getData() throws IOException {

		DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());

		String data = dataInputStream.readUTF();

		System.out.println("Server >> The next list has been received: " + data);

		return data;

	}

	private void sendData(String data) throws IOException {

		DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());

		String message = getAvg(data);

		System.out.println("Server >> Sending the message: " + message);
		dataOutputStream.writeUTF(message);
	}

	private String getAvg(String data) {

		int sum = 0;
		int count = 0;
		for (String num : data.split(",") ) {
			sum += Integer.parseInt(num);
			count ++;
		}

		return "The arithmetic average is " + sum / count;
	}
}
