package sites;

import java.io.FileNotFoundException;
import org.openqa.selenium.*;
import utils.DriverWrapper;
import utils.GeneralUtils;
import utils.MessageUtils;
import utils.PropUtils;
import utils.MessageUtils.MessageData;

public class Walmart {

	// List selectors here
	private static final By LOGIN_PAGE_LOGIN_FIELD = By.cssSelector("#email");
	private static final By LOGIN_PAGE_PASSWORD_FIELD = By.cssSelector("#password");
	private static final By LOGIN_PAGE_SIGNIN_BUTTON = By
			.cssSelector("form#sign-in-form button[data-automation-id = 'signin-submit-btn']");
	private static final By ADD_TO_CART_BUTTON = By
			.cssSelector("button[data-tl-id='ProductPrimaryCTA-cta_add_to_cart_button']");
	private static final By CHECK_OUT_BUTTON = By.cssSelector("div.Cart-PACModal-Body-right-rail button.checkoutBtn");
	private static final By REVIEW_YOUR_ORDER_BUTTON = By.cssSelector("button[data-automation-id='submit-payment-cc']");
	private static final By CCV_FIELD = By.cssSelector("input[data-automation-id='cvv-verify-cc-0']");
	private static final By PLACE_ORDER_BUTTON = By.cssSelector(
			"div:nth-child(2) > div > div > div.Grid-col.u-size-1.u-size-3-12-s.u-size-3-12-m > div > form > div");
	private static final By DELIVERY_OPTION_CONTINUE_BUTTON = By.cssSelector("div.CXO_fulfillment_continue button");
	private static final By CONFIRM_ADDRESS_CONTINUE_BUTTON = By
			.cssSelector("button[data-automation-id='address-book-action-buttons-on-continue']");
	private static final By RECAPTCHA_ALERT = By.cssSelector("div.re-captcha");

	// List URL's here
	private static final String LOGIN_URL = "https://www.walmart.com/account/login?ref=domain";
	private static final String PS5_CONSOLE_URL = "https://www.walmart.com/ip/PlayStation-5-Console/363472942";
	private static final String PS5_DIGITAL_URL = "https://www.walmart.com/ip/Sony-PlayStation-5-Digital-Edition/493824815";
	private static final String TEST_URL = "https://www.walmart.com/ip/Eagle-Claw-Fishing-Tackle-Lazer-Sharp-SlbIBKXL-7-Barrel-Swivel-with-Interlock-Snap-Size-7/50370034";

	private static MessageUtils messageUtils = new MessageUtils();
	protected DriverWrapper driver;

	private static GeneralUtils utils = new GeneralUtils();
	private static int RANDOM_PAGE_LOAD_DELAY = utils.getRandomTime(5000, 12000);

	public static void main(String[] args) throws Exception {
		String requestedItemURL = PS5_CONSOLE_URL;
		String messageSubject = ("In Stock Alert!!!");

		WalmartData.init();
		MessageData.init();

		DriverWrapper driver = new DriverWrapper();

		logIntoWalmart(driver);
		lookForAddToCartButton(driver, requestedItemURL, ADD_TO_CART_BUTTON);
		messageUtils.sendMessage(messageSubject, "ADDED TO CART " + requestedItemURL, MessageData.getMyCell(),
				MessageData.getMyEmail());
		messageUtils.sendMessage(messageSubject, "ADDED TO CART " + requestedItemURL, MessageData.getJasonCell(),
				MessageData.getKeithCell());

		driver.waitForElementThenClick(CHECK_OUT_BUTTON);
		driver.waitForElementThenClick(DELIVERY_OPTION_CONTINUE_BUTTON);
		driver.waitForElementThenClick(CONFIRM_ADDRESS_CONTINUE_BUTTON);
		driver.waitForElementThenClick(CCV_FIELD);
		driver.findElement(CCV_FIELD).sendKeys(WalmartData.getCCV());
		driver.waitForElementThenClick(REVIEW_YOUR_ORDER_BUTTON);
		driver.waitForElementThenClick(PLACE_ORDER_BUTTON);

		messageUtils.sendMessage("WALMART ALERT", "ORDER PLACED! " + requestedItemURL, MessageData.getMyCell(),
				MessageData.getMyEmail());
		messageUtils.sendMessage("WALMART ALERT", "ORDER PLACED! " + requestedItemURL, MessageData.getJasonCell(),
				MessageData.getKeithCell());
		driver.quit();
	}

	private static void checkRecaptcha(DriverWrapper driver) throws Exception {
		try {
			if (driver.tryDoesElementExist(RECAPTCHA_ALERT, 1000, 3)) {
				MessageData.init();
				messageUtils.sendMessage("WALMART ALERT", "Recaptcha Block! Try Again :/ ", MessageData.getMyCell(),
						MessageData.getMyEmail());
				Thread.sleep(200000);
				driver.quit();
			}
		} catch (Exception e) {
			// continue
		}
	}

	private static void logIntoWalmart(DriverWrapper driver) throws Exception {
		driver.get(LOGIN_URL);

		Thread.sleep(4123);
		driver.waitForElementThenClick(LOGIN_PAGE_LOGIN_FIELD);
		Thread.sleep(2556);
		driver.findElement(LOGIN_PAGE_LOGIN_FIELD).sendKeys(WalmartData.getEmail());
		Thread.sleep(2564);
		driver.findElement(LOGIN_PAGE_PASSWORD_FIELD).sendKeys(WalmartData.getPassword());
		Thread.sleep(3222);
		driver.findElement(By.cssSelector("label[for='remember-me']")).click();
		driver.findElement(LOGIN_PAGE_SIGNIN_BUTTON).click();
		Thread.sleep(2156);
	}

	private static void lookForAddToCartButton(DriverWrapper driver, String urlToBeScanned, By uniqueInStockSelector)
			throws Exception {
		boolean buttonFound = false;
		Thread.sleep(1325);

		while (buttonFound == false) {
			driver.get(urlToBeScanned);
			checkRecaptcha(driver);

			Thread.sleep(RANDOM_PAGE_LOAD_DELAY);

			try {
				driver.findElement(uniqueInStockSelector);
				driver.findElement(uniqueInStockSelector).click();
				buttonFound = true;
				break;
			} catch (Exception e) {
				System.out.println("Walmart - Add to Cart button Not Found");
				buttonFound = false;
			}
		}
		System.out.println("Found Something!");
	}

	private static class WalmartData {
		private static String email = null;
		private static String password = null;
		private static String ccv = null;

		private static void init() throws FileNotFoundException {
			PropUtils prop = new PropUtils();
			email = prop.getValue("WALMART_LOGIN");
			password = prop.getValue("WALMART_PASSWORD");
			ccv = prop.getValue("WALMART_CCV");
		}

		private static String getEmail() {
			return email;
		}

		private static String getPassword() {
			return password;
		}

		private static String getCCV() {
			return ccv;
		}
	}

	private Walmart(DriverWrapper driver) {
		this.driver = driver;
	}

}