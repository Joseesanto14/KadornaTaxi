package com.example.kadornataxi.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

public class ViagensActivity extends AppCompatActivity {
    List<Viagem> viagens;
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

//        viagens = ViagemRepository.getViagens();

//        for(Viagem viagem : viagens){
//            edViagens.setText(edViagens.getText().toString() + viagem.toString() + "\n");
//        }

        for(String mes : ViagemRepository.getViagensPorMes().keySet()) {
            edViagens.setText(edViagens.getText().toString() + mes + ":\n");
            for(Viagem viagem : ViagemRepository.getViagensPorMes().get(mes)) {
                edViagens.setText(edViagens.getText().toString() + viagem.toString() + "\n");
                }
        }

    }
    public void voltarMenu(View view){
        finish();
    }
}