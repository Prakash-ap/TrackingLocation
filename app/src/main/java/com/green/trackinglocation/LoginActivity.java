package com.green.trackinglocation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, LocationListener {
    Spinner spinner;
    Button login, logout, breakout;
    TextView employeetext;
    String[] employee_id;
    LocationManager locationManager;
    String date;
    String state,country,sublocality;
    int item;
    String[] employess;
    String locality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        spinner = findViewById(R.id.spinner);
        login = findViewById(R.id.login);
        logout = findViewById(R.id.logout);
        breakout = findViewById(R.id.break_out);
        spinner.setOnItemSelectedListener(this);
        employeetext = findViewById(R.id.candidate_id);
        login.setOnClickListener(this);
        logout.setOnClickListener(this);
        breakout.setOnClickListener(this);
        login.setVisibility(View.VISIBLE);
        logout.setVisibility(View.GONE);
        breakout.setVisibility(View.GONE);

        employess = getResources().getStringArray(R.array.Candidates);
        employee_id = getResources().getStringArray(R.array.Candidates_id);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, employess);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        item = spinner.getSelectedItemPosition();
        //employeetext.setText(Arrays.toString(employee_id));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, employee_id);
        employeetext.setText(adapter.getItem(item));

        //   Toast.makeText(this, "item position:"+item, Toast.LENGTH_SHORT).show();


        //employeetext.setText(employee_id.length);


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:


                if(item==0){
                    Toast.makeText(this, "Select your Name", Toast.LENGTH_SHORT).show();
                }else{
                    login.setVisibility(View.GONE);
                    logout.setVisibility(View.VISIBLE);
                    breakout.setVisibility(View.VISIBLE);
                getLocation();
                }
                break;
            case R.id.logout:

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, employess);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, employee_id);
                employeetext.setText(adapter1.getItem(item));

                if(item==0){
                    Toast.makeText(this, "Select your Name", Toast.LENGTH_SHORT).show();
                }else {


                    login.setVisibility(View.VISIBLE);
                    logout.setVisibility(View.GONE);
                    breakout.setVisibility(View.GONE);
                    getLocation();
                }
                break;

            case R.id.break_out:

                ArrayAdapter<String> adapterbreak = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, employess);
                adapterbreak.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapterbreak);
                ArrayAdapter<String> adapterbreak1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, employee_id);
                employeetext.setText(adapterbreak1.getItem(item));

                if(item==0){
                    Toast.makeText(this, "Select your Name", Toast.LENGTH_SHORT).show();
                }else {

                    logout.setVisibility(View.GONE);
                    login.setVisibility(View.VISIBLE);
                    breakout.setVisibility(View.GONE);
                    getLocation();
                }
                break;

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getLocation() {

        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 2, this);
            Calendar c=Calendar.getInstance();
            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
            date=df.format(c.getTime());
          //  showDialog();


        }
        catch (SecurityException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onLocationChanged(Location location) {

        LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());
        String provider=locationManager.getBestProvider(new Criteria(),true);
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            return;
        }

        Location location1=locationManager.getLastKnownLocation(provider);
        List<String> providerList=locationManager.getAllProviders();

        if(null !=location1 && null != providerList && providerList.size()>0){
            double latitude=location1.getLatitude();
            double longitude=location1.getLongitude();

            Geocoder geocoder=new Geocoder(getApplicationContext(), Locale.getDefault());

            try{
                List<Address> listAddress=geocoder.getFromLocation(latitude,longitude,1);

                if(null!=listAddress && listAddress.size()>0){
                    state=listAddress.get(0).getAdminArea();
                    country=listAddress.get(0).getCountryName();
                     sublocality=listAddress.get(0).getSubLocality();

                     locality=sublocality + state + country;
                     showDialog();

                 //   Toast.makeText(this, " "+ latLng + "," +sublocality + "," +state + "," + country+ "," +date, Toast.LENGTH_SHORT).show();
                  //  markerOptions.title(" "+ latLng + "," +sublocality + "," +state + "," + country );

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }




    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {
        Toast.makeText(LoginActivity.this, "No Network Available", Toast.LENGTH_SHORT).show();

    }

    public void showDialog(){
        final AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
       // alertDialog.setMessage(" " +sublocality + "," +state + "," + country+ "," +date);
        alertDialog.setMessage(" " + locality + "\n" +date);
        alertDialog.setTitle("Location Info");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               dialogInterface.cancel();
            }
        });
        AlertDialog dialog=alertDialog.create();
        dialog.show();


    }
}
