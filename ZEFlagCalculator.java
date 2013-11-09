import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ZEFlagCalculator extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	JCheckBox emissive, glow, singleS, doubleS, hard, perpixel, additive, spec;
	JCheckBox[] checkBoxes;
	JTextField pckd, unpckd;
	JLabel status;
	int[] flagValues;
	
	public ZEFlagCalculator() {
		flagValues = new int[8];
		flagValues[0] = 128;
		flagValues[1] = 64;
		flagValues[2] = 32;
		flagValues[3] = 16;
		flagValues[4] = 8;
		flagValues[5] = 4;
		flagValues[6] = 2;
		flagValues[7] = 1;
		
		// Emissive.
		emissive = new JCheckBox("Emissive");
		JLabel emissiveVals = new JLabel("int(1) = hex(01)");
		// Glow.
		glow = new JCheckBox("Glow");
		JLabel glowVals = new JLabel("int(2) = hex(02)");
		// Singlesided.
		singleS = new JCheckBox("Singlesided");
		JLabel singleVals = new JLabel("int(4) = hex(04)");
		// Doublesided.
		doubleS = new JCheckBox("Doublesided");
		JLabel doubleVals = new JLabel("int(8) = hex(08)");
		// Hardedged..
		hard = new JCheckBox("Hardedged Transparency");
		JLabel hardVals = new JLabel("int(16) = hex(10)");
		// Perpixel.
		perpixel = new JCheckBox("Per-Pixel Lighting");
		JLabel perpVals = new JLabel("int(32) = hex(20)");
		// Additive.
		additive = new JCheckBox("Additive Transparency");
		JLabel addVals = new JLabel("int(64) = hex(40)");
		// Specular.
		spec = new JCheckBox("Specular");
		JLabel specVals = new JLabel("int(128) = hex(80)");
		checkBoxes = new JCheckBox[8];
		checkBoxes[0] = spec;
		checkBoxes[1] = additive;
		checkBoxes[2] = perpixel;
		checkBoxes[3] = hard; 
		checkBoxes[4] = doubleS;
		checkBoxes[5] = singleS;
		checkBoxes[6] = glow;
		checkBoxes[7] = emissive;
		
		JPanel emPanel = new JPanel(new GridLayout(0, 2));
		emPanel.add(emissive);
		emPanel.add(emissiveVals);
		emPanel.add(glow);
		emPanel.add(glowVals);
		emPanel.add(singleS);
		emPanel.add(singleVals);
		emPanel.add(doubleS);
		emPanel.add(doubleVals);
		emPanel.add(hard);
		emPanel.add(hardVals);
		emPanel.add(perpixel);
		emPanel.add(perpVals);
		emPanel.add(additive);
		emPanel.add(addVals);
		emPanel.add(spec);
		emPanel.add(specVals);
		//emPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
		
		// Stuff.
		JLabel pLb = new JLabel("Hex Value");
		pckd = new JTextField();
		JLabel uLb = new JLabel("Int Value");
		unpckd = new JTextField();
		JButton pckdToFlags = new JButton("Hex to Flags");
		pckdToFlags.setActionCommand("unpack");
		JButton unpckdToFlags = new JButton("Int to Flags");
		unpckdToFlags.setActionCommand("decalc");
		JButton flagsToValues = new JButton("Flags to Values");
		flagsToValues.setActionCommand("pack");
		
		pckdToFlags.addActionListener(this);
		unpckdToFlags.addActionListener(this);
		flagsToValues.addActionListener(this);
		
		JPanel btPanel = new JPanel(new GridLayout(0, 1));
		btPanel.add(pLb);
		btPanel.add(pckd);
		btPanel.add(uLb);
		btPanel.add(unpckd);
		btPanel.add(pckdToFlags);
		btPanel.add(unpckdToFlags);
		btPanel.add(flagsToValues);
		//btPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		
		JLabel copyright = new JLabel("Code Copyright (C) Ande 2012");
		JLabel website = new JLabel("https://sites.google.com/site/andescp/");
		
		Box controls = Box.createHorizontalBox();
		controls.add(emPanel);
		controls.add(btPanel);
		
		status = new JLabel("Welcome to ZE Flag Calc!");
		//status.setAlignmentX(100.0f);
		Box statusB = Box.createHorizontalBox();
		statusB.add(status);
		statusB.add(Box.createHorizontalGlue());
		Box copyB = Box.createHorizontalBox();
		copyB.add(copyright);
		copyB.add(Box.createHorizontalGlue());
		
		Box vB = Box.createVerticalBox();
		vB.add(controls);
		vB.add(statusB);
		vB.add(copyB);
		
		add(vB, BorderLayout.LINE_START);
		
	}
	private void intToFlags() {
		int val;
		try{
			val = Integer.parseInt(unpckd.getText());
		}
		catch(Exception e) {
			status.setText("Input not valid.");
			status.setForeground(Color.RED);
			return;
		}
		if(val > 255 || val < 0) {
			status.setText("Input not valid.");
			status.setForeground(Color.RED);
			return;
		}
		int newVal = val;
		for(int i=0; i<8; i++) {
			newVal -= flagValues[i];
			if(newVal<0) {
				checkBoxes[i].setSelected(false);
				newVal += flagValues[i];
			}
			else {
				checkBoxes[i].setSelected(true);
			}
		}
		status.setText("Converted successfully.");
		status.setForeground(Color.GREEN);
	}
	
	private void flagsToValues() {
		int flagVal = 0;
		for(int i=0; i < 8; i++) {
			if(checkBoxes[i].isSelected()) {
				flagVal += flagValues[i];
			}
		}
		unpckd.setText(String.valueOf(flagVal));
		pckd.setText(Integer.toHexString(flagVal));
	}
	
	public void actionPerformed(ActionEvent e) {
		if("unpack".equals(e.getActionCommand())) {
			String in = pckd.getText();
			int unpacked;
			if(in.equals("")) {
				status.setText("Input not valid.");
				status.setForeground(Color.RED);
				return;
			}
			try{
				unpacked = Integer.parseInt(in, 16);
			}
			catch(Exception e1) {
				status.setText("Input not valid.");
				status.setForeground(Color.RED);
				return;
			}
			if(unpacked > 255 || unpacked < 0) {
				status.setText("Input not valid.");
				status.setForeground(Color.RED);
				return;
			}
			unpckd.setText(String.valueOf(unpacked));
			intToFlags();
			return;
		}
		else if("decalc".equals(e.getActionCommand())) {
			if(unpckd.getText().equals("")) {
				status.setText("Input not valid.");
				status.setForeground(Color.RED);
				return;
			}
			intToFlags();
			return;
		}
		else if("pack".equals(e.getActionCommand())) {
			flagsToValues();
			status.setText("Converted successfully.");
			status.setForeground(Color.GREEN);
			return;
		}
	}
	
	/*private static void createAndShowGUI() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		
		JFrame frame = new JFrame("ZEFlagCalc");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JComponent newContPane = new ZEFlagCalculator();
		newContPane.setOpaque(true);
		frame.setContentPane(newContPane);
		
		frame.pack();
		frame.setVisible(true);
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}*/

}
