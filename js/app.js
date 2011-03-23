var processing = require('processing');
var kinect = require('kinect');
var tracker = require('kinectTracker');

var trackPoint;

processing.run({
	
	setup: function() {
        this.size(kinect.width(), kinect.height());
        this.background(204);
        this.frameRate(5);

        kinect.init({
            processing: this,
            enableDepth: true,
            enableIR: false,
            enableRGB: true,
            processDepthImage: false
        });

        tracker.bind(this, kinect);
	},
	
	draw: function() {
        tracker.track();
        trackPoint = tracker.getPos();
		this.ellipse(trackPoint.x, trackPoint.y, 10, 10);
	},

    keyPressed: function() {
        var keyCode = this.keyCode;
        if (keyCode === 81) {
            kinect.stop();
            this.stop();
        }
    }

});