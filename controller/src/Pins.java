import java.io.IOException;
import com.cinterion.io.InPortListener;
import com.cinterion.io.OutPort;
import com.cinterion.io.ADC;


public class Pins extends Thread implements InPortListener {

	public synchronized void portValueChanged(int portValue) {
		if( portValue == 1){
			System.out.println("Port 1");
				this.notify();
		}else if( portValue == 2) {
			System.out.println("Port 2");
		}
	}
}
