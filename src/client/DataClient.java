package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class DataClient {

	private Socket socket;
	private List<Integer> data;

	public DataClient(List<Integer> data) {
		this.data = data;
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

		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	private void sendData() throws IOException {

		DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
		String list = dataToString();

		System.out.println("Client >> Sending the list: " + list);
		dataOutputStream.writeUTF(list);
		dataOutputStream.flush();
	}

	private void receiveData() throws IOException {

		DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

		System.out.println("Client >> Message from server: "
				+ dataInputStream.readUTF());
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
