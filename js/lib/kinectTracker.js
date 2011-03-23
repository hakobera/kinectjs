/**
 * 深度しきい値最小
 *
 * @property
 * @type {number}
 */
var _thresholdMin = 400;

/**
 * 深度しきい値最大
 *
 * @property
 * @type {number}
 */
var _thresholdMax = 500;

/**
 * 追跡座標
 *
 * @property
 * @type {PVector}
 */
var _location;

/**
 * 補完後追跡座標
 *
 * @property
 * @type {PVector}
 */
var _lerpedLocation;

/**
 * 表示用イメージ
 *
 * @private
 * @type {PImage}
 */
var _display;

/**
 * @private
 * @type {PApplet}
 */
var _processing;

/**
 * @private
 * @type {Kinect}
 */
var _kinect;

/**
 * 取得できる画像の横解像度(px)
 *
 * @private
 * @type {number}
 */
var _width;

/**
 * 取得できる画像の縦解像度(px)
 *
 * @private
 * @type {number}
 */
var _height;

/**
 * 深度情報配列
 *
 * @private
 * @type {Array.<number>}
 */
var _depth;

/**
 * 引数がしきい値内に収まっているかどうかを判定して返します。
 *
 * @param {number} v 判定する値
 * @return {boolean} しきい値内にある場合、true
 * @private
 */
function isInRange(v) {
    return _thresholdMin < v && v < _thresholdMax;
}

/*
 * import
 */
var PVector = Packages.processing.core.PVector;

/**
 * @module KinectTracker
 */
exports = {

    /**
     * PApplet， Kinect オブジェクトと関連付け。
     *
     * @param {PApplet} processing procesing アプレット
     * @param {Kinect} kinect Kinect 接続オブジェクト
     */
    bind: function(processing, kinect) {
        _processing = processing;
        _kinect = kinect;
        _width = kinect.width() | 0;
        _height = kinect.height() | 0;
        _display = _processing.createImage(_width, _height, Packages.processing.core.PConstants.RGB);
        _location = new PVector(0, 0);
        _lerpedLocation = new PVector(0, 0);
    },

    /**
     * しきい値内の手の位置をトラッキングします。
     */
    track: function(render) {
        var img = _kinect.getDepthImage(),
            sumX = 0,
            sumY = 0,
            count = 0,
            detectColor = _processing.color(150, 50, 50),
            x, y, yOffset, offset, rawDepth, pt, src, dest, c;

        _depth = _kinect.getRawDepth();

        if (render) {
	        _display.loadPixels();
	        src = img.pixels;
	        dest = _display.pixels;
        }
	    for (y = 0; y < _height; y+=1) {
            yOffset = y * _width;
            for (x = 0; x < _width; x+=1) {
                offset = (_width - x - 1) + yOffset | 0; // 左右反転を元に戻す
                rawDepth = _depth[offset];
                pt = x + yOffset;
                if (isInRange(rawDepth)) {
                    sumX += x;
                    sumY += y;
                    count += 1;
                }
                
                if (render) {
                    if (isInRange(rawDepth)) {
                        c = detectColor;
                    } else {
                        c = src[offset];
                    }
                	dest[pt] = c;
                }
            }
        }
	    if (render) {
	        _display.updatePixels();
	        _processing.image(_display, 0, 0);
	    }
	    
        if (count !== 0) {
            _location.x = sumX / count;
            _location.y = sumY / count;
        }

        _lerpedLocation.x = _processing.lerp(_lerpedLocation.x, _location.x, 0.3);
        _lerpedLocation.y = _processing.lerp(_lerpedLocation.y, _location.y, 0.3);
    },

    /**
     * 補完後のトラッキング座標を返します。
     *
     * @return {PVector} 補完後のトラッキング座標
     */
    getPos: function() {
        return _lerpedLocation;
    },

    /**
     * しきい値を設定します。
     * 
     * @param {number} t しきい値
     */
    setThreshold: function(t) {
        _thresholdMin = t;
    },

    /**
     * 設定されているしきい値を返します。
     *
     * @return {number} 設定されてるしきい値
     */
    getThreshold: function() {
        return _thresholdMin;
    }
    
};
