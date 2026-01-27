package com.example.kadornataxi.ui;

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
import com.example.kadornataxi.model.Viagem;

public class SolicitacaoActivity extends AppCompatActivity {
    EditText edOrigem, edDataOrigem, edHoraOrigem, edDestino, edJustificativa;
    CheckBox checkViagemSeparada;


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
        viewBinding();
    }

    public void salvarViagemDb(View view) {
        Viagem viagem = criarViagemObjeto();
        new ViagemDAO(this).inserirNoDatabase(viagem);
        Toast.makeText(this, "Viagem criada com sucesso!", Toast.LENGTH_SHORT).show();
    }

    @NonNull
    private Viagem criarViagemObjeto() {
        return new Viagem(edOrigem.getText().toString(),
                edDataOrigem.getText().toString(),
                edHoraOrigem.getText().toString(),
                edDestino.getText().toString(),
                edJustificativa.getText().toString(),
                checkViagemSeparada.isChecked());
    }

    private void viewBinding() {
        edOrigem = findViewById(R.id.edOrigem);
        edDataOrigem = findViewById(R.id.edDataOrigem);
        edHoraOrigem = findViewById(R.id.edHoraOrigem);
        edDestino = findViewById(R.id.edDestino);
        edJustificativa = findViewById(R.id.edDescricao);
        checkViagemSeparada = findViewById(R.id.checkViagemSeparada);
    }

    public void voltarMenu(View view) {
        finish();
    }
}