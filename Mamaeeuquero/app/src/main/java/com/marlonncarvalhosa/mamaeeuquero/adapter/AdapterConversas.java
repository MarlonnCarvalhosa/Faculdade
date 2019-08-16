package com.marlonncarvalhosa.mamaeeuquero.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.marlonncarvalhosa.mamaeeuquero.R;
import com.marlonncarvalhosa.mamaeeuquero.fragments.MensagensFragment;
import com.marlonncarvalhosa.mamaeeuquero.model.Conversa;
import com.marlonncarvalhosa.mamaeeuquero.utils.FragmentoUtils;


import java.util.List;

public class AdapterConversas extends RecyclerView.Adapter<AdapterConversas.ViewHolder> {
    private List<Conversa> conversas;
    private FragmentActivity activity;
    public AdapterConversas(FragmentActivity activity , List<Conversa> conversas ) {
        this.activity= activity;
        this.conversas = conversas;


    }

    public AdapterConversas.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterConversas.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_conversas, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterConversas.ViewHolder holder, int position) {
            final Conversa conversa = conversas.get(position);
            holder.textViewProduto.setText(conversa.getProduto().getNome());
            holder.textViewUsuario.setText("abrir conversa");

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentoUtils.replace(activity,  MensagensFragment.newInstace(conversa,1));

                }
            });
    }

    @Override
    public int getItemCount() {
    return  conversas.size();

    }

    public void  atualiza(List<Conversa> conversas){
        this.conversas = conversas;
        this.notifyDataSetChanged();

    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewProduto;
        private TextView textViewUsuario;


        public ViewHolder(View itemView) {
            super(itemView);
            textViewProduto= itemView.findViewById(R.id.textProduto);
            textViewUsuario = itemView.findViewById(R.id.textUsuario);

        }
    }
}
