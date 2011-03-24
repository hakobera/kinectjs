/**
 * Processing の Net ライブラリのラッパーモジュール
 */
exports.connect = function(processing, hostName, port) {
	var client = new Packages.processing.net.Client(processing, hostName, port);
	return client;
};