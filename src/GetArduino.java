import com.fazecast.jSerialComm.*;

public class GetArduino {

	public static void main(String args[]) {
		//for(int i = 0; i<SerialPort.getCommPorts().length; i++)
		//{
		//	System.out.println(SerialPort.getCommPorts()[i].getDescriptivePortName());
		//}
		
		System.out.println(SerialPort.getCommPorts()[4].getDescriptivePortName());
		
		//Lets use Port Number 4 for the Arduino
	}
}
