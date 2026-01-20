package com.example.kadornataxi.ui;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kadornataxi.R;
import com.example.kadornataxi.adapter.MesViagensAdapter;
import com.example.kadornataxi.dao.ViagemDAO;
import com.example.kadornataxi.dto.MesViagens;
import com.example.kadornataxi.model.Viagem;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ViagensActivity extends AppCompatActivity {
    RecyclerView recyclerMeses;
    MesViagensAdapter adapter;
    ViagemDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_viagens);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerMeses = findViewById(R.id.recyclerMeses);
        recyclerMeses.setLayoutManager(new LinearLayoutManager(this));

        carregarViagens();
    }

    private void carregarViagens() {
        dao = new ViagemDAO(this);
        List<Viagem> todas = dao.getAllOrdenadoPorDataDesc();

        Map<String, List<Viagem>> viagensPorMes = new LinkedHashMap<>();

        for(Viagem viagem : todas) {
            String mesAno = viagem.getDataOrigem().substring(0, 7);

            viagensPorMes
                    .computeIfAbsent(mesAno, k -> new ArrayList<>())
                    .add(viagem);
        }

        List<MesViagens> listaMeses = new ArrayList<>();

        for (String mes : viagensPorMes.keySet()) {
            listaMeses.add(new MesViagens(mes, viagensPorMes.get(mes)));
        }

        adapter = new MesViagensAdapter(listaMeses);
        recyclerMeses.setAdapter(adapter);
    }
    public void voltarMenu(View view){
        finish();
    }
}