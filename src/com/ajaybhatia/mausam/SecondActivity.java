package com.ajaybhatia.mausam;

import java.io.IOException;
import java.text.DecimalFormat;

import net.aksingh.java.api.owm.CurrentWeatherData;
import net.aksingh.java.api.owm.OpenWeatherMap;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

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

	private class WeatherTask extends AsyncTask<String, Void, CurrentWeatherData> {

		protected CurrentWeatherData doInBackground(String... params) {
			CurrentWeatherData cwd = null;
			
			try {
				OpenWeatherMap owm = new OpenWeatherMap("");
				cwd = owm.currentWeatherByCityName(params[0]);
			} catch (IOException | JSONException ex) {}

			return cwd;
		}
		
		@Override
		protected void onPostExecute(CurrentWeatherData cwd) {
			super.onPostExecute(cwd);
			
			TextView tvCity = (TextView)findViewById(R.id.tvCityResult);
			TextView tvTemp = (TextView)findViewById(R.id.tvTempResult);
			
			tvCity.setText(cwd.getCityName());
			tvTemp.setText(String.valueOf(toCelcius(cwd.getMainData_Object().getTemperature())) + (char)0x00B0 + "C");
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
