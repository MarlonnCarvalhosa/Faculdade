package com.marlonncarvalhosa.mamaeeuquero.DAO;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.marlonncarvalhosa.mamaeeuquero.R;
import com.marlonncarvalhosa.mamaeeuquero.Views.MainActivity;
import com.marlonncarvalhosa.mamaeeuquero.model.Conversa;
import com.marlonncarvalhosa.mamaeeuquero.model.Imagem;
import com.marlonncarvalhosa.mamaeeuquero.model.Mensagem;
import com.marlonncarvalhosa.mamaeeuquero.model.Produto;
import com.marlonncarvalhosa.mamaeeuquero.model.Usuario;
import com.marlonncarvalhosa.mamaeeuquero.utils.ConfiguraçõesFirebase;
import com.marlonncarvalhosa.mamaeeuquero.utils.ConstantsUtils;
import com.marlonncarvalhosa.mamaeeuquero.utils.ImageUtis;

import java.util.UUID;

public class DataBaseDAO {

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private boolean sucesso = true;
    private int cont = 0;


    public void saveUsuario(Usuario usuario) {
        DatabaseReference reference = ConfiguraçõesFirebase.getFirebase();
        reference.child(ConstantsUtils.BANCO_USUARIO).child(String.valueOf(usuario.getId())).setValue(usuario);
    }

    public void uploadDados(FragmentActivity activity, ImageView image1, ImageView image2, ImageView image3, Produto produto, ProgressDialog progressDialog) {

        uploadImagem(image1, activity, produto.getImagem1(), produto, progressDialog);
        uploadImagem(image2, activity, produto.getImagem2(), produto, progressDialog);
        uploadImagem(image3, activity, produto.getImagem3(), produto, progressDialog);


    }

    private void uploadProduto(Produto produto) {
        produto.setId(ConfiguraçõesFirebase.getFirebase().push().getKey());
        Log.v("teste save", produto.getId() + produto.getNome());
        DatabaseReference reference = ConfiguraçõesFirebase.getFirebase();
        reference.child(ConstantsUtils.BANCO_PRODUTOS).child(String.valueOf(produto.getId())).setValue(produto);
    }

    private boolean uploadImagem(final ImageView image, final FragmentActivity activity, final Imagem imagem, final Produto produto, final ProgressDialog progressDialog) {

        byte[] data = ImageUtis.imageViewParaBytes(image);
        final String path = ConstantsUtils.BANCO_PRODUTOS + UUID.randomUUID() + ".png";
        imagem.setPath(path);
        final StorageReference reference = storage.getReference(path);


        StorageMetadata metadata = new StorageMetadata.Builder().setCustomMetadata("text", produto.getNome()).build();

        UploadTask uploadTask = reference.putBytes(data, metadata);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

                progressDialog.dismiss();
                Toast.makeText(activity, "Erro!:.>" + exception.getMessage(), Toast.LENGTH_SHORT).show();
                sucesso = false;

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {



                StorageReference storageRef = storage.getReference();
                storageRef.child(path).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        imagem.setUrl(String.valueOf(uri));
                        cont++;

                        if(cont==3 && imagem.getUrl()!= null){
                            uploadProduto(produto);
                            progressDialog.dismiss();
                            AlertDialog.Builder alert = new AlertDialog.Builder(activity);

                            alert
                                    .setTitle("Produto Leiloado ;)")
                                    .setIcon(R.drawable.ic_action_check_verde)
                                    .setMessage("Seu produto foi anunciado com sucesso!")
                                    .setCancelable(false)
                                    .setPositiveButton("Finalizar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            activity.startActivity(new Intent(activity, MainActivity.class));
                                        }

                                    });

                            AlertDialog alertDialog = alert.create();
                            alertDialog.show();


                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {

                    }
                });





            }
        });


        return sucesso;
    }
    public static Query getQuerryUsuario(String uId) {
        return FirebaseDatabase.getInstance().getReference(ConstantsUtils.BANCO_USUARIO).child(uId);
    }


    public void updateSimpleInfoUser(Usuario usuario) {
        DatabaseReference reference = ConfiguraçõesFirebase.getFirebase();
        reference.child(ConstantsUtils.BANCO_USUARIO).child(String.valueOf(usuario.getId())).setValue(usuario);
    }

    public void newConversa(Conversa conversa) {
        DatabaseReference reference = ConfiguraçõesFirebase.getFirebase();
        reference.child(ConstantsUtils.BANCO_CONVERSA).child(String.valueOf(conversa.getId())).setValue(conversa);
    }

    public static Query getConversas(String uid) {
        return FirebaseDatabase.getInstance().getReference(ConstantsUtils.BANCO_USUARIO).child(uid+"/conversas");

    }

    public void enviarMensagem(Mensagem mensagem, Conversa conversa) {
        DatabaseReference reference = ConfiguraçõesFirebase.getFirebase();
        reference.child(ConstantsUtils.BANCO_CONVERSA).child(conversa.getId()).child(ConstantsUtils.MENSAGENS).child(mensagem.getId()).setValue(mensagem);


    }

    public static Query getConversa() {
        return FirebaseDatabase.getInstance().getReference(ConstantsUtils.BANCO_CONVERSA);
    }

    public static Query getMensagens(Conversa conversa) {
        Log.v("CONVERSA", conversa.getId());
        return FirebaseDatabase.getInstance().getReference(ConstantsUtils.BANCO_CONVERSA).child(conversa.getId()).child("mensagens");

    }
}