package sites;

import java.io.FileNotFoundException;

import org.openqa.selenium.*;
import utils.DriverWrapper;
import utils.MessageUtils;
import utils.MessageUtils.MessageData;

public class Zoolert {
	// List of Wait Time variables
	private static final long WAIT_FOR_RELOAD = 15000;

	// List selectors here
	private static final By ZOOLERT_IN_STOCK_SELECTOR = By
			.cssSelector("table#trackers tbody tr td[style = 'background-color:#3ADF00; text-align:center;']");
	private static final By ALARM_BUTTON = By.cssSelector("input#groovybtn1on");
	private static final By ROOSTER_BUTTON = By.cssSelector("input[value='rooster']");

	// List URL's here
	private static final String ZOOLERT_PS5_URL = "https://www.zoolert.com/videogames/consoles/playstation5/";
	private static final String ZOOLER_XBOX_URL = "https://www.zoolert.com/videogames/consoles/xbox-series-x/";
	private static final String ZOOLERT_RTX_3070 = "https://www.zoolert.com/computers/videocards/nvidia/rtx-3070/";
	private static final String ZOOLERT_TEST_URL = "https://www.zoolert.com/videogames/mario-kart-live-home-circuit/";

	protected DriverWrapper driver;

	private Zoolert(DriverWrapper driver) {
		this.driver = driver;
	}

	public static void main(String[] args) throws InterruptedException, FileNotFoundException {
		String urlToBeScanned = ZOOLERT_PS5_URL;
		String messageSubject = ("In Stock Alert!!!");
		String messageBody = (urlToBeScanned);

		DriverWrapper driver = new DriverWrapper();
		driver.get(urlToBeScanned);
		driver.findElement(ALARM_BUTTON).click(); // test alarm button on the site
		driver.findElement(ROOSTER_BUTTON).click(); //Rooster sound should play, else check your audio settings
		Thread.sleep(2000);

		scanForAnyInStock(driver, urlToBeScanned, ZOOLERT_IN_STOCK_SELECTOR);
		driver.quit();

		MessageUtils messageUtils = new MessageUtils();
		MessageData.init();
		messageUtils.sendMessage(messageSubject, messageBody, MessageData.getMyCell(), MessageData.getMyEmail());
		messageUtils.sendMessage(messageSubject, messageBody, MessageData.getKeithCell(), MessageData.getJasonCell());
	}

	private static void scanForAnyInStock(DriverWrapper driver, String urlToBeScanned, By uniqueInStockSelector)
			throws InterruptedException {
		boolean isItInStockYet = false;

		while (isItInStockYet == false) {
			driver.get(urlToBeScanned);

			try {
				driver.findElement(uniqueInStockSelector);
				String inStockTextFromElement = driver.findElement(uniqueInStockSelector).getText();
				System.out.println(inStockTextFromElement);
				break;
			} catch (NoSuchElementException e) {
				System.out.println("No Changes on Zoolert Yet!");
				Thread.sleep(WAIT_FOR_RELOAD);
			}
		}
		System.out.println("Found Something!");
	}

}
