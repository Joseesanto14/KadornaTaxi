package com.example.kadornataxi.ui;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.kadornataxi.R;
import com.example.kadornataxi.dao.ViagemDAO;
import com.example.kadornataxi.database.DatabaseHelper;
import com.example.kadornataxi.model.Viagem;
import com.example.kadornataxi.repository.ViagemRepository;

import java.util.ArrayList;
import java.util.Objects;

public class SolicitacaoActivity extends AppCompatActivity {
    EditText edOrigem, edDataOrigem, edHoraOrigem, edDestino, edDataDestino, edHoraDestino, edJustificativa;
    CheckBox checkViagemSeparada;
    ViagemDAO dao;


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
        dao = new ViagemDAO(this);

        inicializarViews();


    }

    public void salvarViagemDb(View view) {
        Viagem viagem = criarViagemObjeto();
        dao.inserirNoDatabase(viagem);
        mensagemSucesso("Viagem criada com sucesso!");
    }
    public void salvarViagemRepository(View view) {
        Viagem viagem = criarViagemObjeto();
        ViagemRepository.addViagem(viagem);
        mensagemSucesso("Viagem criada com sucesso!");
    }

    @NonNull
    private Viagem criarViagemObjeto() {
        Viagem viagem = new Viagem(edOrigem.getText().toString(),
                edDataOrigem.getText().toString(), edHoraOrigem.getText().toString(),
                edDestino.getText().toString(), edDataDestino.getText().toString(),
                edHoraDestino.getText().toString(), edJustificativa.getText().toString(),
                checkViagemSeparada.isChecked());

        return viagem;
    }

    private void inicializarViews() {
        edOrigem = findViewById(R.id.edOrigem);
        edDataOrigem = findViewById(R.id.edDataOrigem);
        edHoraOrigem = findViewById(R.id.edHoraOrigem);
        edDestino = findViewById(R.id.edDestino);
        edDataDestino = findViewById(R.id.edDataDestino);
        edHoraDestino = findViewById(R.id.edHoraDestino);
        edJustificativa = findViewById(R.id.edJustificativa);
        checkViagemSeparada = findViewById(R.id.checkViagemSeparada);
    }

    private void mensagemSucesso(String mensagem) {
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
    }

    public void voltarMenu(View view) {
        finish();
    }
}