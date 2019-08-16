package com.marlonncarvalhosa.mamaeeuquero.fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.marlonncarvalhosa.mamaeeuquero.DAO.DataBaseDAO;
import com.marlonncarvalhosa.mamaeeuquero.R;
import com.marlonncarvalhosa.mamaeeuquero.Views.ImagensActivity;
import com.marlonncarvalhosa.mamaeeuquero.model.Conversa;
import com.marlonncarvalhosa.mamaeeuquero.model.Produto;
import com.marlonncarvalhosa.mamaeeuquero.model.Usuario;
import com.marlonncarvalhosa.mamaeeuquero.utils.ConfiguraçõesFirebase;
import com.marlonncarvalhosa.mamaeeuquero.utils.ConstantsUtils;
import com.marlonncarvalhosa.mamaeeuquero.utils.FragmentoUtils;
import com.marlonncarvalhosa.mamaeeuquero.utils.LanceDialog;

import java.util.ArrayList;
import java.util.List;


public class DescricaoFragment extends Fragment {
    public static final String URL_IMAGEM = "package com.marlonncarvalhosa.mamaeeuquero.fragments;";
    private Bundle bundle;
    private Produto produto;
    private ImageView imageView;
    private TextView nomeProduto, lanceProduto, tempoProduto, detalhesProduto, iddocomprador;
    private Button btnLance, btnMsg;
    private FirebaseAuth auth;

    private List<Conversa> conversas = new ArrayList<>();
    private Query databaseConversas;
    private FirebaseUser currentFirebaseUser;



    public DescricaoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_descricao, container, false);
        auth = FirebaseAuth.getInstance();
        idCampo(view);
        initView(view);
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        getConversa(produto);
        imgFull(view);


        getActivity().setTitle("Detalhes do Produto");
        btnMsg.setOnClickListener(new View.OnClickListener() {
            public Conversa conversa;

            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() == null) {
                    FragmentoUtils.replace(getActivity(), new LoginFragment());
                    return;
                }
                if (currentFirebaseUser.getUid().equals(produto.getIddovendedor())) {

                    android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(getContext());

                    alert
                            .setTitle("ATENÇÃO!")
                            .setIcon(R.drawable.ic_action_alert_red)
                            .setMessage("Não pode abrir uma conversa com você mesmo.")
                            .setCancelable(true)
                            .setPositiveButton("Entendi", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }

                            });

                    android.app.AlertDialog alertDialog = alert.create();
                    alertDialog.show();

                }else {

                    if (auth.getCurrentUser() != null) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user != null) {
                       /* if (conversa != null) {
                            FragmentoUtils.replace(getActivity(), MensagensFragment.newInstace(conversa));
                            Log.v("CONVERSA", conversa.getId());
                        } else {


                        }*/
                            boolean exist = false;

                            for (Conversa conversa : conversas) {
                                if (conversa.getProduto().getId().equals(produto.getId()) && conversa.getIdComprador().equals(currentFirebaseUser.getUid())) {
                                    this.conversa = conversa;
                                    exist = true;
                                    break;
                                }
                            }


                            if (exist) {
                                Log.v("CONVERSA", conversa.getId());
                                FragmentoUtils.replace(getActivity(), MensagensFragment.newInstace(conversa, 0));
                            } else {
                                Conversa conversa = new Conversa();
                                conversa.setId(ConfiguraçõesFirebase.getFirebase().push().getKey());
                                conversa.setIdComprador(user.getUid());
                                conversa.setIdVendedor(produto.getIddovendedor());
                                conversa.setProduto(produto);
                                Log.v("CONVERSA", conversa.getId());
                                FragmentoUtils.replace(getActivity(), MensagensFragment.newInstace(conversa, 0));
                            }

                        } else {
                            Toast.makeText(getActivity(), "Logado!", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            }
        });

        return view;
    }

    public void imgFull(View view) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().startActivity(new Intent(ImagensActivity.newIntent(getActivity(), produto)));

            }
        });
    }

    private void idCampo(View view) {

        nomeProduto = view.findViewById(R.id.TextProduto);
        lanceProduto = view.findViewById(R.id.TextValor);
        tempoProduto = view.findViewById(R.id.TextTempo);
        detalhesProduto = view.findViewById(R.id.textDetalhes);
        imageView = view.findViewById(R.id.imageProduto);
        btnLance = view.findViewById(R.id.btn_lance);
        btnMsg = view.findViewById(R.id.btn_enviar_msg);
    }

    private void abrirDialogo() {

        LanceDialog lance_dialog = new LanceDialog();
        lance_dialog.setProduto(produto);
        lance_dialog.show(getFragmentManager(), " Lance");

    }

    private void initView(final View view) {

        bundle = getArguments();
        if (bundle != null) {
            produto = (Produto) bundle.getSerializable(ConstantsUtils.PRODUTO);

            nomeProduto.setText("" + produto.getNome());
            lanceProduto.setText(produto.getPreco());
            tempoProduto.setText(produto.getNomedocomprador());
            detalhesProduto.setText(produto.getDescrição());
            //  iddocomprador.setText(produto.getLancedocomprador());
            btnLance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    verificaAuth();
                }
            });

            String url = produto.getImagem1().getUrl();

            try {
                Glide.with(getActivity())
                        .load(url)
                        .into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private void getConversa(Produto produto) {

        databaseConversas = DataBaseDAO.getConversa();
        databaseConversas.keepSynced(true);
        databaseConversas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    conversas.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Conversa conversa = snapshot.getValue(Conversa.class);
                        conversas.add(conversa);

                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public DescricaoFragment newInstance(Produto produto) {
        DescricaoFragment descricaoFragment = new DescricaoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ConstantsUtils.PRODUTO, produto);
        descricaoFragment.setArguments(bundle);
        return descricaoFragment;
    }

    public void verificaAuth() {
        if (auth.getCurrentUser() != null) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                if (user.getUid().equals(produto.getIddovendedor())) {

                    if (produto.getLancedocomprador().equals("")) {

                        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(getContext());

                        alert
                                .setTitle("ATENÇÃO!")
                                .setIcon(R.drawable.ic_action_alert_red)
                                .setMessage("Produto ainda não possui um lance")
                                .setCancelable(true)
                                .setPositiveButton("Entendi", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }

                                });

                        android.app.AlertDialog alertDialog = alert.create();
                        alertDialog.show();

                    } else {

                        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(getContext());

                        alert
                                .setTitle("ATENÇÃO!")
                                .setIcon(R.drawable.ic_action_alert_red)
                                .setMessage("Produto sendo arrematado por " + produto.getNomedocomprador() + " no valor de R$ " + produto.getPreco())
                                .setCancelable(true)
                                .setPositiveButton("Entendi", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }

                                });

                        android.app.AlertDialog alertDialog = alert.create();
                        alertDialog.show();

                    }

                    return;

                } else {
                    abrirDialogo();

                }

            }

        }

        if (auth.getCurrentUser() == null) {
            FragmentoUtils.replace(getActivity(), new LoginFragment());
        }

    }

    //Evento de clique de voltar para fragmento anterior <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Override
    public void onResume() {
        super.onResume();

        if (getView() == null) {
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    FragmentoUtils.replace(getActivity(), new InicioFragment());
                    return true;
                }

                return false;

            }

        });

    }

}