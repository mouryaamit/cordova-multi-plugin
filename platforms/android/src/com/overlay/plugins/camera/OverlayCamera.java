package com.overlay.plugins.camera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.util.Base64;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;

import static com.overlay.plugins.camera.OverlayCameraActivity.ERROR_MESSAGE;
import static com.overlay.plugins.camera.OverlayCameraActivity.FILENAME;
import static com.overlay.plugins.camera.OverlayCameraActivity.IMAGE_URI;
import static com.overlay.plugins.camera.OverlayCameraActivity.QUALITY;
import static com.overlay.plugins.camera.OverlayCameraActivity.RESULT_ERROR;
import static com.overlay.plugins.camera.OverlayCameraActivity.TARGET_HEIGHT;
import static com.overlay.plugins.camera.OverlayCameraActivity.TARGET_WIDTH;


public class OverlayCamera extends CordovaPlugin {

    private CallbackContext callbackContext;

	@Override
    public boolean execute(String action, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
	    if (!hasRearFacingCamera()) {
	        callbackContext.error("No rear camera detected");
	        return false;
	    }
	    this.callbackContext = callbackContext;
	    Context context = cordova.getActivity().getApplicationContext();
	    Intent intent = new Intent(context, OverlayCameraActivity.class);
	    intent.putExtra(FILENAME, args.getString(0));
	    intent.putExtra(QUALITY, args.getInt(1));
	    intent.putExtra(TARGET_WIDTH, args.getInt(2));
	    intent.putExtra(TARGET_HEIGHT, args.getInt(3));
	    cordova.startActivityForResult(this, intent, 0);
        return true;
    }

	private boolean hasRearFacingCamera() {
	    Context context = cordova.getActivity().getApplicationContext();
	    return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
	}

	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
	    if (resultCode == Activity.RESULT_OK) {
//	        callbackContext.success(convertToBase64(intent.getExtras().getString(IMAGE_URI)));
	        convertToBase64(intent.getExtras().getString(IMAGE_URI));
	    } else if (resultCode == RESULT_ERROR) {
	        String errorMessage = intent.getExtras().getString(ERROR_MESSAGE);
	        if (errorMessage != null) {
	            callbackContext.error(errorMessage);
	        } else {
	            callbackContext.error("Failed to take picture");
	        }
	    }
    }
	private void convertToBase64(String filePath) {
		String imgStr = "";
		try {
			Uri _uri = Uri.parse(filePath);
			if (_uri != null && "content".equals(_uri.getScheme())) {
				Cursor cursor = cordova
						.getActivity()
						.getContentResolver()
						.query(_uri,
								new String[] { android.provider.MediaStore.Images.ImageColumns.DATA },
								null, null, null);
				cursor.moveToFirst();
				filePath = cursor.getString(0);
				cursor.close();
			} else {
				filePath = _uri.getPath();
			}
			File imageFile = new File(filePath);
			if (!imageFile.exists())
				callbackContext.error("Failed to take picture");

			byte[] bytes = new byte[(int) imageFile.length()];

			FileInputStream fileInputStream = new FileInputStream(imageFile);
			fileInputStream.read(bytes);

			imgStr = Base64.encodeToString(bytes, Base64.DEFAULT);
		} catch (Exception e) {
			callbackContext.error("Failed to take picture");
		}
		callbackContext.success(imgStr);
	}

}
