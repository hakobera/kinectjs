package com.github.hakobera.jskinect;

import org.openkinect.processing.Kinect;

import processing.core.PApplet;
import processing.core.PVector;

public class MainApplet extends PApplet {
	private static final long serialVersionUID = 1L;

	private Kinect kinect;

	private KinectTracker tracker;
	
	private long count = 0;

	@Override
	public void setup() {
		size(640, 480);
		background(204);
		stroke(0);
		frameRate(5);

		kinect = new Kinect(this);
		tracker = new KinectTracker(this, kinect);
	}

	@Override
	public void draw() {
		System.out.println("update " + ++count);
		tracker.track();
		tracker.display();
		
		PVector v2 = tracker.getLerpedPos();
		ellipse(v2.x, v2.y, 20, 20);
	}

	@Override
	public void stop() {
		tracker.quit();
		super.stop();
	}

}
