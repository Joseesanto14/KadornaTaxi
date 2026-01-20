package com.example.kadornataxi.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.kadornataxi.R;
import com.example.kadornataxi.model.Viagem;

import java.util.List;

public class ViagemAdapter extends RecyclerView.Adapter<ViagemAdapter.ViewHolder> {

    private List<Viagem> viagens;

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
        holder.txtResumo.setText(
                v.getDataOrigem() + " - " + v.getOrigem() + " -> " + v.getDestino()
        );
    }

    @Override
    public int getItemCount() {
        return viagens.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtResumo;

        ViewHolder(View itemView) {
            super(itemView);
            txtResumo = itemView.findViewById(R.id.txtResumo);
        }
    }
}