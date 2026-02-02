package com.example.kadornataxi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.kadornataxi.R;
import com.example.kadornataxi.dao.ConfiguracaoDAO;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void irSolicitacaoAcitivity(View view){
        ConfiguracaoDAO dao = new ConfiguracaoDAO(this);
        if (dao.configExiste()) {
            Intent intent = new Intent(this, SolicitacaoActivity.class);
            startActivity(intent);
        } else {
            startActivity(new Intent(this, ConfiguracaoActivity.class));
        }
    }

    public void irViagensAcitivity(View view){
        Intent intent = new Intent(this, ViagensActivity.class);
        startActivity(intent);
    }

    public void irGerarRelatorioAcitivity(View view){
        Intent intent = new Intent(this, GerarRelatorioActivity.class);
        startActivity(intent);
    }

    public void irConfigActivity(View view) {
        startActivity(new Intent(this, ConfiguracaoActivity.class));
    }
}