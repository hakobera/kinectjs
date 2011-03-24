var processing = require('processing');
var kinect = require('kinect');
var tracker = require('kinectTracker');
var netClient = require('netClient');

var trackPoint,
	render = false,
	client;

processing.run({
	
	setup: function() {
        this.size(kinect.width(), kinect.height());
        this.background(204);
        this.frameRate(5);

        kinect.init({
            processing: this,
            enableDepth: true,
            enableIR: false,
            enableRGB: false,
            processDepthImage: true
        });
        tracker.bind(this, kinect);
        client = netClient.connect(this, "127.0.0.1", 9999);
        console.log('setup finished.')
	},
	
	draw: function() {
        tracker.track(render);
        trackPoint = tracker.getPos();
		this.ellipse(trackPoint.x, trackPoint.y, 10, 10);
		
		if (client.active) {
			client.write(trackPoint.x + "," + trackPoint.y);
		}
	},

    keyPressed: function() {
        var keyCode = this.keyCode;
        console.log(keyCode);
        if (keyCode === 81) {
            kinect.stop();
            this.stop();
        } else if (keyCode === 65) {
        	render = !render;
        }
    },
    
    stop: function() {
    	kinect.stop();
    }

});