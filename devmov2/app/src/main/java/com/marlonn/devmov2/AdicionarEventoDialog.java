package com.marlonn.devmov2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.marlonn.devmov2.DAO.DataBaseDAO;
import com.marlonn.devmov2.model.Evento;

public class AdicionarEventoDialog extends AppCompatDialogFragment {

    private GoogleMap mMap;
    private Marker currentLocationMaker;
    private DatabaseReference mDatabase;
    private LatLng currentLocationLatLong;
    private DatabaseReference idInfo;
    private String uidEvento;
    private Evento evento = new Evento();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseAuth firebaseAuth;

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

                uploadEvento();

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

    private void uploadEvento () {



        uidEvento = user.getUid();

        evento.setIdEvento(uidEvento);
        evento.setNomeDoEvento("evento nome");
        evento.setDescricaoDoEvento("descriçao");
        evento.setFimDoEvento("10");
        evento.setInicioDoEvento("5");
        evento.setEventoOn(true);

        new DataBaseDAO().updateSimpleEvento(getActivity(), evento);
    }
}