/**
 * Created by Amit Mourya on 11/05/16.
 */
var browserPlugin = {
    open: function(url, success, failure) {
        cordova.exec(success, failure, "BrowserPlugin", "open", [url]);
    }
};

module.exports = browserPlugin;
