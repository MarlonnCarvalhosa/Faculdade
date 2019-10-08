package com.marlonn.devmov2.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class AdicionarEventoDialog extends AppCompatDialogFragment {

    private String uidEvento, nomeCriadorEvento, diaInicio, mesInicio, nomeMes, anoInicio, diaFim, mesFim, anoFim, horaInicio, horaFim;
    private ImageView imgEvento;
    private Evento evento = new Evento();
    private EditText nomeDoEvento, descricaoEvento;
    private boolean eventoOn = true;
    private double latitude;
    private double longitude;
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePatch;
    private Button dataInicioEvento, dataFimEvento, horaInicioEvento, horaFimEvento ;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseAuth firebaseAuth;
    FirebaseStorage storage;
    StorageReference storageReference;

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    Calendar calendar;
    private int currentHour, currentMinute, year, month, dayOfMonth;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.adicionar_evento, null);

        firebaseAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        currentMinute = calendar.get(Calendar.MINUTE);

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
        horaInicioEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        horaInicio = String.format("%02d:%02d", hourOfDay, minutes);
                    }
                }, currentHour, currentMinute, true);

                timePickerDialog.show();
            }
        });

        horaFimEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        horaFim = String.format("%02d:%02d", hourOfDay, minutes);
                    }
                }, currentHour, currentMinute, true);

                timePickerDialog.show();
            }
        });

        dataInicioEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        diaInicio = String.valueOf(day);
                        mesInicio = String.valueOf(month + 1);
                        anoInicio = String.valueOf(year);

                        Calendar cal = Calendar.getInstance();
                        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
                        cal.set(Calendar.MONTH,month);
                        nomeMes = month_date.format(cal.getTime());
                    }
                }, year, month, dayOfMonth);
                datePickerDialog.show();
            }

        });

        dataFimEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        diaFim = String.valueOf(day);
                        mesFim = String.valueOf(month + 1);
                        anoFim = String.valueOf(year);
                    }
                }, year, month, dayOfMonth);
                datePickerDialog.show();
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
        evento.setDiaInicioEvento(diaInicio);
        evento.setMesInicioEvento(mesInicio);
        evento.setNomeMes(nomeMes.substring(0,1).toUpperCase().concat(nomeMes.substring(1)));
        evento.setAnoInicioEvento(anoInicio);
        evento.setDiaFimEvento(diaFim);
        evento.setMesFimEvento(mesFim);
        evento.setAnoFimEvento(anoFim);
        evento.setHoraInicio(horaInicio);
        evento.setHoraFim(horaFim);
        evento.setDescricaoDoEvento(descricaoEvento.getText().toString());
        evento.setLatitude(latitude);
        evento.setLongitude(longitude);
        evento.setEventoOn(eventoOn);

        new DataBaseDAO().saveEvento(getActivity(), evento);
    }

}