var overlayCamera = {
    getPicture: function(filename, success, failure, options) {
        options = options || {};
        var quality = options.quality || 100;
        var targetWidth = options.targetWidth || -1;
        var targetHeight = options.targetHeight || -1;
        cordova.exec(success, failure, "OverlayCamera", "takePicture", [filename, quality, targetWidth, targetHeight]);
    }
};

module.exports = overlayCamera;
