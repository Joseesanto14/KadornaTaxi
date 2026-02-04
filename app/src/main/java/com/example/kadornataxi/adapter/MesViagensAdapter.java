package com.example.kadornataxi.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kadornataxi.R;
import com.example.kadornataxi.dto.MesViagens;
import com.example.kadornataxi.interfaces.OnGerarRelatorioListener;

import java.util.List;

public class MesViagensAdapter extends RecyclerView.Adapter<MesViagensAdapter.ViewHolder> {

    private List<MesViagens> meses;
    private OnGerarRelatorioListener listener;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    public MesViagensAdapter(List<MesViagens> meses, OnGerarRelatorioListener listener) {
        this.meses = meses;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mes_viagens, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MesViagens mes = meses.get(position);

        holder.recyclerViagens.setRecycledViewPool(viewPool);

        holder.txtMes.setText(mes.getMesAno());
        holder.txtQuantidade.setText(mes.getViagens().size() + " viagens");

        holder.recyclerViagens.setLayoutManager(
                new LinearLayoutManager(holder.itemView.getContext()));

        holder.recyclerViagens.setAdapter(
                new ViagemAdapter(mes.getViagens()));

        holder.recyclerViagens.setVisibility(
                mes.isExpandido() ? View.VISIBLE : View.GONE);

        holder.btGerarRelatorio.setVisibility(
                mes.isExpandido() ? View.VISIBLE : View.GONE
        );

        holder.itemView.setOnClickListener(v -> {
            mes.setExpandido(!mes.isExpandido());
            notifyItemChanged(position);
        });

        holder.txtArrow.setText(
                mes.isExpandido() ? "▼" : "▶"
        );

        holder.btGerarRelatorio.setOnClickListener(v -> {
            listener.onGerarRelatorio(mes.getMesAno(), mes.getViagens());
        });

    }

    @Override
    public int getItemCount() {
        return meses.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtMes, txtQuantidade, txtArrow;
        Button btGerarRelatorio;
        RecyclerView recyclerViagens;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMes = itemView.findViewById(R.id.txtMes);
            txtQuantidade = itemView.findViewById(R.id.txtQuantidade);
            txtArrow = itemView.findViewById(R.id.txtArrow);
            recyclerViagens = itemView.findViewById(R.id.recyclerViagens);
            btGerarRelatorio = itemView.findViewById(R.id.btGerarRelatorio);
        }
    }
}
