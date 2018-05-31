import java.awt.EventQueue;

import javax.swing.JFrame;

import com.fazecast.jSerialComm.SerialPort;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainWindow {

	private JFrame frame;
	private static SerialPort comPort;
	private int state = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		comPort = SerialPort.getCommPorts()[4];
		comPort.openPort();
		
		System.out.println("Connected to " + comPort.getDescriptivePortName());
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		//comPort.closePort();
		//System.out.println(comPort.getDescriptivePortName() + " has been closed");

	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnClickHereTo = new JButton("Click here to Turn LED On");
		btnClickHereTo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				byte[] h = {'H'};
				byte[] l = {'L'};
				
				if(state==0)
				{
					comPort.writeBytes(h,  1);
					state = 1;
				}
					
					
				else {
					comPort.writeBytes(l, 1);
					state = 0;
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				byte[] l = {'L'};
				
				comPort.writeBytes(l,  1);
			}
		});
		btnClickHereTo.setBounds(96, 148, 242, 29);
		frame.getContentPane().add(btnClickHereTo);
	}
}
