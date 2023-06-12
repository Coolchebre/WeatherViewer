package com.example.weatherviewer;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button button;
    ImageView imageView;
    TextView country_jpb, city_jpb, temp_jpb, time, latitude, longitude, humidity, sunrise, sunset, pressure, windSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editTextText);
        button = findViewById(R.id.button);
        country_jpb = findViewById(R.id.country);
        city_jpb = findViewById(R.id.city);
        temp_jpb = findViewById(R.id.textView5);
        time = findViewById(R.id.date);
        latitude = findViewById(R.id.Latitude);
        longitude = findViewById(R.id.Longitude);
        humidity = findViewById(R.id.Humidity);
        sunrise = findViewById(R.id.Sunrise);
        sunset = findViewById(R.id.Sunset);
        pressure = findViewById(R.id.Pressure);
        windSpeed = findViewById(R.id.Wind);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findWeather();
            }
        });
    }

        public void findWeather(){
            String city = editText.getText().toString();
            String url = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=96dc74d7e4c4b7cf1be47260470e9741";

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //calling api

                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        //find country
                        JSONObject object1 = jsonObject.getJSONObject("sys");
                        String country_find = object1.getString("country");
                        country_jpb.setText(country_find);

                        //find city
                        String city_find = jsonObject.getString("name");
                        city_jpb.setText(city_find);

                        //find date & time
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat std = new SimpleDateFormat("dd/MM/yyyy \nHH:mm:ss");
                        String date = std.format(calendar.getTime());
                        time.setText(date);

                        //find temperature
                        JSONObject object2 = jsonObject.getJSONObject("main");
                        String temp_find = object2.getString("temp");
                        temp_jpb.setText(temp_find+"°C");

                        //gind image icon
                        JSONArray jsonArray = jsonObject.getJSONArray("weather");
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                        String img = jsonObject1.getString("icon");
                        Picasso.get().load("https://openweathermap.org/img/wn/"+img+"@2x.png").into(imageView);

                        //find latitude
                        JSONObject object3 = jsonObject.getJSONObject("coord");
                        double lat_find = object3.getDouble("lat");
                        latitude.setText(lat_find+"°  N");

                        //find longitude
                        JSONObject object4 = jsonObject.getJSONObject("coord");
                        double long_find = object4.getDouble("lon");
                        longitude.setText(long_find+"°  E");

                        //find humidity
                        JSONObject object5 = jsonObject.getJSONObject("main");
                        int humidity_find = object5.getInt("humidity");
                        humidity.setText(humidity_find+"°  %");

                        //find sunrise
                        JSONObject object6 = jsonObject.getJSONObject("sys");
                        String sunrise_find = object6.getString("sunrise");
                        sunrise.setText(sunrise_find);

                        //find sunset
                        JSONObject object7 = jsonObject.getJSONObject("sys");
                        String sunset_find = object7.getString("sunrise");
                        sunset.setText(sunset_find);

                        //find pressure
                        JSONObject object8 = jsonObject.getJSONObject("main");
                        String pressure_find = object8.getString("pressure");
                        pressure.setText(pressure_find+"  hpa");

                        //find Wind Speed
                        JSONObject object9 = jsonObject.getJSONObject("wind");
                        String wind_find = object9.getString("speed");
                        windSpeed.setText(wind_find+"  km/h");

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this,error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(stringRequest);
        }

}