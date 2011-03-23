exports.log = function() {
	var length = arguments.length,
		sysout = java.lang.System.out,
		v, i, type;
	
	for (i = 0; i < length; ++i) {
		v = arguments[i];
		typename = typeof v;
		if (i !== 0) {
			java.lang.System.out.print(' ');
		}
		if (typename === 'object') {
			v = JSON.stringify(v);
		}
		sysout.print(v);
	}
	sysout.println();
}