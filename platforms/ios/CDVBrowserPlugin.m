#import "CDVBrowserPlugin.h"
#import <Cordova/CDVPluginResult.h>
#import <Cordova/CDVUserAgentUtil.h>

#pragma mark CDVBrowserPlugin

@interface CDVBrowserPlugin () {
    NSInteger _previousStatusBarStyle;
}
@end

@implementation CDVBrowserPlugin

- (void)pluginInitialize
{
    _previousStatusBarStyle = -1;
    _callbackIdPattern = nil;
}

- (void)onReset
{
    [self close:nil];
}


- (void)open:(CDVInvokedUrlCommand*)command
{
    CDVPluginResult* pluginResult;

    NSString* url = [command argumentAtIndex:0];

    self.callbackId = command.callbackId;

    if (url != nil) {
        [[UIApplication sharedApplication] openURL:[NSURL URLWithString:url]];
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    } else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"incorrect number of arguments"];
    }

    [pluginResult setKeepCallback:[NSNumber numberWithBool:YES]];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

@end