package com.example.hospitalapp3;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.hospitalapp3.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        float zoomLevel = 11.0f;
        // Add a marker in Sydney and move the camera
        LatLng jeanTalon = new LatLng(45.54643263273733, -73.60927758921656);
        LatLng notreDame = new LatLng(45.52516114989879, -73.56222534259108);
        LatLng dieu = new LatLng(45.514260555994866, -73.5784822877381);
        LatLng chum = new LatLng(45.5115335462393, -73.55760489525944);
        LatLng general = new LatLng(45.49690368377076, -73.58867817431313);
        LatLng jewish = new LatLng(45.497759313738264, -73.63007181287419);
        LatLng justine = new LatLng(45.50317239651404, -73.62518564328926);
        LatLng shriners = new LatLng(45.47250551062658, -73.6015142674077);
        mMap.addMarker(new MarkerOptions().position(jeanTalon).title("Marker in Montreal"));
        mMap.addMarker(new MarkerOptions().position(notreDame).title("Marker in Montreal"));
        mMap.addMarker(new MarkerOptions().position(dieu).title("Marker in Montreal"));
        mMap.addMarker(new MarkerOptions().position(chum).title("Marker in Montreal"));
        mMap.addMarker(new MarkerOptions().position(general).title("Marker in Montreal"));
        mMap.addMarker(new MarkerOptions().position(jewish).title("Marker in Montreal"));
        mMap.addMarker(new MarkerOptions().position(justine).title("Marker in Montreal"));
        mMap.addMarker(new MarkerOptions().position(shriners).title("Marker in Montreal"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(jeanTalon, zoomLevel));
    }
}