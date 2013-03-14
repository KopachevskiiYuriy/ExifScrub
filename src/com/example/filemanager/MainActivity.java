package com.example.filemanager;

import java.io.File;
import java.io.IOException;

import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.view.View;

public class MainActivity extends Activity {

	/*@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
*/

	protected static final String EXIF_MESSAGE = "com.example.filemanager.EXIF_ACTIVITY";

//	protected static final String EXIF_MESSAGE = null;
	
	ListView listView;
	private String CURRENT_FOLDER = Environment.getExternalStorageDirectory().getPath();
	
	@Override

	
	
	
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
    //this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    listView = (ListView) findViewById(R.id.list);

	File file = new File(CURRENT_FOLDER);
	//String[] files = file.list();	
	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,file.list());
	//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
	//ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,file.list());
	//adapter.insert(".",0);
	//adapter.insert("..",0);
	//String[] w = file.list();

	
	listView.setAdapter(adapter);
	listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	@Override
	public void onItemClick(AdapterView<?> lista, View arg1, int position,
				long arg3) {
		String filename = (String) lista.getItemAtPosition(position);
	    //String filenameArray[] = filename.split("\\.");
	    //String extension = filenameArray[filenameArray.length-1];
	    String extension = filename.substring(filename.lastIndexOf('.') + 1);
	    System.out.println(extension);
	    
	    if(extension.equals(filename)){	//filter for folders/files
	    	File file = new File(CURRENT_FOLDER + "/" + lista.getItemAtPosition(position));
	    	CURRENT_FOLDER = CURRENT_FOLDER + "/" + lista.getItemAtPosition(position);
	    	System.err.println(CURRENT_FOLDER.toString());
	    	//listView = (ListView) findViewById(R.id.list);
	    	listView.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, file.list()));
	        }
	    else{	//else file not folder
		    System.out.println(CURRENT_FOLDER + "/" + lista.getItemAtPosition(position));	        	
		    String exiffilename = CURRENT_FOLDER + "/" + lista.getItemAtPosition(position);
		    Intent intent = new Intent(getBaseContext(), ExifActivity.class);
		        
		    intent.putExtra(EXIF_MESSAGE, exiffilename); //Start exif checking with given filename
		    startActivity(intent);
	        }
		}

	    
	});
	

	}
		 
	
   
	public void onClick(View v) {
	      switch(v.getId()){
	       case R.id.Button_settings :
	   	   startActivity(new Intent(this, Preferences.class));
	   	   break;
	       case R.id.Button_back : 
	       jumpBack();
	       break;
		   default:
	    	   
	      }
	       
	       
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		 //Handles hardware back button

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			jumpBack();
		return true;
		} 
		else { 
			return super.onKeyDown(keyCode, event); 
			}
		
		}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
	    case R.id.Button_settings:
	    startActivity(new Intent(this, Preferences.class));
	    return true;
	    default:
	    return super.onOptionsItemSelected(item);
	}
	}
	
	public void jumpBack(){
		File current = new File(CURRENT_FOLDER);
		
		if(!current.equals(Environment.getExternalStorageDirectory())){//access outside of SDCARD not allowed
			File parent = current.getParentFile();
			CURRENT_FOLDER = parent.getAbsolutePath();

			listView.setAdapter(new ArrayAdapter<String>(
				MainActivity.this,
				android.R.layout.simple_list_item_1, parent.list()));
		}
	}
	
}
