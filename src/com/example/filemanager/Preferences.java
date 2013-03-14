package com.example.filemanager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class Preferences extends PreferenceActivity {
	@Override
	protected void onCreate (Bundle savedInstanceState){
		String overwrite = "overWritePref";
		boolean defaultOverwrite = true;
		
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.preferences);
		PreferenceManager.setDefaultValues(this, overwrite, MODE_PRIVATE, R.xml.preferences, false);
	//	Preference customPref = (Preference) findPreference("customPref");
		//SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		//prefs.getBoolean("overWritePref", false);
		
	/*	SharedPreferences shared = getSharedPreferences(overwrite, MODE_PRIVATE);
		SharedPreferences.Editor editor = shared.edit();
		editor.putBoolean(overwrite, defaultOverwrite);
		editor.commit();
	*/	
	}
	
	public boolean getOverwritePref (){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		return prefs.getBoolean("overWritePref", false);
	}
	    
}

