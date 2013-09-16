import java.awt.Container;

import javax.swing.JApplet;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class ZEFlagCalcApplet extends JApplet {
	private static final long serialVersionUID = 2L;
	public void init() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
		//ZEFlagCalculator calc = new ZEFlagCalculator();
		//Container content = getContentPane();
		//content.add(calc);
		resize(480, 240);
	}
	
	private void createAndShowGUI() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		
		//JFrame frame = new JFrame("ZEFlagCalc");
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JComponent newContPane = new ZEFlagCalculator();
		//newContPane.setOpaque(true);
		Container content = getContentPane();
		content.add(newContPane);
	}
	
	public void main(String[] args) {
		init();
	}
}
