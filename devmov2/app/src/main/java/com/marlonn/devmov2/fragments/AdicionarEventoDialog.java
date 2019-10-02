package com.marlonn.devmov2.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.content.ContentResolverCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.marlonn.devmov2.DAO.DataBaseDAO;
import com.marlonn.devmov2.R;
import com.marlonn.devmov2.model.Evento;
import com.marlonn.devmov2.utils.LocationData;

import java.util.HashMap;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class AdicionarEventoDialog extends AppCompatDialogFragment {

    private String uidEvento, nomeCriadorEvento;
    private ImageView imgEvento;
    private Evento evento = new Evento();
    private EditText nomeDoEvento, dataInicioEvento, dataFimEvento, horaInicioEvento, horaFimEvento, descricaoEvento;
    private boolean eventoOn = true;
    private double latitude;
    private double longitude;
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePatch;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseAuth firebaseAuth;
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.adicionar_evento, null);

        firebaseAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        nomeDoEvento = view.findViewById(R.id.edt_nomeEvento);
        nomeCriadorEvento = firebaseAuth.getCurrentUser().getDisplayName();
        dataInicioEvento = view.findViewById(R.id.edt_dataInicioEvento);
        dataFimEvento = view.findViewById(R.id.edt_dataFimEvento);
        horaInicioEvento = view.findViewById(R.id.edt_horaInicio);
        horaFimEvento = view.findViewById(R.id.edt_horaFim);
        descricaoEvento = view.findViewById(R.id.edt_descricao);
        imgEvento = view.findViewById(R.id.add_imagemEvento);
        imgEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleriaImagem();
            }
        });

        
        if(getArguments() != null){
            latitude = getArguments().getDouble("lat");
            longitude = getArguments().getDouble("long");
        }

        builder.setView(view);
        builder.setTitle("Crie um evento!");
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("Finalzar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                uploadEvento();
                uploadImagem();

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

    public static AdicionarEventoDialog newInstance(LocationData locationData) {
        AdicionarEventoDialog dialog = new AdicionarEventoDialog();
        Bundle bundle = new Bundle();
        bundle.putDouble("lat", locationData.latitude);
        bundle.putDouble("long", locationData.longitude);
        dialog.setArguments(bundle);
        return dialog;
    }

    private void abrirGaleriaImagem() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecionar Imagem"), PICK_IMAGE_REQUEST);

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null ) {

            filePatch = data.getData();

            try {
               Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePatch);
               imgEvento.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }

    private void uploadImagem () {
        if (filePatch != null) {
            StorageReference ref = storageReference.child("imagens/" + UUID.randomUUID().toString());
            ref.putFile(filePatch).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            DatabaseReference imagestore = FirebaseDatabase.getInstance().getReference().child("eventos").child(evento.getId()).child("imagem");
                            HashMap<String, String> hashMap  = new HashMap<>();
                            hashMap.put("imageurl", String.valueOf(uri));

                            imagestore.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            });

                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "falhou" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    private void uploadEvento () {
        uidEvento = user.getUid();
        evento.setIdUsuario(uidEvento);
        evento.setNomeDoEvento(nomeDoEvento.getText().toString());
        evento.setNomeCriadorEvento(nomeCriadorEvento);
        evento.setInicioDoEvento(dataInicioEvento.getText().toString());
        evento.setFimDoEvento(dataFimEvento.getText().toString());
        evento.setHoraInicio(horaInicioEvento.getText().toString());
        evento.setHoraFim(horaFimEvento.getText().toString());
        evento.setDescricaoDoEvento(descricaoEvento.getText().toString());
        evento.setLatitude(latitude);
        evento.setLongitude(longitude);
        evento.setEventoOn(eventoOn);

        new DataBaseDAO().saveEvento(getActivity(), evento);
    }
}