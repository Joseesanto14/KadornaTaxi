package com.example.kadornataxi.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kadornataxi.R;
import com.example.kadornataxi.adapter.MesViagensAdapter;
import com.example.kadornataxi.dao.ConfiguracaoDAO;
import com.example.kadornataxi.dao.ViagemDAO;
import com.example.kadornataxi.dto.MesViagens;
import com.example.kadornataxi.model.Configuracao;
import com.example.kadornataxi.model.Viagem;
import com.example.kadornataxi.report.RelatorioPdfGenerator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ViagensActivity extends AppCompatActivity {
    RecyclerView recyclerMeses;
    MesViagensAdapter adapter;

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
        ViagemDAO dao = new ViagemDAO(this);
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

    public void gerarRelatorio(View view){
        Configuracao configuracao = new ConfiguracaoDAO(this).getConfiguracao();

        List<Viagem> viagens = new ViagemDAO(this).getViagemByPeriodo("2026-01");

        RelatorioPdfGenerator gerador = new RelatorioPdfGenerator(this);

        String caminho = gerador.gerarRelatorioMensal("2026-01", viagens, configuracao).toString();

        Log.d("Relatorio", "Caminho: " + caminho);
        Toast.makeText(this, "Caminho: " +
                caminho,
                Toast.LENGTH_LONG).show();
    }

    public void voltarMenu(View view){
        finish();
    }
}