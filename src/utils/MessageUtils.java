package utils;

import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MessageUtils {

	/**
	 * @author Tony Bomova
	 *
	 */
	public static class MessageData {
		public static String emailClientEmailAddress;
		public static String emailClientKey;
		public static String myCell;
		public static String myEmail;
		public static String jasonCell;
		public static String keithCell;

		public static void init() throws FileNotFoundException {
			PropUtils prop = new PropUtils();

			// Yahoo Client Credentials
			emailClientEmailAddress = prop.getValue("EMAIL_CLIENTS_EMAIL_ADDRESS");
			emailClientKey = prop.getValue("EMAIL_CLIENTS_KEY");

			// Only works with Verizon phone numbers and emails
			myCell = prop.getValue("MY_CONTACT_1");
			myEmail = prop.getValue("MY_CONTACT_2");
			jasonCell = prop.getValue("OTHER_CONTACT_1");
			keithCell = prop.getValue("OTHER_CONTACT_2");
		}

		public static String getEmailClientEmailAddress() {
			return emailClientEmailAddress;
		}

		public static String getEmailClientKey() {
			return emailClientKey;
		}

		public static String getMyCell() {
			return myCell;
		}

		public static String getMyEmail() {
			return myEmail;
		}

		public static String getJasonCell() {
			return jasonCell;
		}

		public static String getKeithCell() {
			return keithCell;
		}

	}

	/**
	 * Pre set up for Yahoo email
	 * 
	 * @param messageSubject
	 * @param messageBody
	 * @param toRecipient
	 * @param ccRecipient
	 */
	public void sendMessage(String messageSubject, String messageBody, String toRecipient, String ccRecipient) {
		try {
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.mail.yahoo.com");
			props.put("mail.smtp.auth", "true");
			props.put("mail.debug", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.port", "465");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.socketFactory.fallback", "false");

			Session mailSession = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(MessageData.getEmailClientEmailAddress(),
							MessageData.getEmailClientKey());
				}
			});

			mailSession.setDebug(true); // Enable the debug mode

			Message msg = new MimeMessage(mailSession);

			// --[ Set the FROM, TO, DATE and SUBJECT fields
			msg.setFrom(new InternetAddress(MessageData.getEmailClientEmailAddress()));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toRecipient));
			msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccRecipient));
			msg.setSentDate(new Date());
			msg.setSubject(messageSubject);

			// --[ Create the body of the mail
			msg.setText(messageBody);

			// --[ Ask the Transport class to send our mail message
			Transport.send(msg);

		} catch (Exception E) {
			System.out.println("Something went wrong with the MessageUtils class");
			System.out.println(E);
		}

	}
}
