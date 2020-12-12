package client;

import java.util.Arrays;
import java.util.List;

public class ClientApp {

	public static void main(String[] args) {

		List<Integer> ints = Arrays.asList(2,2,2,-6,-9);

		DataClient dataClient = new DataClient(ints, "positive");

		dataClient.transferData("localhost", 8888);
	}
}
