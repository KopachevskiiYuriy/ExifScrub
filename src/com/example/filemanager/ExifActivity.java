package com.example.filemanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ExifActivity extends Activity {
  ListView myListView;
  String filename;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exif);
	    this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		myListView = (ListView)findViewById(R.id.list);
		
		Intent intent = getIntent();
		//final String filename = Environment.getExternalStorageDirectory().getPath() + "/DCIM/100MEDIA/IMAG0711.jpg";
		filename = intent.getStringExtra(MainActivity.EXIF_MESSAGE);
		 
        try {
        
     	   ExifInterface exif = new ExifInterface(filename);
     	   ShowExif(exif, filename);
     	  } catch (IOException e) {
     	   // TODO Auto-generated catch block
     	   e.printStackTrace();
     	   Toast.makeText(getBaseContext(), "Error opening exif", Toast.LENGTH_LONG).show();
     	  }
        
        final Button scrubber = (Button) findViewById(R.id.button_scrubber);
        scrubber.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              	 scrubber.setEnabled(false);
              	 try {
					ScrubExif(filename);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.err.println("Error ScrubExif(filename)");
				}
              	    
            }
        });
        
  
	}

	public void onClick(View v) {
	      switch(v.getId()){
	       case R.id.Button_back : 
	       finish();
	       break;
	       case R.id.Button_settings :
	   	   startActivity(new Intent(this, Preferences.class));
	   	   break;
	       case R.id.button_scrubber :
	           	final Button scrubber = (Button) findViewById(R.id.button_scrubber);
            	scrubber.setEnabled(false);//TODO check this
            	scrubber.setBackgroundResource(R.drawable.blank);
            	 try {
					ScrubExif(filename);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.err.println("Error ScrubExif(filename)");
					}
            	 break;
		   default:
	    	   
	      }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_exif, menu);
		return true;
	}

    private void ShowExif(ExifInterface exif, String filename)
    {
    
    
    ArrayList<String> listings = new ArrayList<String>();
    
//     myTextView.setTextColor(Color.BLUE);
//     String myAttribute="Exif information ---\n";
     listings.add(filename);
     listings.add(getTagString(ExifInterface.TAG_DATETIME, exif));
     listings.add(getTagString(ExifInterface.TAG_FLASH, exif));
     listings.add(getTagString(ExifInterface.TAG_GPS_LATITUDE, exif));
     listings.add(getTagString(ExifInterface.TAG_GPS_LATITUDE_REF, exif));
     listings.add(getTagString(ExifInterface.TAG_GPS_LONGITUDE, exif));
     listings.add(getTagString(ExifInterface.TAG_GPS_LONGITUDE_REF, exif));
     listings.add(getTagString(ExifInterface.TAG_IMAGE_LENGTH, exif));
     listings.add(getTagString(ExifInterface.TAG_IMAGE_WIDTH, exif));
     listings.add(getTagString(ExifInterface.TAG_MAKE, exif));
     listings.add(getTagString(ExifInterface.TAG_MODEL, exif));
     listings.add(getTagString(ExifInterface.TAG_WHITE_BALANCE, exif));
     myListView.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, listings));
//   myListView.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_2, listings));
    }
    
    private String getTagString(String tag, ExifInterface exif)
    {
     return(tag + " : " + exif.getAttribute(tag) + "\n");
    }
	
    private void ScrubExif(String filename) throws IOException{
    	File working = new File(filename);
    	String newfilename = filename.replace(".jpg",
                "_SCRUBBED.jpg" );
    	File copy = new File(newfilename);
    	//String prefix = filename.substring(filename.lastIndexOf('.'));
//    	File temp = File.createTempFile(prefix + "_SCRUBBED", ".jpg");
    	working = copy(working, copy);
     	 ExifInterface exif = new ExifInterface(working.getAbsolutePath());
    	//exif.setAttribute(ExifInterface.TAG_DATETIME, "");
    	exif.setAttribute(ExifInterface.TAG_MAKE, "");
    	exif.setAttribute(ExifInterface.TAG_MODEL, "");
    	exif.setAttribute(ExifInterface.TAG_DATETIME, "");
    	exif.saveAttributes();
    	ShowExif(exif,newfilename);
    	
		}
    public File copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();

        return dst;
    }
    }

