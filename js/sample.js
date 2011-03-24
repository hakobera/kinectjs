var processing = require('processing');
var kinect = require('kinect');

processing.run({
	setup: function() {
        this.size(kinect.width(), kinect.height());
        this.frameRate(5);
        kinect.init({
            processing: this,
            enableDepth: true,
            enableIR: false,
            enableRGB: false,
            processDepthImage: true
        });
	},
	
	draw: function() {
        var depthImg = kinect.getDepthImage();
        this.image(depthImg, 0, 0);
	},
    
    stop: function() {
    	kinect.stop();
    }
});