package sites;

import org.openqa.selenium.*;

import utils.DriverWrapper;
import utils.GeneralUtils;
import utils.MessageUtils;
import utils.MessageUtils.MessageData;

public class PlaystationDirect {
	// List of Wait Time variables
	private static final long PAGE_LOAD_TIME = 12000;

	// List selectors here
	private static final By CONSOLE_ADD_TO_CART_BUTTON = By
			.cssSelector("div.card-left button.transparent-orange-button");
	private static final By DIGITAL_ADD_TO_CART_BUTTON = By
			.cssSelector("div.card-right button.transparent-orange-button");
	private static final By RECAPTCHA = By.cssSelector("div#divChallenge");

	private static final String PS5_URL = "https://direct.playstation.com/en-us/hardware/ps5";
	private static final String TEST_PS4_URL = "https://direct.playstation.com/en-us/hardware/ps4";

	private static GeneralUtils util = new GeneralUtils();

	protected DriverWrapper driver;

	private PlaystationDirect(DriverWrapper driver) {
		this.driver = driver;
	}

	static MessageUtils messageUtils = new MessageUtils();

	public static void main(String[] args) throws Exception {
		String urlToBeScanned = PS5_URL;

		DriverWrapper driver = new DriverWrapper();
		Thread.sleep(1999);
		lookForAddToCartButton(driver, PS5_URL, CONSOLE_ADD_TO_CART_BUTTON);

		util.playAlertSound();

		keepDriverOpen(driver, urlToBeScanned);
	}

	private static void keepDriverOpen(DriverWrapper driver, String urlToBeScanned) throws Exception {
		boolean button = false;

		while (button == false) {
			MessageData.init();
			messageUtils.sendMessage("PlaystationDirect is up", "Where the hell are you!", MessageData.getMyCell(),
					MessageData.getMyEmail());
			Thread.sleep(10000000);
		}
	}

	private static void lookForAddToCartButton(DriverWrapper driver, String urlToBeScanned, By uniqueInStockSelector)
			throws Exception {
		boolean addToStockSelector = false;
		boolean messageSent = false;
		boolean recaptchaPresent = false;

		MessageData.init();

		while (addToStockSelector == false) {
			Thread.sleep(PAGE_LOAD_TIME);
			if (recaptchaPresent == false) {
				driver.get(urlToBeScanned);
			}

			if (driver.tryDoesElementExist(RECAPTCHA, 1000, 3) && messageSent == false) {
				messageUtils.sendMessage("Recaptcha...Maybe its something.", "Go To: " + urlToBeScanned,
						MessageData.getMyCell(), MessageData.getMyEmail());
				messageSent = true;
				recaptchaPresent = true;
			} else {
				try {
					driver.findElement(uniqueInStockSelector).click();
					addToStockSelector = true;
					util.playAlertSound();
					messageUtils.sendMessage("PS5 ALERT!", "Able to click add to cart. Go To: " + urlToBeScanned,
							MessageData.getMyCell(), MessageData.getMyEmail());
					break;
				} catch (Exception e) {
					System.out.println("Playstation Direct - Add To Cart button not available yet!");
					addToStockSelector = false;
				}
				if (addToStockSelector == true) {
					break;
				}
			}

		}
		System.out.println("Playstation 5 Found, Good Luck!");
	}

}