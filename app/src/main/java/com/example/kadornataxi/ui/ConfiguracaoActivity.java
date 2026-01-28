package com.example.kadornataxi.ui;

import static android.widget.Toast.LENGTH_LONG;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.kadornataxi.R;
import com.example.kadornataxi.dao.ConfiguracaoDAO;
import com.example.kadornataxi.database.DatabaseHelper;
import com.example.kadornataxi.model.Configuracao;

public class ConfiguracaoActivity extends AppCompatActivity {
    EditText edValorKmRodado, edValorHoraEsperada, edMotorista;
    ConfiguracaoDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_configuracao);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        iniciarViews();
        carregarConfigs();
    }
    public void voltarMenu(View view) {
        finish();
    }

    public void salvarConfiguracoes(View view) {
        Configuracao configuracao = new Configuracao(
                1,
                Float.parseFloat(edValorKmRodado.getText().toString()),
                Float.parseFloat(edValorHoraEsperada.getText().toString()),
                edMotorista.getText().toString());

        dao.configurar(configuracao,this);
        finish();
    }

    private void iniciarViews() {
        edValorKmRodado = findViewById(R.id.edValorKmRodado);
        edValorHoraEsperada = findViewById(R.id.edValorHoraEsperada);
        edMotorista = findViewById(R.id.edMotorista);
    }

    private void carregarConfigs() {
        dao = new ConfiguracaoDAO(this);

        if (dao.configExiste()) {
            Configuracao configuracao = dao.getConfiguracao();

            edValorKmRodado.setText(String.valueOf(configuracao.getValorKmRodado()));
            edValorHoraEsperada.setText(String.valueOf(configuracao.getValorHoraEspera()));
            edMotorista.setText(configuracao.getMotorista());

        } else {
            Toast.makeText(
                    this,
                    "Configure os preços antes de prosseguir",
                    LENGTH_LONG).show();
        }
    }
}