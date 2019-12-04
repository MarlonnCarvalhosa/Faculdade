package com.marlonn.devmov2.views;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.Guideline;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.marlonn.devmov2.R;
import com.marlonn.devmov2.fragments.AdicionarEventoDialog;
import com.marlonn.devmov2.fragments.DescricaoEventoDialog;
import com.marlonn.devmov2.fragments.LoginDialog;
import com.marlonn.devmov2.model.Evento;
import com.marlonn.devmov2.model.Usuario;
import com.marlonn.devmov2.utils.CircleTransform;
import com.marlonn.devmov2.utils.LocationData;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, Serializable, AdicionarEventoDialog.CallbackEvento {

    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth firebaseAuth;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private Marker currentLocationMaker;
    private LatLng currentLocationLatLong;
    private LocationData locationData;
    private DatabaseReference mDatabase;
    private CircleImageView btn_perfil;
    private FloatingActionButton btn_mylocation;
    private ArrayList<Evento>  eventos = new ArrayList<>();
    private Usuario usuario = new Usuario();
    private Evento evento = new Evento();
    private String uidEvento, idUsuario, nomeUsuario, fotoPerfilGoogle;
    private TextView nomeDoEvento, descricaoDoEvento;
    private int hora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_maps);
        nomeDoEvento = findViewById(R.id.txt_nomeEvento);
        descricaoDoEvento = findViewById(R.id.txt_decricaoEvento);
        btn_perfil = findViewById(R.id.perfil_btn);
        btn_mylocation = findViewById(R.id.btn_my_location);
        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(MapsActivity.this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        startGettingLocations();
        getMarkers();
        mMap = googleMap;

        if (firebaseAuth.getCurrentUser() != null) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            Picasso.get().load(firebaseAuth.getCurrentUser().getPhotoUrl()).into(btn_perfil);

            if (user != null) {

                mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng latLng) {

                        locationData = new LocationData(latLng.latitude, latLng.longitude);
                        AdicionarEventoDialog adicionarEventoDialog = AdicionarEventoDialog.newInstance(locationData);
                        adicionarEventoDialog.show(getSupportFragmentManager(), " Eventos");
                    }
                });

                btn_perfil.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(MapsActivity.this, ProfileActivity.class);
                        startActivity(intent);

                    }
                });

            }

        } else {

            mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {

                    abrirDialogoLogin();

                }
            });

            btn_perfil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    abrirDialogoLogin();

                }
            });

        }

        btn_mylocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startGettingLocations();

            }
        });

        Calendar c = Calendar.getInstance();
        hora = c.get(Calendar.HOUR_OF_DAY);

        if(hora >= 18 || hora < 6) {
            try {
                boolean success = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.mapstyle));

                if (!success) {
                    Log.e("MapActivity", "Style parsing failed.");
                }

            } catch (Resources.NotFoundException e) {
                Log.e("MapActivity", "Can't find style. Error: ", e);
            }

        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Evento evento = getEventos(marker.getTag().toString());
                DescricaoEventoDialog.newInstance().setarEvento(evento).show(getSupportFragmentManager(), "evento");

                return false;
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {

        if (currentLocationMaker != null) {
            currentLocationMaker.remove();
        }

        currentLocationLatLong = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLocationLatLong);
        markerOptions.title("Localização atual");
        BitmapDescriptor icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
        markerOptions.icon(icon);
        try{
            markerOptions.title(firebaseAuth.getCurrentUser().getDisplayName());
        }catch (Exception e){
            markerOptions.title("Eu");
        }

        currentLocationMaker = mMap.addMarker(markerOptions);

        CameraPosition cameraPosition = new CameraPosition.Builder().zoom(15).target(currentLocationLatLong).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        if (firebaseAuth.getCurrentUser() != null) {
            LocationData locationData = new LocationData(location.getLatitude(), location.getLongitude());
            mDatabase.child("usuarios").child(firebaseAuth.getCurrentUser().getUid()).child("mylocation").setValue(locationData);

        }

    }

    private ArrayList findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList result = new ArrayList();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canAskPermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canAskPermission() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("GPS desativado!");
        alertDialog.setMessage("Ativar GPS?");
        alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }

    private void startGettingLocations() {

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        boolean isGPS = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetwork = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean canGetLocation = true;
        int ALL_PERMISSIONS_RESULT = 101;
        long MIN_DISTANCE_CHANGE_FOR_UPDATES = 5;// Distance in meters
        long MIN_TIME_BW_UPDATES = 60000;// Time in milliseconds

        ArrayList<String> permissions = new ArrayList<>();
        ArrayList<String> permissionsToRequest;

        permissions.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionsToRequest = findUnAskedPermissions(permissions);

        //Check if GPS and Network are on, if not asks the user to turn on
        if (!isGPS && !isNetwork) {
            showSettingsAlert();
        } else {
            // check permissions

            // check permissions for later versions
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (permissionsToRequest.size() > 0) {
                    requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]),
                            ALL_PERMISSIONS_RESULT);
                    canGetLocation = false;
                }
            }
        }

        //Checks if FINE LOCATION and COARSE Location were granted
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(this, "Permissão negada", Toast.LENGTH_SHORT).show();
            return;
        }

        //Starts requesting location updates
        if (canGetLocation) {
            if (isGPS) {
                lm.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

            } else if (isNetwork) {
                // from Network Provider

                lm.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

            }
        } else {
            Toast.makeText(this, "Não é possível obter a localização", Toast.LENGTH_SHORT).show();
        }
    }

    private void getMarkers(){
        mDatabase.child("eventos").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue(Evento.class) != null) {
                            for(DataSnapshot data : dataSnapshot.getChildren()){
                                Evento evento = data.getValue(Evento.class);
                                evento.setId(data.getKey());
                                eventos.add(evento);
                            }
                            pegarEventos(eventos);

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }

                });

    }

    private void pegarEventos(List<Evento> evento) {
        for (Evento evento1 : evento) {
            addRedMarkers(evento1);
        }

    }

    private void addRedMarkers(Evento evento) {
        LatLng latLng = new LatLng(evento.getLatitude(), evento.getLongitude());
        Marker marker = mMap.addMarker(new MarkerOptions().position(latLng));
        marker.setTag(evento.getId());
        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

    }

    private Evento getEventos(String id) {
        for(Evento evento: eventos){
                if(evento.getId().equalsIgnoreCase(id))
                return evento;
        }
        return new Evento();

    }

    public boolean isOnline() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return manager.getActiveNetworkInfo() != null &&
                manager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (opr.isDone()) {
            GoogleSignInResult result = opr.get();
            handleSigninResult(result);
        } else {
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSigninResult(googleSignInResult);
                }
            });
        }

    }

    private void handleSigninResult(GoogleSignInResult result) {
        if (result.isSuccess()) {

            GoogleSignInAccount conta = result.getSignInAccount();
            idUsuario = user.getUid();
            nomeUsuario = conta.getDisplayName();
            fotoPerfilGoogle = String.valueOf(conta.getPhotoUrl());

        }

    }

    private void abrirDialogoLogin() {

        LoginDialog loginDialog = new LoginDialog();
        loginDialog.show(getSupportFragmentManager(), " login");

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override
    public void onProviderEnabled(String s) {
    }

    @Override
    public void onProviderDisabled(String s) {
    }

    @Override
    public void atualizaMarkers() {
        getMarkers();
    }
}
