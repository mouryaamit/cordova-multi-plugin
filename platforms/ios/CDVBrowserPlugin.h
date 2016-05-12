
#import <Cordova/CDVPlugin.h>
#import <Cordova/CDVInvokedUrlCommand.h>
#import <Cordova/CDVScreenOrientationDelegate.h>


@class CDVBrowserPluginViewController;

@interface CDVBrowserPlugin : CDVPlugin {
}

@property (nonatomic, retain) CDVBrowserPluginViewController* inAppBrowserViewController;
@property (nonatomic, copy) NSString* callbackId;
@property (nonatomic, copy) NSRegularExpression *callbackIdPattern;

- (void)open:(CDVInvokedUrlCommand*)command;

@end

