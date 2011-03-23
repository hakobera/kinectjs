package com.github.hakobera.kinectjs;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import processing.core.PApplet;

/**
 * Processing アプレットを表示するためのラッパーです。
 * 
 * @author hakobera
 */
public class ProcessingWrapper extends JFrame {
	private static final long serialVersionUID = 4120092014486250461L;
	
	public ProcessingWrapper(PApplet applet) {
		super();
		applet.init();
		setLayout(new BorderLayout());
		add(applet, BorderLayout.CENTER);
	}
	
}
