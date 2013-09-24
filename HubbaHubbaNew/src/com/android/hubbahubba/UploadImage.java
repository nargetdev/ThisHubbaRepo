package com.android.hubbahubba;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class UploadImage extends Activity{

	//declare Strings to pass through to AddSpot activity
	Button cancelButton, locateOnMapButton, continueButton;
    
    //for take photo
	String mCurrentPhotoPath;
	ImageView mImage;
	ImageButton takePhotoButton, uploadPhotoButton;
	String mImagePath;
	Uri imageViewUri;
	Uri mSelectedImage = Uri.parse("android.resource://com.segf4ult.test/" + R.drawable.ic_launcher);
	Bitmap spotImage;
	Bitmap mImageBitmap;
	ImageView mImageView;
	private Uri fileUri;
    private static final int SELECT_PHOTO = 1;
    private static final int TAKE_PHOTO = 2;
    public static final int MEDIA_TYPE_IMAGE = 3;
	public static final int MEDIA_TYPE_VIDEO = 4;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
	private static final String JPEG_FILE_SUFFIX = "Hubba_Hubba";
	private static final String JPEG_FILE_PREFIX = "Hubba_Hubba";
	String rider;
	String user;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_location_layout);
		
		// FOR TOAST
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_LONG;
		String text;
		
		//initialize all needed objects 
		cancelButton = (Button) findViewById(R.id.cancelButton);
		continueButton = (Button) findViewById(R.id.continueButton);
		takePhotoButton = (ImageButton) findViewById(R.id.takePhotoButton);
		uploadPhotoButton = (ImageButton) findViewById(R.id.uploadPhotoButton);
		
		uploadPhotoButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				uploadPhotoButton.setPressed(true);
				
				Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
				photoPickerIntent.setType("image/*");
				startActivityForResult(photoPickerIntent, SELECT_PHOTO);
			}
		});
		
		takePhotoButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				//TODO take picture
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, TAKE_PHOTO);
				
				// SAVE TO FILE
				File f = null;
				try {
					f = createImageFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(f != null){
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
				}
				else{
					//TODO output error message
				}
			}
		});
		
		// login Button takes you to the map view (ROB: YOU WILL NEED TO CHANGE THIS BACK TO MAPVIEW, not MainActivity)
		cancelButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(UploadImage.this, ActionBarActivity.class);
				setResult(Activity.RESULT_CANCELED, intent);
				finish();
			}
		});

		continueButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Context context = getApplicationContext();
				int duration = Toast.LENGTH_LONG;
				CharSequence text;

				Intent intent = new Intent(UploadImage.this, AddSpot.class);
				intent.putExtra("mSelectedImage", mSelectedImage.toString());
				
				text = mSelectedImage.toString();
				Toast toaster = Toast.makeText(context, text, duration);
				toaster.show();
				
				startActivity(intent);
			}
		});
	}
	
	private void dispatchTakePictureIntent(final int actionCode) throws IOException {    
	 // create Intent to take a picture and return control to the calling application
	    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

	    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
	    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
	    intent.setType("image/*");
	    
	    // start the image capture Intent
	    startActivityForResult(intent, actionCode);
	    
	    File f = createImageFile();
	    //intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
	}
	
	/** Create a file Uri for saving an image or video */
	private static Uri getOutputMediaFileUri(int type){
	      return Uri.fromFile(getOutputMediaFile(type));
	}
	
	/** Create a File for saving an image or video */
	@SuppressLint("SimpleDateFormat")
	private static File getOutputMediaFile(int type){
	    // To be safe, you should check that the SDCard is mounted
	    // using Environment.getExternalStorageState() before doing this.

	    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
	              Environment.DIRECTORY_PICTURES), "MyCameraApp");
	    // This location works best if you want the created images to be shared
	    // between applications and persist after your app has been uninstalled.

	    // Create the storage directory if it does not exist
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	            Log.d("MyCameraApp", "failed to create directory");
	            return null;
	        }
	    }

	    // Create a media file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    File mediaFile;
	    if (type == MEDIA_TYPE_IMAGE){
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "HUBBA_HUBBA_IMG_"+ timeStamp + ".jpg");
	    } else if(type == MEDIA_TYPE_VIDEO) {
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "HUBBA_HUBBA_VID_"+ timeStamp + ".mp4");
	    } else {
	        return null;
	    }

	    return mediaFile;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    switch(requestCode) {
		    case SELECT_PHOTO:
		        if(resultCode == RESULT_OK){  
		            mSelectedImage = data.getData();
		            
		            Toast.makeText(this, "IMG From:\n" +
		                     data.getData(), Toast.LENGTH_LONG).show();
		            try {
						spotImage = decodeUri(mSelectedImage);
						uploadPhotoButton.setImageBitmap(spotImage);
						//uploadPhotoButton.setBackgroundResource(R.color.abs__background_holo_light);
						
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		            takePhotoButton.setClickable(false);
		        }
		        break;
		        
		    //case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE:
		    case TAKE_PHOTO:
		        if (resultCode == RESULT_OK) {
		            // Image captured and saved to fileUri specified in the Intent
		            Toast.makeText(this, "Image saved to:\n" +
		                     data.getData(), Toast.LENGTH_LONG).show();
		            mSelectedImage = data.getData();
		            //TODO THE BUG IS SOMEWHERE IN THE TRY CATCH BLOCK
		            
		            try {
						spotImage = decodeUri(mSelectedImage);
						takePhotoButton.setImageBitmap(spotImage);
						Toast.makeText(this, "GOT EM" +
			                     data.getData(), Toast.LENGTH_LONG).show();
						//uploadPhotoButton.setBackgroundResource(R.color.abs__background_holo_light);
					} catch (FileNotFoundException e) {
						Toast.makeText(this, "FILE NOT FOUND FUCKER", Toast.LENGTH_LONG).show();
						e.printStackTrace();
					}
		            uploadPhotoButton.setClickable(false);
		            
		        } else if (resultCode == RESULT_CANCELED) {
		        	Toast.makeText(this, "Image cancelled\n you suck", Toast.LENGTH_LONG).show();
		        } else {
		        	Toast.makeText(this, "Image FAILED\n you REALLY suck", Toast.LENGTH_LONG).show();
		        }
		        
	            break;
	            
		    case CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE:
		        if (resultCode == RESULT_OK) {
		            // Video captured and saved to fileUri specified in the Intent
		            Toast.makeText(this, "Video saved to:\n" +
		                     data.getData(), Toast.LENGTH_LONG).show();
		        } else if (resultCode == RESULT_CANCELED) {
		            // User cancelled the video capture
		        } else {
		            // Video capture failed, advise user
		        }
		        break;
	    }
	}
	
	private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {

        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 140;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE
               || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);

    }
	
	@SuppressLint("SimpleDateFormat")
	private File createImageFile() throws IOException {
	    // Create an image file name
	    String timeStamp = 
	        new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
	    File image = File.createTempFile(
	        imageFileName, 
	        JPEG_FILE_SUFFIX//, 
	        //getAlbumDir()
	    );
	    mCurrentPhotoPath = image.getAbsolutePath();
	    return image;
	}
}