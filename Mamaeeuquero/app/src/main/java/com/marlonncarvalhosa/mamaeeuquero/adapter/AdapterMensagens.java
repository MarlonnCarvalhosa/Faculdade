package com.marlonncarvalhosa.mamaeeuquero.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.marlonncarvalhosa.mamaeeuquero.R;
import com.marlonncarvalhosa.mamaeeuquero.model.Mensagem;

import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public class AdapterMensagens extends RecyclerView.Adapter<AdapterMensagens.ViewHolder> {
    private List<Mensagem> mensagems ;
    private FragmentActivity activity;
    private LinearLayout linearLayout;
    private TextView textView;
    private CardView cardMens;
    private String idusuario;


    public AdapterMensagens(List<Mensagem> mensagems, FragmentActivity activity, String idusuario) {
        this.mensagems = mensagems;
        this.activity = activity;
        this.idusuario=idusuario;
    }



    @NonNull
    @Override
    public AdapterMensagens.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterMensagens.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_mensagens, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMensagens.ViewHolder holder, int position) {
        Mensagem mensagem = mensagems.get(position);
        Log.v("teste",mensagem.getUsuario().getId()+"==="+idusuario);
        if ((mensagem.getUsuario().getId()).equals(idusuario)){
            linearLayout.setGravity(Gravity.RIGHT);
            textView.setGravity(Gravity.RIGHT);
            cardMens.setCardBackgroundColor(ContextCompat.getColor(activity, R.color.verdeClaro));
        }else {
            holder.textViewNome.setText(mensagem.getUsuario().getNomeUsuario());

        }

        holder.textViewMsg.setText(mensagem.getTexto());

    }

    @Override
    public int getItemCount() {
        return mensagems.size();
    }

    public void atualiza(List<Mensagem>mensagems){
        this. mensagems= mensagems;
        this.notifyDataSetChanged();
        Log.v("MENSA", mensagems.size()+"");

    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewMsg;
        private TextView textViewNome;

        public ViewHolder(View itemView) {
            super(itemView);
            cardMens = itemView.findViewById(R.id.cardMens);
            linearLayout = itemView.findViewById(R.id.linearmensagens);
            textView=itemView.findViewById(R.id.textNome);
            textViewMsg = itemView.findViewById(R.id.textTexto);
            textViewNome = itemView.findViewById(R.id.textNome);
        }
    }
}