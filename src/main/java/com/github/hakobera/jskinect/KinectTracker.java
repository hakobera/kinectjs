package com.github.hakobera.jskinect;

import org.openkinect.processing.Kinect;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PVector;

public class KinectTracker {
	
	private static final int KW = 640;
	private static final int KH = 480;
	
	private PApplet processing;
	private Kinect kinect;
	
	private int threshold = 600;

	/**
	 * トラッカーの位置
	 */
	private PVector loc;

	/**
	 * 位置補完後のトラッカーの位置
	 */
	private PVector lerpedLoc;
	
	private int[] depth;
	
	private PImage display;
	 
	/**
	 * コンストラクタ
	 * 
	 * @param processing processing 
	 * @param kinect Kinect 操作オブジェクト
	 */
	public KinectTracker(PApplet processing, Kinect kinect) {
		this.processing = processing;
	    this.kinect = kinect;
	    
		kinect.start();
	    kinect.enableRGB(false);
	    kinect.enableDepth(true);
	    kinect.processDepthImage(true);

	    display = processing.createImage(KW, KH, PConstants.RGB);

	    loc = new PVector(0, 0);
	    lerpedLoc = new PVector(0, 0);
	}
	 
	public void track() {
		depth = kinect.getRawDepth();

	    if (depth == null) {
	      return;
	    }

	    float sumX = 0;
	    float sumY = 0;
	    float count = 0;

	    for (int x = 0; x < KW; ++x) {
	    	for (int y = 0; y < KH; y++) {
	    		int offset = KW - x - 1 + y * KW;
	    		int rawDepth = depth[offset];

	    		if (rawDepth < threshold) {
	    			sumX += x;
	    			sumY += y;
	    			count++;
	    		}
	    	}
	    }

	    if (count != 0) {
	    	loc = new PVector(sumX/count, sumY/count);
	    }

	    lerpedLoc.x = PApplet.lerp(lerpedLoc.x, loc.x, 0.3f);
	    lerpedLoc.y = PApplet.lerp(lerpedLoc.y, loc.y, 0.3f);
	}

	public PVector getLerpedPos() {
		return lerpedLoc;
	}

	public PVector getPos() {
		return loc;
	}
	  
	public void display() {
		PImage img = kinect.getDepthImage();
	    if (depth == null || img == null) {
	    	return;
	    }
	    
	    display.loadPixels();
	    for (int x = 0; x < KW; ++x) {
	    	for (int y = 0; y < KH; ++y) {
	    		int offset = KW - x - 1 + y * KW;
	    		int rawDepth = depth[offset];
	        
	    		int pix = x + y * display.width;
	    		if (rawDepth < threshold) {
	    			display.pixels[pix] = processing.color(150, 50, 50);
	    		} else {
	    			display.pixels[pix] = img.pixels[offset];
	    		}
	    	}
	    }
	    display.updatePixels();
	    
	    processing.image(display, 0, 0);
	}
	  
	public void quit() {
		kinect.quit();
	}
	  
	public void setThreshold(int t) {
		threshold = t;
	}
	  
	public int getThreshold() {
		return threshold;
	}
	  
}