package utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class PropUtils {

	/**
	 * Gets the value by key via the mykeys file
	 * 
	 * @param key
	 * @return
	 * @throws FileNotFoundException
	 */
	public String getValue(String key) throws FileNotFoundException {
		Scanner scanner = new Scanner(new FileReader("mykeys"));
		String value = null;
		String line;

		while (scanner.hasNext()) {
			line = scanner.nextLine();
			if (!line.startsWith("#") || !line.startsWith("/") || !line.isEmpty()) {
				String[] row = line.split("=");
				if (row[0].contains(key)) {
					value = row[1].toString();
				}
			}

		}
		scanner.close();
		return value;
	}
}
