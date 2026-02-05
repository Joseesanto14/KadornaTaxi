package com.example.kadornataxi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

public class ViagensActivity extends AppCompatActivity {

    private RecyclerView recyclerMeses;
    private MesViagensAdapter adapter;
    private SearchView searchView;
    private Chip chipSeparadas;
    private List<Viagem> todasAsViagensOriginal = new ArrayList<>();

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

        inicializarViews();
        setupFiltros();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarDadosDoBanco();
    }

    private void inicializarViews() {
        recyclerMeses = findViewById(R.id.recyclerMeses);
        recyclerMeses.setLayoutManager(new LinearLayoutManager(this));

        searchView = findViewById(R.id.searchView);
        chipSeparadas = findViewById(R.id.chipSeparadas);
    }

    private void carregarDadosDoBanco() {
        todasAsViagensOriginal = new ViagemDAO(this).getAllOrdenadoPorDataDesc();

        aplicarFiltros();
    }

    private void setupFiltros() {
        chipSeparadas.setOnCheckedChangeListener((buttonView, isChecked) -> {
            aplicarFiltros();
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText) {
                aplicarFiltros(); // Filtra a cada letra digitada
                return true;
            }
        });
    }

    private void aplicarFiltros() {
        String textoBusca = searchView.getQuery().toString().toLowerCase();
        boolean filtrarApenasSeparadas = chipSeparadas.isChecked();

        List<Viagem> listaFiltrada = new ArrayList<>();

        for (Viagem v : todasAsViagensOriginal) {
            boolean matchesBusca = false;
            boolean matchesChip = false;

            if (textoBusca.isEmpty()) {
                matchesBusca = true;
            } else {
                String destino = v.getDestino() != null ? v.getDestino().toLowerCase() : "";
                String origem = v.getOrigem() != null ? v.getOrigem().toLowerCase() : "";
                String data = v.getData() != null ? v.getData() : "";
                String motorista = v.getMotorista() != null ? v.getMotorista().toLowerCase() : "";
                String descricao = v.getDescricao() != null ? v.getDescricao().toLowerCase() : "";

                if (destino.contains(textoBusca) ||
                        origem.contains(textoBusca) ||
                        data.contains(textoBusca) ||
                        descricao.contains(textoBusca) ||
                        motorista.contains(textoBusca) ||
                        v.getClassificacao().contains(textoBusca))
                {
                    matchesBusca = true;
                }
            }

            if (!filtrarApenasSeparadas) {
                matchesChip = true;
            } else {
                if (v.getClassificacao() != null && !v.getClassificacao().equals("Comum")) {
                    matchesChip = true;
                }
            }

            if (matchesBusca && matchesChip) {
                listaFiltrada.add(v);
            }
        }

        atualizarRecyclerView(listaFiltrada);
    }

    private void atualizarRecyclerView(List<Viagem> listaParaExibir) {
        List<MesViagens> listaMeses = Viagem.gerarListaDeMeses(listaParaExibir);

        adapter = new MesViagensAdapter(listaMeses, (mesAno, viagensDoMes) -> {
            gerarRelatorioPdf(mesAno, viagensDoMes);
        });

        recyclerMeses.setAdapter(adapter);
    }

    private void gerarRelatorioPdf(String mesAno, List<Viagem> viagensDoMes) {
        ConfiguracaoDAO dao = new ConfiguracaoDAO(this);

        if (!dao.configExiste()) {
            Toast.makeText(this, "Configure o valor do KM antes!", Toast.LENGTH_LONG).show();
            irConfigActivity(null);
            return;
        }

        Configuracao config = dao.getConfiguracao();
        RelatorioPdfGenerator gerador = new RelatorioPdfGenerator(this);

        String nomeArquivo = "Relatorio_" + mesAno.replace("/", "-");

        try {
            gerador.gerarRelatorioMensal(nomeArquivo, viagensDoMes, config);
            Toast.makeText(this, "PDF salvo em Downloads!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao gerar PDF: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    // --- MÉTODOS DE BOTÕES (Chamados pelo XML) ---

    public void irSolicitacaoActivity(View view) {
        ConfiguracaoDAO dao = new ConfiguracaoDAO(this);
        if (dao.configExiste()) {
            startActivity(new Intent(this, SolicitacaoActivity.class));
        } else {
            Toast.makeText(this, "Configure o app primeiro!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, ConfiguracaoActivity.class));
        }
    }

    public void irConfigActivity(View view) {
        startActivity(new Intent(this, ConfiguracaoActivity.class));
    }
}