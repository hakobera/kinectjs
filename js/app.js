new JavaAdapter(Packages.processing.core.PApplet, {
	
	setup: function() {
		this.size(640, 480);
		this.background(204);
		this.frameRate(10);
	},
	
	draw: function() {
		this.ellipse(10, 10, 20, 20);
	}
	
});
