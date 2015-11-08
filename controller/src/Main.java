import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import com.cinterion.io.ATCommand;
import com.cinterion.io.ATCommandFailedException;
import com.cinterion.io.ATCommandListener;
import com.cinterion.io.OutPort;

import java.io.IOException;
import java.util.Vector;
import com.cinterion.io.InPort;
import com.cinterion.io.InPortListener;
import com.cinterion.io.ADC;

public class Main extends MIDlet{

	private ATCommand atc;
	InPort inPort = null;
	OutPort outPort;
	private ADC adc;

	public Main() {
		// TODO Auto-generated constructor stub
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {

		System.out.println("SmsReceiver stopped");

		try {
			inPort.release();
		} catch (IOException e) {
		} catch (NullPointerException e) {
		}

		notifyDestroyed();

	}

	protected void pauseApp() {
		// TODO Auto-generated method stub
	}

	protected void startApp() throws MIDletStateChangeException {

		Vector inPins = new Vector();
		inPins.addElement("GPIO11");
		inPins.addElement("GPIO12");
		inPins.addElement("GPIO5");
		
		// configuration for output port with 8 pins
		Vector outPins = new Vector(8);
		Vector outValues = new Vector(8);
		//outPins.addElement("GPIO5"); outValues.addElement(new Integer(1));
		outPins.addElement("GPIO6"); outValues.addElement(new Integer(1));
		outPins.addElement("GPIO8"); outValues.addElement(new Integer(1));

		System.out.println("SmsReceiver started");

		try {
			final ADC adc = new ADC(0, 0);
		
			// create input port
			inPort = new InPort(inPins);
			
			outPort = new OutPort(outPins, outValues);
			//int portLen = outPort.getPins().size();
		
			System.out.println("InPort value: " + inPort.getValue());
			
			for (int i = 0; i < 2; i++) {
				System.out.println("Flasher thread completed " + adc.getValue()
						* 4.17);
				Thread.sleep(1000);
			}
			// create an instance of ATCommand
			ATCommand atc = new ATCommand(false);
			
			Message.init(atc, outPort);
			Message.sendSMS("MessageTest:Salut", "+40xxxxxxxxx", atc);
			System.out.println(inPort.getValue());

		} catch (Exception e) {
			e.printStackTrace();
		}

		//destroyApp(true);

	}

	

	

}
