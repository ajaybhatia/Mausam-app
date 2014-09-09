package com.ajaybhatia.mausam;

import java.io.IOException;
import java.text.DecimalFormat;

import net.aksingh.java.api.owm.CurrentWeatherData;
import net.aksingh.java.api.owm.CurrentWeatherData.Main;
import net.aksingh.java.api.owm.OpenWeatherMap;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends Activity {
	private String city;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);

		Intent intent = getIntent();
		city = intent.getStringExtra(MainActivity.CITY);
		
		WeatherTask task = new WeatherTask();
		task.execute(new String[]{city});
	}

	private class WeatherTask extends AsyncTask<String, Void, Main> {

		protected Main doInBackground(String... params) {
			Main main = null;
			
			try {
				OpenWeatherMap owm = new OpenWeatherMap("");
				CurrentWeatherData cwd = owm.currentWeatherByCityName(params[0]);
				main = cwd.getMainData_Object();
			} catch (IOException | JSONException ex) {}

			return main;
		}
		
		@Override
		protected void onPostExecute(Main result) {
			super.onPostExecute(result);
			
			TextView tvCity = (TextView)findViewById(R.id.tvCityResult);
			TextView tvTemp = (TextView)findViewById(R.id.tvTempResult);
			
			tvCity.setText(city);
			tvTemp.setText(String.valueOf(toCelcius(result.getMaxTemperature())) + (char)0x00B0 + "C");
		}
	}
	
	private float toCelcius(float fahrenheit) {
		DecimalFormat df = new DecimalFormat("0.00");
		return Float.parseFloat(df.format((fahrenheit - 32) * 5.0f/9.0f));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.second, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
