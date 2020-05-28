package co.dp.registroaxa;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int LOCATION_REQUEST_CODE = 1;
    private GoogleMap mMap;

    private TextView direccion;
    private LinearLayout datos;
    private Button confirmar;
    String address;
    Geocoder geocoder;
    List<Address> addresses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        direccion = (TextView) findViewById(R.id.tvDireccion);
        datos = (LinearLayout) findViewById(R.id.constraint_datos);
        confirmar = (Button) findViewById(R.id.btnSelectM);

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",address);
                setResult(1, returnIntent);
                finish();

            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        LatLng inicial = new LatLng( 4.60,  -74.081);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(inicial, 6));
        geocoder = new Geocoder(this, Locale.getDefault());


        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            public void onMapClick(LatLng point) {
                // Drawing marker on the map
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(point);
                markerOptions.title(point.latitude + " : " + point.longitude);
                mMap.clear();
                mMap.animateCamera(CameraUpdateFactory.newLatLng(point));
                mMap.addMarker(markerOptions);

                datos.setVisibility(View.VISIBLE);

                try {
                    addresses = geocoder.getFromLocation(point.latitude, point.longitude, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                address = addresses.get(0).getAddressLine(0);
                direccion.setText(address);


            }
        });





    }



}
