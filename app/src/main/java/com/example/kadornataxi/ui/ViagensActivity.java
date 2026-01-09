package com.example.kadornataxi.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.kadornataxi.R;
import com.example.kadornataxi.model.Viagem;
import com.example.kadornataxi.repository.ViagemRepository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ViagensActivity extends AppCompatActivity {
    TextView edViagens;

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
        edViagens = findViewById(R.id.edViagens);
        edViagens.setText("");

        exibirViagens();
    }
    public void voltarMenu(View view){
        finish();
    }

    private void exibirViagens(){
        edViagens.setText("");
        for(String mes : ViagemRepository.getTodasViagens().keySet()) {
            edViagens.setText(edViagens.getText().toString() + mes + ":\n");
            for(Viagem viagem : Objects.requireNonNull(ViagemRepository.getTodasViagens().get(mes))) {
                edViagens.setText(edViagens.getText().toString() + viagem.toString() + "\n");
            }
        }
    }

    private void exibirViagens(Map<String, List<Viagem>> viagens){
        edViagens.setText("");
        for(String mes : viagens.keySet()) {
            edViagens.setText(edViagens.getText().toString() + mes + ":\n");
            for(Viagem viagem : Objects.requireNonNull(viagens.get(mes))) {
                edViagens.setText(edViagens.getText().toString() + viagem.toString() + "\n");
            }
        }
    }
    public void btViagensSeparadas(View view){
        exibirViagens(ViagemRepository.getTodasViagensSeparadas());
    }

    public void btExibirTodasViagens(View view){
        exibirViagens();

    }
}