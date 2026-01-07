package com.example.kadornataxi.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.kadornataxi.R;
import com.example.kadornataxi.model.Viagem;
import com.example.kadornataxi.repository.ViagemRepository;

import java.util.ArrayList;
import java.util.Objects;

public class SolicitacaoActivity extends AppCompatActivity {
    EditText edOrigem, edDataOrigem, edHoraOrigem, edDestino, edDataDestino, edHoraDestino, edJustificativa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_solicitacao);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edOrigem = findViewById(R.id.edOrigem);
        edDataOrigem = findViewById(R.id.edDataOrigem);
        edHoraOrigem = findViewById(R.id.edHoraOrigem);
        edDestino = findViewById(R.id.edDestino);
        edDataDestino = findViewById(R.id.edDataDestino);
        edHoraDestino = findViewById(R.id.edHoraDestino);
        edJustificativa = findViewById(R.id.edJustificativa);
    }

    public void gerarViagem(View view){
        Viagem viagem = new Viagem(edOrigem.getText().toString(),
                edDataOrigem.getText().toString(), edHoraOrigem.getText().toString(),
                edDestino.getText().toString(), edDataDestino.getText().toString(),
                edHoraDestino.getText().toString(), edJustificativa.getText().toString());
//        ViagemRepository.adicionarViagem(viagem);
//        ViagemRepository.listarViagens();

        String recorte = viagem.getDataDestino().substring(3,10);
        System.out.println("Recorte: " + recorte);
        ViagemRepository.getViagensPorMes()
                .computeIfAbsent(recorte, k -> new ArrayList<>());
        ViagemRepository.getViagensPorMes().get(recorte).add(viagem);
        System.out.println("Adicionado viagem: " + viagem);
    }

    public void voltarMenu(View view){
        finish();
    }
}