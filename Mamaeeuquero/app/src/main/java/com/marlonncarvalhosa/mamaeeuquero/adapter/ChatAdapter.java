package com.marlonncarvalhosa.mamaeeuquero.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marlonncarvalhosa.mamaeeuquero.R;
import com.marlonncarvalhosa.mamaeeuquero.model.Mensagem;
import com.marlonncarvalhosa.mamaeeuquero.model.Usuario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{

  private List<Mensagem> mensagens;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_chat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Mensagem mensagem = mensagens.get(position);

        holder.textViewMensagem.setText(mensagem.getTexto());
        holder.textViewNome.setText(mensagem.getNome());

    }

    @Override
    public int getItemCount() {
        return mensagens.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewMensagem;
        private TextView textViewNome;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewNome = itemView.findViewById(R.id.nomeChat);
            textViewMensagem = itemView.findViewById(R.id.text_view_mensagem);
        }
    }
}
