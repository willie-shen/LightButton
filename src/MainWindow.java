import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.fazecast.jSerialComm.SerialPort;

public class MainWindow {

	private JFrame frame;
	private static SerialPort comPort;
	private static int state;

	static Connection conn = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//Connect to the database

		try {
			conn = DriverManager.getConnection("jdbc:mysql://lightbutton.cz5aiacvevw3.us-west-2.rds.amazonaws.com:3306/LightButton?useSSL=false&user=willieshen&password=password");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println("Connected to the database");
		
		java.sql.PreparedStatement getState = null;
		String getStateID = "SELECT * FROM LightState WHERE LightID = 1";
		
			try {
				getState = conn.prepareStatement(getStateID);
				ResultSet rs = getState.executeQuery();
				
				if(rs.next()) {
					state = rs.getInt("lightState");
					System.out.println("State: " + state);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	
		
		comPort = SerialPort.getCommPorts()[4];
		comPort.openPort();
		
		System.out.println("Connected to " + comPort.getDescriptivePortName());
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		byte[] l = {'L'};
		byte[] h = {'H'};
		
		if(state == 0)
		{
			System.out.println("Turning off");
			comPort.writeBytes(l,  1);
		}
		else {
			System.out.println("Turning on");
			comPort.writeBytes(h, 1);
		}
		
		
		
		
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
				
				java.sql.PreparedStatement changeState = null;
				
			
				if(state==0)
				{
					comPort.writeBytes(h,  1);
					state = 1;
					
					java.sql.Statement st = null;
					
					String getStateID = "UPDATE LightState SET lightState = 1 where lightID = 1";
					
					try {
						//changeState = conn.prepareStatement(getStateID);
						//ResultSet rs = changeState.executeQuery();
						
						st = conn.createStatement();
						st.executeUpdate(getStateID);
						
						//if(rs.next()) {
						//	state = rs.getInt("lightState");
						//	System.out.println(state);
					//	}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
					
					
				else {
					comPort.writeBytes(l, 1);
					state = 0; 
					
					java.sql.Statement st = null;
					
					String getStateID = "UPDATE LightState SET lightState = 0 where lightID = 1";
					
					try {
						//changeState = conn.prepareStatement(getStateID);
						//ResultSet rs = changeState.executeQuery();
						
						st = conn.createStatement();
						st.executeUpdate(getStateID);
						
						//if(rs.next()) {
						//	state = rs.getInt("lightState");
						//	System.out.println(state);
					//	}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			
		});
		btnClickHereTo.setBounds(96, 148, 242, 29);
		frame.getContentPane().add(btnClickHereTo);
		
		JButton btnHold = new JButton("Hold");
		btnHold.addMouseListener(new MouseAdapter() {
			private java.sql.PreparedStatement changeState = null;
			
			@Override
			public void mousePressed(MouseEvent e) {
				byte[] h = {'H'};
				comPort.writeBytes(h,  1);
				state = 1;
				
				java.sql.Statement st = null;
				
				String getStateID = "UPDATE LightState SET lightState = 1 where lightID = 1";
				
				try {
					//changeState = conn.prepareStatement(getStateID);
					//ResultSet rs = changeState.executeQuery();
					
					st = conn.createStatement();
					st.executeUpdate(getStateID);
					
					//if(rs.next()) {
					//	state = rs.getInt("lightState");
					//	System.out.println(state);
				//	}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				byte[] l = {'L'};
				comPort.writeBytes(l,  1);
				state = 0;
				
	java.sql.Statement st = null;
				
				String getStateID = "UPDATE LightState SET lightState = 0 where lightID = 1";
				
				try {
					//changeState = conn.prepareStatement(getStateID);
					//ResultSet rs = changeState.executeQuery();
					
					st = conn.createStatement();
					st.executeUpdate(getStateID);
					
					//if(rs.next()) {
					//	state = rs.getInt("lightState");
					//	System.out.println(state);
				//	}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		btnHold.setBounds(96, 189, 117, 29);
		frame.getContentPane().add(btnHold);
	}
}
