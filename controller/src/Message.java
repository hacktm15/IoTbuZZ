import java.io.IOException;

import com.cinterion.io.ATCommand;
import com.cinterion.io.ATCommandFailedException;
import com.cinterion.io.ATCommandListener;
import com.cinterion.io.OutPort;

public class Message {

	static int flag = 0;
	public static void init(ATCommand atc, final OutPort outPort ) {
		try {
			final ATCommand at = atc;
			
			outPort.setValue(0);

			// create the AT command listener
			ATCommandListener listener = new ATCommandListener() {

				public void RINGChanged(boolean SignalState) {
					System.out.println("22");
				}

				public void DSRChanged(boolean SignalState) {
					System.out.println("65");
				}

				public void DCDChanged(boolean SignalState) {
					System.out.println("15");
				}

				public void CONNChanged(boolean SignalState) {
					System.out.println("33");
				}

				public void ATEvent(final String Event) {

					System.out.println("received URC: " + Event);

					// search for SMS related URCs
					if (Event.indexOf("+CMTI") > 0) {
						//LedON
						if (flag == 0){
							try {
								
								outPort.setValue(1);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							flag = 1;
						}else if (flag == 1){//LedOFF
							try {
								
								outPort.setValue(0);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						String content = getSmsContent(Event, false, at);

						System.out.println("Sms content: " + content);
					}

				}
			};
			// add (activate) the listener
			atc.addListener(listener);

			// check the registration
			boolean registered = false;

			while (registered == false) {

				String registration_response = atc.send("AT+CREG?\r");

				int localy_registered = registration_response.indexOf(",1");

				if ((localy_registered > -1)) {
					registered = true;
					System.out.println("Module registered to the network");
				} else {
					System.out.println("Module not registered to the network");
				}

				Thread.sleep(2000);

				// switch to the text mode
				System.out.println(atc.send("at+cmgf=1\r"));

				// activate the SMS URCs
				System.out.println(atc.send("at+cnmi=2,1\r"));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public static void sendSMS(String text, String nrTel, ATCommand atc)
			throws IllegalStateException, IllegalArgumentException,
			ATCommandFailedException {

		int SMS_CAPACITY = 160;
		int CONCATENATED_SMS_CAPACITY = 150;
		atc.send("at+cmgf=1\r");

		if (text.length() <= SMS_CAPACITY) {
			// Simple message
			String raspuns = atc.send(new StringBuffer("at+cmgs=\"")
					.append(nrTel).append('"').append("\r").toString());
			if (raspuns.indexOf('>') != -1) {
				raspuns = atc.send(new StringBuffer(text).append((char) 26)
						.toString());
				if (raspuns.indexOf("+cmgs\r") != -1) {
					return;
				}

			}
		}
	}

	public static String getSmsContent(String arg0, boolean deleteSms,
			ATCommand atc) {

		// index of received SMS message in the memory
		int idx = arg0.indexOf(",");

		String sms_positionInMemory = arg0.substring(idx + 1, idx + 3);

		// get the SMS content
		String contentOfSms = "";
		try {

			// get the message from the index position
			String response = atc
					.send("AT+CMGR=" + sms_positionInMemory + "\r");
			// delete the SMS
			if (deleteSms) {
				atc.send("AT+CMGD=" + sms_positionInMemory + "\r");
			}

		} catch (Exception e) {
			e.printStackTrace();
			contentOfSms = null;
		}
		return contentOfSms;
	}

}
