package server;

import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataServer {

	private ServerSocket serverSocket;
	private Socket clientSocket;

	public DataServer() {
	}

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

			System.out.println("Server >> Socket Accepted");

			sendData(getData());

			System.out.println("Server >> Ending");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private JSONObject getData() throws IOException {

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
				new DataInputStream(clientSocket.getInputStream())));

		return new JSONObject(bufferedReader.readLine());
	}

	private void sendData(JSONObject json) throws IOException {

		PrintWriter printWriter = new PrintWriter(new DataOutputStream(clientSocket.getOutputStream()));

		JSONObject jsonMessage = getAvg(json.get("data").toString(), json.get("option").toString());

		printWriter.println(jsonMessage.toString());
		printWriter.flush();
	}

	private JSONObject getAvg(String data, String option) {

		int sum = 0;
		int count = 0;

		for (int i: getSortedData(data, option)) {

			sum += i;
			count++;
		}

		Map<String, Integer> map = new HashMap<>();
		map.put("avg", sum / count);

		return new JSONObject(map);
	}

	private List<Integer> getSortedData(String data, String option) {

		List<Integer> sortedData = new ArrayList<>();

		for (String s : data.split(",")) {
			int i = Integer.parseInt(s);

			if (option.equals("positive") && i >= 0)
				sortedData.add(i);

			if (option.equals("negative") && i < 0)
				sortedData.add(i);
		}

		return sortedData;
	}
}