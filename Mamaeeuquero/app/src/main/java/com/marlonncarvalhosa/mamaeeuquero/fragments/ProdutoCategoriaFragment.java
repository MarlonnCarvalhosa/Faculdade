package com.marlonncarvalhosa.mamaeeuquero.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.marlonncarvalhosa.mamaeeuquero.R;
import com.marlonncarvalhosa.mamaeeuquero.adapter.ProdutoAdapter;
import com.marlonncarvalhosa.mamaeeuquero.model.Produto;
import com.marlonncarvalhosa.mamaeeuquero.utils.ConfiguraçõesFirebase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProdutoCategoriaFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 71;
    private RecyclerView recyclerView;
    private List<Produto> produtos;
    private Query databaseProdutos;
    private ProdutoAdapter adapter;
    private ImageView imagemProduto;
    private Uri mImageUri;
    private String categoria;
    private String iddocomprador;
    private Bundle bundle;


    public ProdutoCategoriaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_produto_categoria, container, false);

        Bundle args = getArguments();
        if(args!= null){
        categoria = args.getString("key");
        iddocomprador=args.getString("ID");

        }
        bundle=getArguments();
        idCampo(view);
        preencherLista();
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.get().load(mImageUri).into(imagemProduto);
            imagemProduto.setImageURI(mImageUri);
        }

    }

    private void preencherLista() {
        produtos = new ArrayList<>();
        databaseProdutos = ConfiguraçõesFirebase.getProdutos();
        databaseProdutos.keepSynced(true);
        databaseProdutos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                try{

                    produtos.clear();
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Produto produto = snapshot.getValue(Produto.class);
                        if (categoria!=null){
                        if (categoria.equals(produto.getCat())){
                            produtos.add(produto);
                            }}

                            if (iddocomprador!=null){
                                if (iddocomprador.equals(produto.getIddovendedor())){
                                    produtos.add(produto);
                                }}

                    }
                    adapter.atualiza(produtos);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        adapter = new ProdutoAdapter(getActivity(),produtos);
        recyclerView.setAdapter(adapter);

    }

    public void idCampo(View view) {

        imagemProduto = view.findViewById(R.id.imagemProduto);
        recyclerView = view.findViewById(R.id.recyclerproduto);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

}


