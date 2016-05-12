/**
 * Created by Amit Mourya on 11/05/16.
 */
var browserPlugin = {
    open: function(url) {
        cordova.exec(null, null, "BrowserPlugin", "open", [url]);
    }
};

module.exports = browserPlugin;
