package client;

import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataClient {

	private Socket socket;
	private List<Integer> data;
	private String option;

	public DataClient(List<Integer> data, String option) {
		this.data = data;
		this.option = option;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public Map<String, String> getDataWithOption() {

		Map<String, String> data = new HashMap<>();

		data.put("data", dataToString());
		data.put("option", getOption());

		return data;
	}

	public List<Integer> getData() {
		return data;
	}

	public void setData(List<Integer> data) {
		this.data = data;
	}

	public boolean transferData(String host, int port) {

		System.out.println("Client >> Starting");

		try {
			socket = new Socket(host, port);

			sendData();

			receiveData();

			System.out.println("Client >> Ending");

			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private void sendData() throws IOException {

		PrintWriter printWriter = new PrintWriter(new DataOutputStream(socket.getOutputStream()));

		JSONObject jsonObject = new JSONObject(getDataWithOption());

		System.out.println("Client >> Sending the list: " + dataToString());

		printWriter.println(jsonObject.toString());
		printWriter.flush();
	}

	private void receiveData() throws IOException {

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
				new DataInputStream(socket.getInputStream())));

		JSONObject receivedJson = new JSONObject(bufferedReader.readLine());

		System.out.println("Client >> Message from server: "
				+ receivedJson);
	}

	private String dataToString() {

		StringBuilder strData = new StringBuilder();

		this.data.forEach(i -> strData.append(i).append(","));

		return strData.substring(0, strData.length() - 1);
	}

	@Override
	public String toString() {
		return "DataClient{" + "data=" + data + '}';
	}
}
