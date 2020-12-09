package client;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ClientApp {

	public static void main(String[] args) {

		List<Integer> ints = Arrays.asList(2,2,2);

		DataClient dataClient = new DataClient(ints);

		dataClient.transferData("localhost", 8888);
	}
}
