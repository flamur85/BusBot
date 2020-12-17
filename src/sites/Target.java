package sites;

import java.io.FileNotFoundException;
import org.openqa.selenium.*;
import utils.DriverWrapper;
import utils.GeneralUtils;
import utils.MessageUtils;
import utils.PropUtils;
import utils.MessageUtils.MessageData;

public class Target {
	// List of Wait Time variables
	private static final long WAIT_FOR_RELOAD = 10000;
	private static final long PAGE_LOAD_TIME = 1000;

	// List selectors here
	private static final By LOGIN_PAGE_LOGIN_FIELD = By.cssSelector("input#username");
	private static final By LOGIN_PAGE_PASSWORD_FIELD = By.cssSelector("input#password");
	private static final By LOGIN_PAGE_SIGNIN_BUTTON = By.cssSelector("button#login");
	private static final By ADD_TO_CART_BUTTON = By.cssSelector("button[data-test='orderPickupButton']");
	private static final By DECLINE_COVERAGE_BUTTON = By.cssSelector("button[aria-label='close']");
	private static final By CHECKOUT_BUTTON = By.cssSelector("span[data-icon-name='CommerceCart']");
	private static final By READY_TO_CHECKOUT_BUTTON = By.cssSelector("button[data-test='checkout-button']");
	private static final By PLACE_YOUR_ORDER_BUTTON = By.cssSelector("button[data-test='placeOrderButton']");
	private static final By CCV_FIELD = By.cssSelector("input#creditCardInput-cvv");
	private static final By SIGNIN_BUTTON = By.cssSelector("nav a#account span[data-icon-name='ProfileUserCircle']");
	private static final By SIGNIN_LI = By.cssSelector("li[id='accountNav-signIn'] a div");

	private static final String PS5_CONSOLE_URL = "https://www.target.com/p/playstation-5-console/-/A-81114595#lnk=sametab";
	private static final String PS5_DIGITAL_URL = "https://www.target.com/p/playstation-5-digital-edition-console/-/A-81114596";
	private static final String TEST_URL = "https://www.target.com/p/keurig-k-classic-single-serve-k-cup-pod-coffee-maker-k50/-/A-50981282";

	protected DriverWrapper driver;

	private Target(DriverWrapper driver) {
		this.driver = driver;
	}

	public static void main(String[] args) throws Exception {
		String urlToBeScanned = PS5_CONSOLE_URL;
		String messageSubject = ("In Stock Alert!!!");

		TargetData.init();
		MessageData.init();
		DriverWrapper driver = new DriverWrapper();

		driver.get("https://www.target.com");
		driver.waitForElementThenClick(SIGNIN_BUTTON);
		Thread.sleep(2000);
		driver.waitForElementThenClick(SIGNIN_LI);
		driver.waitForElementThenClick(LOGIN_PAGE_LOGIN_FIELD);
		driver.findElement(LOGIN_PAGE_LOGIN_FIELD).sendKeys(TargetData.getEmail());
		driver.findElement(LOGIN_PAGE_PASSWORD_FIELD).sendKeys(TargetData.getPassword());
		driver.findElement(LOGIN_PAGE_SIGNIN_BUTTON).click();
		Thread.sleep(30000); // hit back on the browser, then forward, then log in... damn target

		driver.get(urlToBeScanned);
		lookForAddToCartButton(driver, urlToBeScanned, ADD_TO_CART_BUTTON);

		MessageUtils messageUtils = new MessageUtils();
		messageUtils.sendMessage(messageSubject, "ADDED TO CART " + urlToBeScanned, MessageData.getMyCell(),
				MessageData.getMyEmail());
		messageUtils.sendMessage(messageSubject, "ADDED TO CART " + urlToBeScanned, MessageData.getJasonCell(),
				MessageData.getKeithCell());

		GeneralUtils test = new GeneralUtils();
		test.playAlertSound();

		driver.waitForElementThenClick(DECLINE_COVERAGE_BUTTON);
		driver.waitForElementThenClick(CHECKOUT_BUTTON);
		driver.waitForElementThenClick(READY_TO_CHECKOUT_BUTTON);
		try {
			driver.waitForElementThenClick(CCV_FIELD);
			driver.findElement(CCV_FIELD).sendKeys(TargetData.getCCV());
		} catch (Exception e) {
			System.out.println("Did not ask for ccv");
		}

		driver.waitForElementThenClick(PLACE_YOUR_ORDER_BUTTON);
		messageUtils.sendMessage("TARGET ALERT", "ORDER PLACED! " + urlToBeScanned, MessageData.getMyCell(),
				MessageData.getMyEmail());
		messageUtils.sendMessage("TARGET ALERT", "ORDER PLACED! " + urlToBeScanned, MessageData.getJasonCell(),
				MessageData.getKeithCell());
		driver.quit();
	}

	private static class TargetData {
		private static String email = null;
		private static String password = null;
		private static String ccv = null;

		private static void init() throws FileNotFoundException {
			PropUtils prop = new PropUtils();
			email = prop.getValue("TARGET_LOGIN");
			password = prop.getValue("TARGET_PASSWORD");
			ccv = prop.getValue("TARGET_CCV");
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

	private static void lookForAddToCartButton(DriverWrapper driver, String urlToBeScanned, By uniqueInStockSelector)
			throws InterruptedException {
		boolean buttonFound = false;

		while (buttonFound == false) {
			driver.get(urlToBeScanned);
			Thread.sleep(PAGE_LOAD_TIME);

			try {
				driver.findElement(uniqueInStockSelector).click();
				buttonFound = true;
				break;
			} catch (NoSuchElementException e) {
				System.out.println("Nothing yet!");
				buttonFound = false;
				Thread.sleep(WAIT_FOR_RELOAD);
			}
		}
		System.out.println("Found Something!");
	}

}