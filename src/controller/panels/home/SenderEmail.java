package controller.panels.home;

import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SenderEmail {
	private static final String INVALID_PARAMETERS = "Tutti i parametri richiesti devono essere riempiti";

	private final char[] password;
	private static final String GMAIL_HOST = "smtp.gmail.com";
	private static final String YAHOO_HOST = "smtp.mail.yahoo.com";
	private static final int PORT = 25;
	
	private String host;
	private final String user;
	private final List<String> destinatari;
	private final String oggetto;
	private final String corpo;

	/**
	 * Costruttore completo, richiede i parametri di connessione al server di
	 * posta
	 * 
	 * @param user
	 * @param password
	 * @param host
	 * @param mittente
	 * @param destinatari
	 * @param oggetto
	 * @param allegati
	 */
	public SenderEmail(final String oggetto, final String corpo, final List<String> destinatari, final String user, final char[] password)  throws Exception{
		checkparameters(user, password, oggetto, corpo);
		this.destinatari = destinatari;
		this.user = user;
		setHost(user);
		this.password = password;
		this.oggetto = oggetto;
		this.corpo = corpo;
	}

	private void setHost(final String email) {
		final String[] parts = email.split("@");
		switch (parts[1]) {
			case "gmail.com":
				this.host = GMAIL_HOST;
				break;
			case "yahoo.com":
			  this.host = YAHOO_HOST;
			  break;
			case "yahoo.it":
			  this.host = YAHOO_HOST;
			  break;
		}
	}

	public void sendEmail() throws Exception{
 
		final Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.user", user);
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", PORT);

		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.socketFactory.port", PORT);

		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");

		final Session session = Session.getInstance(props, null);

		final MimeBodyPart messageBodyPart1 = new MimeBodyPart();

		final Multipart multipart = new MimeMultipart();
		final MimeMessage msg = new MimeMessage(session);

		msg.setSubject(oggetto);
		msg.setSentDate(new Date());
		msg.setFrom(new InternetAddress(user));

		InternetAddress[] addressTo = new InternetAddress[destinatari
				.size()];
		int i = 0;
		for (final String dest : destinatari) {
			addressTo[i] = new InternetAddress(dest);
			i++;
		}
		msg.setRecipients(Message.RecipientType.TO, addressTo);

		messageBodyPart1.setText(this.corpo);
		multipart.addBodyPart(messageBodyPart1);

		msg.setContent(multipart);

		final Transport transport = session.getTransport("smtps");
		transport.connect(host, user, new String(password));
		transport.sendMessage(msg, msg.getAllRecipients());
		transport.close();
	}

	private void checkparameters(final String user, final char[] password, final String oggetto, final String corpo) {
		if (user.equals("") || password.length == 0 || password == null || oggetto.equals("") || corpo.equals("")) {
			throw new IllegalArgumentException(INVALID_PARAMETERS);
		}
	}
}