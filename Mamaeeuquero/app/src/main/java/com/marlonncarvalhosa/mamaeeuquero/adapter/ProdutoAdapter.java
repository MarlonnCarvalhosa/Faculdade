package com.marlonncarvalhosa.mamaeeuquero.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.marlonncarvalhosa.mamaeeuquero.R;
import com.marlonncarvalhosa.mamaeeuquero.fragments.DescricaoFragment;
import com.marlonncarvalhosa.mamaeeuquero.model.Produto;
import com.marlonncarvalhosa.mamaeeuquero.model.Usuario;
import com.marlonncarvalhosa.mamaeeuquero.utils.FragmentoUtils;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ViewHolder> {
    private FragmentActivity activity;
    private List<Produto> produtos;
    private Usuario usuario = new Usuario();

    public final static long SECOND_MILLIS = 1000;
    public final static long MINUTE_MILLIS = SECOND_MILLIS*60;
    public final static long HOUR_MILLIS = MINUTE_MILLIS*60;
    private static final int MINUTES_IN_AN_HOUR = 60;
    private static final int SECONDS_IN_A_MINUTE = 60;

    private static final String FORMAT = "%02dh %02dm %02ds";

    int seconds , minutes;

    public ProdutoAdapter(FragmentActivity activity, List<Produto> produtos){
        this.activity = activity;
        this.produtos = produtos;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewProduto,textViewPreco,textViewCidade,data, textViewCountTimer, titulo_lance;
        private ImageView imageView;
        private LinearLayout clickCard;
        Calendar calendar;

        public ViewHolder(View itemView) {
            super(itemView);

            clickCard = itemView.findViewById(R.id.linearAdapter);
            titulo_lance = itemView.findViewById(R.id.titulo_lance);
            textViewCountTimer = itemView.findViewById(R.id.tempo);
            textViewProduto = itemView.findViewById(R.id.nomeProduto);
            textViewCidade = itemView.findViewById(R.id.cidade);
            textViewPreco = itemView.findViewById(R.id.preco);
            imageView = itemView.findViewById(R.id.imagemProduto);
            data = itemView.findViewById(R.id.datainicio);
            calendar = Calendar.getInstance();

        }
    }

    public void atualiza(List<Produto> produtos){
        Collections.reverse(produtos);
        this.produtos=produtos;
        this.notifyDataSetChanged();

    }

    @NonNull
    @Override
    public ProdutoAdapter.ViewHolder  onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProdutoAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produto,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ProdutoAdapter.ViewHolder holder, int position) {
        final Produto produto = produtos.get(position);

        holder.textViewProduto.setText(produto.getNome());
        holder.textViewCidade.setText(produto.getLocal());
        holder.textViewPreco.setText(produto.getPreco());
        holder.data.setText(produto.getDataInicial());
        holder.calendar.set(Calendar.DAY_OF_MONTH,produto.getDia());
        holder.calendar.set(Calendar.HOUR_OF_DAY, produto.getHora());
        holder.calendar.set(Calendar.MINUTE,produto.getMinuto());
        holder.calendar.set(Calendar.SECOND,produto.getSegundos());

        holder.textViewCountTimer.getContext();

        holder.clickCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentoUtils.replace(activity, new DescricaoFragment().newInstance(produto));

            }
        });
        try {
            Glide.with(activity).load(produto.getImagem1().getUrl()).apply(RequestOptions.circleCropTransform()).into(holder.imageView);
        }catch (Exception e){
            e.printStackTrace();
        }

        Calendar proximoDia = Calendar.getInstance();
        proximoDia.setTimeInMillis(holder.calendar.getTimeInMillis());
        proximoDia.add(Calendar.DAY_OF_MONTH, 1);

        long milisegundos = (proximoDia.getTimeInMillis() - Calendar.getInstance().getTimeInMillis());

        new CountDownTimer(	milisegundos, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {

               holder.textViewCountTimer.setText(timeConversion((int) (millisUntilFinished)/1000));

            }

            public void onFinish() {

                holder.textViewCountTimer.setText("Tempo expirado!");
                holder.titulo_lance.setText("Arrematado por:");
                holder.titulo_lance.setTextColor(ContextCompat.getColor(activity, R.color.verdeEscuro));
                holder.clickCard.setBackgroundColor(ContextCompat.getColor(activity, R.color.teste));

                if (usuario.getId().equals(produto.getIddovendedor())) {

                    return;
                }
                if (usuario.getId().equals(produto.getLancedocomprador())) {

                    return;
                } else {

                    holder.clickCard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            AlertDialog.Builder alert = new AlertDialog.Builder(activity);

                            alert
                                    .setTitle("Que pena!")
                                    .setIcon(R.drawable.bebe_chorando)
                                    .setMessage("Esse produto já foi arrematado por " + produto.getNomedocomprador())
                                    .setCancelable(true)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }

                                    });

                            AlertDialog alertDialog = alert.create();
                            alertDialog.show();

                        }

                    });

                }

            }

        }.start();

    }

    private static String timeConversion(int totalSeconds) {

        int hours = totalSeconds / MINUTES_IN_AN_HOUR / SECONDS_IN_A_MINUTE;

        int minutes = (totalSeconds - (hoursToSeconds(hours)))
                / SECONDS_IN_A_MINUTE;

        int seconds = totalSeconds
                - ((hoursToSeconds(hours)) + (minutesToSeconds(minutes)));

        return hours + "h " + minutes + "m " + seconds + "s" ;
    }

    private static int hoursToSeconds(int hours) {
        return hours * MINUTES_IN_AN_HOUR * SECONDS_IN_A_MINUTE;
    }

    private static int minutesToSeconds(int minutes) {
        return minutes * SECONDS_IN_A_MINUTE;
    }



    @Override
    public int getItemCount() {

        return produtos.size();
    }

}