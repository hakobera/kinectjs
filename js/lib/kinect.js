/**
 * Kinect 操作オブジェクト
 * @property
 */
var kinect;

/**
 * 初期設定。
 */
var defaultConf = {
	enableDepth: true,
	enableIR: false,
	enableRGB: false,
	processDepthImage: true
};

/**
 * Kinect から入力される画像データの横の解像度(px)
 *
 * @constant
 */
var WIDTH = 640 | 0;

/**
* Kinect から入力される画像データの縦の解像度(px)
*
* @constant
*/
var HEIGHT = 480 | 0;

/**
 * Kinect 操作オブジェクトを Processing と接続します。
 * @public
 */
exports = {
    init: function(conf) {
        var processing = conf.processing,
                enableDepth = conf.enableDepth,
                enableIR = conf.enableIR,
                enableRGB = conf.enableRGB,
                processDepthImage = conf.processDepthImage;

        kinect = new org.openkinect.processing.Kinect(processing);
        kinect.start();
        kinect.enableDepth(enableDepth);
        kinect.enableIR(enableIR);
        kinect.enableRGB(enableRGB);
        kinect.processDepthImage(processDepthImage);
    },

    start: function() {
        kinect.start;
    },

    stop: function() {
        kinect.quit();
    },

    getDepathFPS: function() {
        return kinect.getDepthFPS();
    },

    getDepthImage: function() {
        return kinect.getDepthImage();
    },

    getRawDepth: function() {
        return kinect.getRawDepth();
    },

    getVideoFPS: function() {
        return kinect.getVideoFPS();
    },

    getVideoImage: function() {
        return kinect.getVideoImage();
    },

    tilt: function(angle) {
        kinect.tile(angle);
    },

    width: function() {
        return WIDTH;
    },

    height: function() {
        return HEIGHT;
    }
};