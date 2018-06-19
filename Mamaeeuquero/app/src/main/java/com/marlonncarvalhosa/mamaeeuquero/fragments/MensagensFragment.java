package com.marlonncarvalhosa.mamaeeuquero.fragments;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.marlonncarvalhosa.mamaeeuquero.DAO.DataBaseDAO;
import com.marlonncarvalhosa.mamaeeuquero.R;
import com.marlonncarvalhosa.mamaeeuquero.adapter.AdapterConversas;
import com.marlonncarvalhosa.mamaeeuquero.adapter.AdapterMensagens;
import com.marlonncarvalhosa.mamaeeuquero.model.Conversa;
import com.marlonncarvalhosa.mamaeeuquero.model.Mensagem;
import com.marlonncarvalhosa.mamaeeuquero.model.Produto;
import com.marlonncarvalhosa.mamaeeuquero.model.Usuario;
import com.marlonncarvalhosa.mamaeeuquero.utils.ConfiguraçõesFirebase;
import com.marlonncarvalhosa.mamaeeuquero.utils.ConstantsUtils;
import com.marlonncarvalhosa.mamaeeuquero.utils.FragmentoUtils;

import java.util.ArrayList;
import java.util.List;

public class MensagensFragment extends Fragment {
    private Usuario vendedor = new Usuario();
    private Usuario comprador = new Usuario();
    private EditText editTextMensagem;
    private Button buttonEnviar;
    private Conversa conversa;
    private RecyclerView recyclerView;
    private Bundle bundle;
     private List<Conversa> conversasComprador;
    private boolean existente= false;
    private FirebaseUser currentFirebaseUser;
    private AdapterMensagens adapter;
    private List<Mensagem >mensagems ;
    private Query queryMensagens;
    private FirebaseAuth auth;
    private String idusuario;
    private int back;




    public static Fragment newInstace(Conversa conversa, int i) {
        MensagensFragment mensagensFragment = new MensagensFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantsUtils.CALL, i);
        bundle.putSerializable(ConstantsUtils.CONVERSA, conversa);

        mensagensFragment.setArguments(bundle);
        return mensagensFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mensagens, container, false);

         currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
         auth=FirebaseAuth.getInstance();
         idusuario=auth.getUid();
        initView(view);
        getMensagens();

        return view;

    }

    private void initView(View view) {
        recyclerView= view.findViewById(R.id.recyclerMsg);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), 0));;

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration mDivider = new DividerItemDecoration(recyclerView.getContext(),
                0
        );
        recyclerView.addItemDecoration(mDivider);
        editTextMensagem = view.findViewById(R.id.editTextMensagem);
        buttonEnviar = view.findViewById(R.id.buttonEnviar);

        bundle = getArguments();
        if (bundle != null) {
            // criar Conversa se não existir guardar id no perfil dos dois usuarios;
            conversa = (Conversa) bundle.getSerializable(ConstantsUtils.CONVERSA);
            back = bundle.getInt(ConstantsUtils.CALL);
            getUsuario(conversa.getIdVendedor(), "v");
            getUsuario(conversa.getIdComprador(), "c");


            //verifica se a conversa ja exite

        }

        buttonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensagem = editTextMensagem.getText().toString();
                if(!mensagem.isEmpty()){
                    conversasComprador = comprador.getConversas();
                    for (Conversa c: conversasComprador ) {
                        if (c.getProduto().getId().equals(conversa.getProduto().getId())) {
                            existente = true;
                            break;

                        }



                    }
                    if (!existente) {
                        comprador.getConversas().add(conversa);
                        vendedor.getConversas().add(conversa);
                        //criar banco conv
                        new DataBaseDAO().newConversa(conversa);
                        new DataBaseDAO().updateSimpleInfoUser(comprador);
                        new DataBaseDAO().updateSimpleInfoUser(vendedor);
                    } else {


                    }

                    enviarMensagem(mensagem);
                }else {
                    Toast.makeText(getActivity(), "Digite algo!", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    private void enviarMensagem(String msg) {
        Mensagem mensagem = new Mensagem();
        mensagem.setId(ConfiguraçõesFirebase.getFirebase().push().getKey());
        if(currentFirebaseUser.getUid().equals(comprador.getId())){
            comprador.setConversas(null);
            mensagem.setUsuario(comprador);
        }else {
            vendedor.setConversas(null);
            mensagem.setUsuario(vendedor);

        }
        mensagem.setTexto(msg);
        new DataBaseDAO().enviarMensagem(mensagem, conversa);
        editTextMensagem.setText("");

    }


    private void getUsuario(String uId, final String s) {
        Query queryPerfil = DataBaseDAO.getQuerryUsuario(uId);
        queryPerfil.keepSynced(true);
        queryPerfil.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    Usuario usuario = dataSnapshot.getValue(Usuario.class);
                    if (s.equals("c")) {
                        setComprador(usuario);
                    } else {
                        setVendedor(usuario);
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

    public void setComprador(Usuario comprador) {
        this.comprador = comprador;
    }

    public void setVendedor(Usuario vendedor) {
        this.vendedor = vendedor;
    }

    public void getMensagens() {
        mensagems = new ArrayList<>();
        queryMensagens = DataBaseDAO.getMensagens(conversa);
        queryMensagens.keepSynced(true);
        queryMensagens.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    mensagems.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Mensagem mensagem = snapshot.getValue(Mensagem.class);
                        mensagems.add(mensagem);


                    }
                    adapter.atualiza(mensagems);

                } catch (Exception e) {
                    e.printStackTrace();

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        adapter = new AdapterMensagens(mensagems,getActivity(),idusuario) ;
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onResume() {
        super.onResume();

        if(getView() == null){
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    // handle back button's click listener
                    if(back ==0){
                        FragmentoUtils.replace(getActivity(), new DescricaoFragment().newInstance(conversa.getProduto()));
                    }
                    else {
                        FragmentoUtils.replace(getActivity(), new ConversasFragment());
                    }
                    return true;
                }
                return false;
            }
        });
    }

        }


