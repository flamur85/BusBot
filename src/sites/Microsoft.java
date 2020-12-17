package sites;

import java.io.FileNotFoundException;
import org.openqa.selenium.*;
import utils.DriverWrapper;
import utils.MessageUtils;
import utils.MessageUtils.MessageData;

public class Microsoft {
	// List of Wait Time variables
	private static final long WAIT_FOR_RELOAD = 10000;
	private static final long PAGE_LOAD_TIME = 1000;

	// List selectors here
	private static final By ADD_TO_CART_BUTTON = By.cssSelector("div.pb-4 button[disabled]");

	private static final String XBOX_URL = "https://www.xbox.com/en-us/configure/8WJ714N3RBTL?irgwc=1&OCID=AID2000142_aff_7593_1243925&tduid=%28ir__y1whbphqikkfqjiqkk0sohzn3f2xsorioemwo9x200%29%287593%29%281243925%29%28MfQy8kfx.Gk-Y1hUPSY3GtbMg0mSxiIfsA%29%28%29&irclickid=_y1whbphqikkfqjiqkk0sohzn3f2xsorioemwo9x200";

	private static MessageUtils messageUtils = new MessageUtils();
	protected DriverWrapper driver;

	private Microsoft(DriverWrapper driver) {
		this.driver = driver;
	}

	public static void main(String[] args) throws Exception {
		String urlToBeScanned = XBOX_URL;
		String messageSubject = ("In Stock Alert!!!");

		MessageData.init();
		DriverWrapper driver = new DriverWrapper();

		driver.get(urlToBeScanned);
		lookForAddToCartButton(driver, urlToBeScanned, ADD_TO_CART_BUTTON);

		messageUtils.sendMessage(messageSubject, "ADDED TO CART " + urlToBeScanned, MessageData.getMyCell(),
				MessageData.getMyEmail());
		messageUtils.sendMessage(messageSubject, "ADDED TO CART " + urlToBeScanned, MessageData.getJasonCell(),
				MessageData.getKeithCell());
	}

	private static void lookForAddToCartButton(DriverWrapper driver, String urlToBeScanned, By uniqueInStockSelector)
			throws InterruptedException, FileNotFoundException {
		boolean buttonFound = false;

		MessageData.init();

		try {
			while (buttonFound == false) {
				Thread.sleep(WAIT_FOR_RELOAD);
				driver.get(urlToBeScanned);
				Thread.sleep(PAGE_LOAD_TIME);
				if (driver.findElement(uniqueInStockSelector) == null) {
					buttonFound = true;
				} else {
					System.out.println("Microsoft - Add to Cart button still not there.");
					buttonFound = false;
				}
			}

		} catch (Exception e) {
			messageUtils.sendMessage("Something went wrong", "ADDED TO CART " + urlToBeScanned, MessageData.getMyCell(),
					MessageData.getMyEmail());
			System.out.println(e);
		}
	}
}