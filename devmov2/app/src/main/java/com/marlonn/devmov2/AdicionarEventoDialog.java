package com.marlonn.devmov2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.database.DatabaseReference;

public class AdicionarEventoDialog extends AppCompatDialogFragment implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private Marker currentLocationMaker;
    private DatabaseReference mDatabase;
    private LatLng currentLocationLatLong;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());



        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.adicionar_evento, null);

        builder.setView(view);
        builder.setTitle("Adicionar Evento");
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("Finalzar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                final ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setMessage(AdicionarEventoDialog.this.getString(R.string.aguarde));
                progressDialog.show();

                Runnable progressRunnable = new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.cancel();
                    }
                };

                Handler pdCanceller = new Handler();
                pdCanceller.postDelayed(progressRunnable, 2500);



            }
        });

        return builder.create();
    }

    @Override
    public void onLocationChanged(Location location) {

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
    public void onMapReady(GoogleMap googleMap) {

    }
}