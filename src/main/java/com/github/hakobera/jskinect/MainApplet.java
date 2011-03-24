package com.github.hakobera.jskinect;

import org.openkinect.processing.Kinect;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import processing.net.Client;

public class MainApplet extends PApplet {
	private static final long serialVersionUID = 1L;

	private Kinect kinect;

	private KinectTracker tracker;
	
	private Client client;
	
	private long count = 0;

	@Override
	public void setup() {
		size(640, 480);
		background(204);
		stroke(0);
		frameRate(5);

		kinect = new Kinect(this);
		tracker = new KinectTracker(this, kinect);
		client = new Client(this, "127.0.0.1", 9999);
	}

	@Override
	public void draw() {
		tracker.track();
		tracker.display();
		
		PVector v2 = tracker.getLerpedPos();
		ellipse(v2.x, v2.y, 20, 20);
		if (client.active()) {
			client.write(v2.x + "," + v2.y);
		}
	}

	@Override
	public void stop() {
		tracker.quit();
		super.stop();
	}

}
