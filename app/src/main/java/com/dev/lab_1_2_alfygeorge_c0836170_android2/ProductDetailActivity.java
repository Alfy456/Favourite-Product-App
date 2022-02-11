package com.dev.lab_1_2_alfygeorge_c0836170_android2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import com.dev.lab_1_2_alfygeorge_c0836170_android2.database.RoomDB;
import com.dev.lab_1_2_alfygeorge_c0836170_android2.databinding.ActivityProductDetailBinding;
import com.dev.lab_1_2_alfygeorge_c0836170_android2.model.Products;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductDetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    ActivityProductDetailBinding binding;
    Products products;
    private GoogleMap mMap;
    List<Products> productsList = new ArrayList<>();
    RoomDB database;

    private static final int REQUEST_CODE = 1;
    public static final int UPDATE_INTERVAl = 5000;
    public static final int FASTEST_INTERVAl = 3000;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    LatLng latLng = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //initialize database
        database = RoomDB.getInstance(this);
        addProducts();
        productsList = database.mainDAO().getAllProducts();

        binding.pdtTitle.setText(productsList.get(0).getProduct_name());
        binding.pdtDesc.setText(productsList.get(0).getProduct_description());
        binding.pdtPrice.setText(productsList.get(0).getProduct_price());
        findUserAddress(productsList.get(0).getProduct_latitude(), productsList.get(0).getProduct_longitude());

        latLng = new LatLng(productsList.get(0).getProduct_latitude(), productsList.get(0).getProduct_longitude());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        products = new Products();
        try {
            products = (Products) getIntent().getSerializableExtra("products");
            binding.pdtTitle.setText(products.getProduct_name());
            binding.pdtDesc.setText(products.getProduct_description());
            binding.pdtPrice.setText(products.getProduct_price());
            latLng = new LatLng(products.getProduct_latitude(),products.getProduct_longitude());

            findUserAddress(products.getProduct_latitude(), products.getProduct_longitude());
        }catch (Exception e){
            e.printStackTrace();
        }

        binding.imgShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                productsList = database.mainDAO().getAllProducts();
                Intent intent = new Intent(ProductDetailActivity.this,MainActivity.class);
                intent.putExtra("products",products);
                startActivityForResult(intent,102);
            }
        });

        //instantiation
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

         mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Add a marker in Sydney and move the camera
             mMap.addMarker(new MarkerOptions().position(latLng).title("Product provider location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));

        if (!isGranted()) {
            requestLocationPermission();
        } else {
            //startUpdateLocation();
        }


    }

    private  void  findUserAddress(double latitude,double longitude){
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

            binding.pdtProviderLocation.setText(address);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void addProducts() {
        database.mainDAO().insert(new Products("Samsung Galaxy Book Go 14",
                "Samsung Galaxy Book Go 14\" Laptop - Qualcomm Snapdragon 7c, 4GB DDR4, 128GB SSD, Win 11 Home (CAD Warranty)",
                "299.99",
                43.7670943329406, -79.19484879338205));

        database.mainDAO().insert(new Products("ASUS VivoBook 15 Thin and Light Laptop",
                "ASUS VivoBook 15 Thin and Light Laptop, 15.6” FHD, Intel Core i5-1035G1, 8GB RAM, 256GB PCIe SSD, Wi-Fi 6, Windows 10 Home - X512JA-BS52-CB",
                "669.00",
                43.775794549022855, -79.25779082955492));

        database.mainDAO().insert(new Products("ASUS VivoBook X705MA Thin and Lightweight Laptop",
                "ASUS VivoBook X705MA Thin and Lightweight Laptop, 17.3” HD+, Intel Pentium Silver N5030, 8GB DDR4, 1TB HDD, Wi-Fi 5, Windows 10 Home - X705MA-DH21-CA",
                "599.99",
                43.77560815596304, -79.1789899755869));

        database.mainDAO().insert(new Products("2020 Apple MacBook Air Laptop",
                "2020 Apple MacBook Air Laptop: Apple M1 Chip, 13\" Retina Display, 8GB RAM, 256GB SSD Storage, Backlit Keyboard, FaceTime HD Camera, Touch ID. Works with iPhone/iPad; Space Gray, English",
                "1,249.99",
                43.749771432229224, -79.29670967188389));

        database.mainDAO().insert(new Products("ASUS M509DA 15.6",
                "ASUS M509DA 15.6\" HD Laptop, AMD Ryzen 5 3500U Processor, 8 GB DDR4 RAM, 256 GB PCIE Storage, Windows 10 64-bit, Grey",
                "610.00",
                43.78997043329135, -79.25910178722634));

        database.mainDAO().insert(new Products("Dell Chromebook",
                "Dell Chromebook 3120 XDGJH - CRM3120-333BLK (11.6 inches, Intel Celeron N2840 2.16GHz, 4GB RAM, 16GB SSD, Chromebook OS) (Renewed)",
                "$178.00",
                43.82082483923851, -79.18142187188118));

        database.mainDAO().insert(new Products(" Lenovo IdeaPad",
                "New Lenovo IdeaPad 3 Intel i5-1135G7 Quad Core 12GB RAM 256GB SSD 15.6-inch FHD Touch Screen Laptop",
                "749.99",
                43.79629159481817, -79.13540002775103));

        database.mainDAO().insert(new Products("Microsoft Surface Laptop Go",
                "Microsoft Surface Laptop Go - 12.4\" Touchscreen - Intel Core i5 - 8GB Memory - 128GB SSD - Platinum - English",
                "759.99",
                43.76054009842148, -79.21845015839152));

        database.mainDAO().insert(new Products("Acer Chromebook 311 (NX.HKFAA.003)",
                "Acer Chromebook 311 (NX.HKFAA.003) | 11.6in. Display (1366 x 768) | Intel Celeron N4000 Processor | 4GB RAM | 32GB eMMC Storage | Chrome OS | Silver",
                "199.99",
                43.79386123496775, -79.23856156024222));

        database.mainDAO().insert(new Products("HP Laptop",
                "HP Laptop 14 inch, Intel® Celeron® N4500, Intel® UHD Graphics, 4 GB DDR4-2933 MHz RAM, Windows 10 Home (14-dq3020ca, 2021 Model)",
                "299.99",
                43.78943912013543, -79.23985345839043));

    }


    private void startUpdateLocation() {
        //location request
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAl);
        locationRequest.setFastestInterval(FASTEST_INTERVAl);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // mMap.clear();
                Location location = locationResult.getLastLocation();

                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                 mMap.addMarker(new MarkerOptions().position(latLng).title("User Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            }

            @Override
            public void onLocationAvailability(@NonNull LocationAvailability locationAvailability) {
                super.onLocationAvailability(locationAvailability);
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());


    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(this
                ,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);


    }

    private boolean isGranted() {
        return ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) ==
                (PackageManager.PERMISSION_GRANTED);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (REQUEST_CODE == requestCode){


            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)){
                new AlertDialog.Builder(this).setMessage("Accessing the location is mandatory")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(ProductDetailActivity.this
                                        ,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);

                            }
                        }).setNegativeButton("Cancel",null)
                        .create()
                        .show();


            }else {
                startUpdateLocation();
            }
        }
    }

}