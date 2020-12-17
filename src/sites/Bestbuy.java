package sites;

import java.io.FileNotFoundException;
import org.openqa.selenium.*;
import utils.DriverWrapper;
import utils.GeneralUtils;
import utils.MessageUtils;
import utils.PropUtils;
import utils.MessageUtils.MessageData;

public class Bestbuy {
	protected DriverWrapper driver;

	private Bestbuy(DriverWrapper driver) {
		this.driver = driver;
	}

	// List of Wait Time variables
	private static final long WAIT_FOR_RELOAD = 8000;
	private static final long PAGE_LOAD_TIME = 1000;

	// List selectors here
	private static final By LOGIN_PAGE_LOGIN_FIELD = By.cssSelector("input.tb-input[type='email']");
	private static final By LOGIN_PAGE_PASSWORD_FIELD = By.cssSelector("input.tb-input[type='password']");
	private static final By LOGIN_PAGE_SIGNIN_BUTTON = By.cssSelector("div.cia-form__controls button");
	private static final By ADD_TO_CART_BUTTON = By
			.cssSelector("div[id *= 'fulfillment-add-to-cart-button'] > div > div > div > button.btn-primary");
	private static final By GO_TO_CART_BUTTON = By.cssSelector("div.go-to-cart-button");
	private static final By CHECKOUT_BUTTON = By.cssSelector("div.checkout-buttons__checkout button");
	private static final By CONTINUE_TO_PAYMENT_BUTTON = By.cssSelector("div.button--continue button");
	private static final By PLACE_YOUR_ORDER_BUTTON = By.cssSelector("button.button__fast-track");
	private static final By CCV_FIELD = By.cssSelector("div.credit-card-form__cvv-wrap input");

	private static final String PS5_CONSOLE_URL = "https://www.bestbuy.com/site/sony-playstation-5-console/6426149.p?skuId=6426149";
	private static final String PS5_DIGITAL_URL = "https://www.bestbuy.com/site/sony-playstation-5-digital-edition-console/6430161.p?skuId=6430161";
	private static final String RTX3070_URL = "https://www.bestbuy.com/site/nvidia-geforce-rtx-3070-8gb-gddr6-pci-express-4-0-graphics-card-dark-platinum-and-black/6429442.p?skuId=6429442";
	private static final String TEST_URL = "https://www.bestbuy.com/site/skullcandy-sesh-evo-true-wireless-in-ear-headphones-true-black/6412863.p?skuId=6412863";

	private static MessageUtils messageUtils = new MessageUtils();

	public static void main(String[] args) throws Exception {
		String urlToBeScanned = RTX3070_URL;
		String messageSubject = ("In Stock Alert!!!");

		BestbuyData.init();
		MessageData.init();

		DriverWrapper driver = new DriverWrapper();

		driver.get(urlToBeScanned);
		lookForAddToCartButton(driver, urlToBeScanned, ADD_TO_CART_BUTTON);

		messageUtils.sendMessage(messageSubject, "ADDED TO CART " + urlToBeScanned, MessageData.getMyCell(),
				MessageData.getMyEmail());
		messageUtils.sendMessage(messageSubject, "ADDED TO CART " + urlToBeScanned, MessageData.getJasonCell(),
				MessageData.getKeithCell());
		
		GeneralUtils test = new GeneralUtils();
		test.playAlertSound();

		driver.waitForElementThenClick(GO_TO_CART_BUTTON);
		driver.waitForElementThenClick(CHECKOUT_BUTTON);
		driver.waitForElementThenClick(LOGIN_PAGE_LOGIN_FIELD);
		driver.findElement(LOGIN_PAGE_LOGIN_FIELD).sendKeys(BestbuyData.getEmail());
		driver.findElement(LOGIN_PAGE_PASSWORD_FIELD).sendKeys(BestbuyData.getPassword());
		driver.findElement(LOGIN_PAGE_SIGNIN_BUTTON).click();
		driver.waitForElementThenClick(CONTINUE_TO_PAYMENT_BUTTON);
		driver.waitForElementThenClick(CCV_FIELD);
		driver.findElement(CCV_FIELD).sendKeys(BestbuyData.getCCV());
		driver.waitForElementThenClick(PLACE_YOUR_ORDER_BUTTON);

		driver.quit();

		messageUtils.sendMessage("BESTBUY ORDER COMPLETE! ", "ADDED TO CART " + urlToBeScanned, MessageData.getMyCell(),
				MessageData.getMyEmail());
		messageUtils.sendMessage("BESTBUY ORDER COMPLETE! ", "ADDED TO CART " + urlToBeScanned, MessageData.getMyCell(),
				MessageData.getMyEmail());
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
				System.out.println("Bestbuy - Add to Cart button still not here.");
				buttonFound = false;
				Thread.sleep(WAIT_FOR_RELOAD);
			}
		}
		System.out.println("Added to Bestbuy Cart!");
		GeneralUtils util = new GeneralUtils();
		util.playAlertSound();
	}

	private static class BestbuyData {
		private static String email = null;
		private static String password = null;
		private static String ccv = null;

		private static void init() throws FileNotFoundException {
			PropUtils prop = new PropUtils();
			email = prop.getValue("BESTBUY_LOGIN");
			password = prop.getValue("BESTBUY_PASSWORD");
			ccv = prop.getValue("BESTBUY_CCV");
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
}