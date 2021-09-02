package space.abdilazov.weatherapp;

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

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button button;
    ImageView imageView;
    TextView country_tv, city_tv, temp_tv;

    TextView latitude,longitude,humidity,sunrise,sunset,pressure,wind_speed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.et_city);
        button = findViewById(R.id.button);
        country_tv = findViewById(R.id.country);
        city_tv = findViewById(R.id.city);
        temp_tv = findViewById(R.id.textView);

        latitude = findViewById(R.id.Latitude);
        longitude = findViewById(R.id.Longitude);
        humidity = findViewById(R.id.Humidity);
        sunrise = findViewById(R.id.Sunrise);
        sunset = findViewById(R.id.Sunset);
        pressure = findViewById(R.id.Pressure);
        wind_speed = findViewById(R.id.Wind_speed);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findWeather();
            }
        });

    }

    public void findWeather() {
        String city = editText.getText().toString();
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=462f445106adc1d21494341838c10019";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);

//                    find country
                    JSONObject jsonObject1 = jsonObject.getJSONObject("sys");
                    String country_find = jsonObject1.getString("country");
                    country_tv.setText(country_find);

//                    find city
                    String city_find = jsonObject.getString("name");
                    city_tv.setText(city_find);

//                    find temperature
                    JSONObject jsonObject2 = jsonObject.getJSONObject("main");
                    String temp_find = jsonObject2.getString("temp");
                    temp_tv.setText(temp_find+"%");

//                    gind image icon
                    JSONArray jsonArray = jsonObject.getJSONArray("Weather");
                    JSONObject jsonObject3 = jsonArray.getJSONObject(0);
                    String img = jsonObject3.getString("icon");
                    Picasso.get().load("http://openweathermap.org/img/wn/"+img+"02d@2x.png").into(imageView);

//                    find latitude
                    JSONObject jsonObject4 = jsonObject.getJSONObject("coord");
                    double lat_find = jsonObject4.getDouble("lat");
                    latitude.setText(lat_find+ "°  N" );


//                    find longitude
                    JSONObject jsonObject5 = jsonObject.getJSONObject("coord");
                    double long_find = jsonObject5.getDouble("lon");
                    longitude.setText(long_find+ "°  E" );

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Нет такого города", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);

    }
}