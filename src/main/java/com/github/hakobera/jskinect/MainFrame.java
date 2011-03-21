package com.github.hakobera.jskinect;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;


public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private MainApplet applet;
	
	public MainFrame() {
		super();
		init();
		bindEvent();
	}
	
	private void init() {
		applet = new MainApplet();
		applet.init();
		
		setLayout(new BorderLayout());
		add(applet, BorderLayout.CENTER);
	}
	
	private void bindEvent() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				System.exit(0);
			}
		
		});
	}
	
	public static void main(String[] args) {
		try {
			JFrame frame = new MainFrame();
			frame.pack();
			frame.setLocation(100, 100);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
