exports.run = function(obj) {
	return new JavaAdapter(Packages.processing.core.PApplet, obj);
}