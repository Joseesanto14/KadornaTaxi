package com.example.kadornataxi.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.kadornataxi.R;
import com.example.kadornataxi.model.Viagem;

import java.util.List;
import java.util.Locale;

public class ViagemAdapter extends RecyclerView.Adapter<ViagemAdapter.ViewHolder> {

    private final List<Viagem> viagens;

    public ViagemAdapter(List<Viagem> viagens) {
        this.viagens = viagens;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_resumo_viagens, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Viagem v = viagens.get(position);

        String dataHora = v.getDiaMes() + " • " + v.getHora();
        holder.txtDataHora.setText(dataHora);

        String valorTotal = "R$ " + String.format(Locale.getDefault(),
                "%.2f", v.getValorTotal());
        holder.txtValorTotal.setText(valorTotal);

        holder.txtOrigem.setText(v.getOrigem());

        holder.txtDestino.setText(v.getDestino());

        String kmsRodados = v.getKmsRodados() + " km";
        holder.txtKmsRodados.setText(kmsRodados);

        holder.txtDescricao.setText(v.getDescricao());
    }

    @Override
    public int getItemCount() {
        return viagens.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtDataHora, txtValorTotal, txtOrigem, txtDestino, txtKmsRodados, txtDescricao;

        ViewHolder(View itemView) {
            super(itemView);
            txtDataHora = itemView.findViewById(R.id.txtDataHora);
            txtValorTotal = itemView.findViewById(R.id.txtValorTotal);
            txtOrigem = itemView.findViewById(R.id.txtOrigem);
            txtDestino = itemView.findViewById(R.id.txtDestino);
            txtKmsRodados = itemView.findViewById(R.id.txtKmsRodados);
            txtDescricao = itemView.findViewById(R.id.txtDescricao);
        }
    }
}