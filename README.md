### Description

Phonegap plugin which allows the caller to customise a camera preview, including a custom button and overlaying a image.

### Using the plugin

- Add the plugin ID and version to the config.xml.

```
<gap:plugin name="com.overlay.plugins.camera" />
```

- Add custom images for the capture button and borders to your project. The image locations under the Phonegap www directory cannot currently be modified.

|         Path           |        Description        |
| -----------------------| --------------------------| 
| www/img/cameraoverlay/overlay.png | The top left border image |
| www/img/cameraoverlay/cancel_button.png | The default image for the cancel button |
| www/img/cameraoverlay/capture_button.png | The default image for the capture button |
| www/img/cameraoverlay/capture_button_pressed.png | The image for the capture button when tapped |

- Call the plugin from JavaScript. The API is similiar to the Phonegap provided API but currently lacking some of its features.

```js
navigator.overlayCamera.getPicture(filename, success, failure, [ options ]);
```

|         Parameter       |        Description        |
| ----------------------- | --------------------------| 
| filename | The filename to use for the captured image - the file will be stored in the local application cache. Note that the plugin only returns images in the JPG format. |
| success | A callback which will be executed on successful capture with the image data as the first parameter. |
| error | A callback which will be executed if the capture fails with an error message as the first parameter. |
| options | An optional object specifying capture options. |

### Capture options

|         Option       | Default Value |        Description        |
|----------------------|---------------|---------------------------| 
| quality | 100 | The compression level to use when saving the image - a value between 1 and 100, 100 meaning no reduction in quality. |
| targetWidth | -1 | The target width of the scaled image, -1 to disable scaling. |
| targetHeight | -1 | The target height of the scaled image, -1 to disable scaling.  |

### Image scaling

Setting both targetWidth and targetHeight to -1 will disable image scaling. Setting both values to positive integers will scale the image to that exact size which may result in distortion. If the aspect ratio should be respected, supply only the targetWidth or targetHeight and the other will be set based on the aspect ratio.

### Example

```js
navigator.overlayCamera.getPicture(filename, function success(imageData) {
    console.log(imageData);
}, function failure(error) {
    alert(error);
}, {
    quality: 80,
    targetWidth: 120,
    targetHeight: 120
});
```
