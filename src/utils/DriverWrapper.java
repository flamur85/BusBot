package utils;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverWrapper implements WebDriver {
	private WebDriver driver;
	public static final int RETRY_TIMEOUT = 500;

	/**
	 * Creates driver based on OS its running on.
	 */
	public DriverWrapper() {

		if (System.getProperty("os.name").contains("Mac")) {
			System.setProperty("webdriver.chrome.driver", "chromedriver");
		} else {
			System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		}
		// ChromeOptions options = new ChromeOptions();
		// options.addArguments("--headless", "--disable-gpu",
		// "--window-size=1920,1200","--ignore-certificate-errors");
		// WebDriver driver = new ChromeDriver(options);
		driver = new ChromeDriver();
	}

	/**
	 * Sends one key at a time randomly to try mimic a human typing.
	 * 
	 * @param element
	 * @param stringToSend
	 * @throws Exception
	 */
	public void sendKeyOneAtATime(By element, String stringToSend) throws Exception {
		String value = stringToSend;
		GeneralUtils util = new GeneralUtils();

		WebElement textBox = driver.findElement(element);
		textBox.clear();

		for (int i = 0; i < value.length(); i++) {
			char character = value.charAt(i);
			String keyToSend = new StringBuilder().append(character).toString();
			textBox.sendKeys(keyToSend);
			Thread.sleep(util.getRandomTime(100, 900));
		}

	}

	/**
	 * Will wait endlessly for element until found.
	 * 
	 * @param element
	 * @return
	 * @throws Exception
	 */
	public boolean waitForElementThenClick(By element) throws Exception {
		boolean found = false;

		while (found == false) {
			try {
				driver.findElement(element).click();
				break;
			} catch (NoSuchElementException e) {
				Thread.sleep(RETRY_TIMEOUT);
			}
		}
		return found;
	}

	/**
	 * Tries to find an element a specified amount of times and if found it will
	 * click it.
	 * 
	 * @param element
	 * @param millis
	 * @param retryCount
	 * @return
	 * @throws Exception
	 */
	public boolean tryDoesElementExist(By element, long millis, int retryCount) throws Exception {
		boolean found = false;
		int counter = 0;

		while (found == false) {
			counter++;
			if (counter <= retryCount) {
				try {
					if (driver.findElement(element) != null) {
						found = true;
					}
				} catch (Exception e) {
					System.out.println("Recaptcha Challenge Not Found.");
				}
			} else {
				found = false;
				break;
			}
		}
		return found;
	}

	public void get(String url) {
		driver.get(url);
	}

	public WebElement findElement(By element) {
		return driver.findElement(element);
	}

	public void quit() {
		driver.quit();
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<WebElement> findElements(By arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCurrentUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPageSource() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getWindowHandle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> getWindowHandles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Options manage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Navigation navigate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TargetLocator switchTo() {
		// TODO Auto-generated method stub
		return null;
	}

}
