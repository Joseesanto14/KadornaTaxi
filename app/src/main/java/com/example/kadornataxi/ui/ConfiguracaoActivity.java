package com.example.kadornataxi.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.kadornataxi.model.Configuracao;

public class ConfiguracaoActivity extends AppCompatActivity {
    EditText edValorKmRodado, edValorHoraEsperada, edMotorista, edClassificacaoViagemSeparada;

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

        viewBinding();
        carregarConfigs();
    }

    //---------- UI ----------
    public void voltarMenu(View view) {
        finish();
    }

    private void carregarConfigs() {
        ConfiguracaoDAO dao = new ConfiguracaoDAO(this);

        if (dao.configExiste()) {
            Configuracao configuracao = dao.getConfiguracao();

            edValorKmRodado.setText(String.valueOf(configuracao.getValorKmRodado()));
            edValorHoraEsperada.setText(String.valueOf(configuracao.getValorHoraEspera()));
            edMotorista.setText(configuracao.getMotorista());
            edClassificacaoViagemSeparada.setText(configuracao.getClassificacaoViagemSeparada());
        }
    }

    private void viewBinding() {
        edValorKmRodado = findViewById(R.id.edValorKmRodado);
        edValorHoraEsperada = findViewById(R.id.edValorHoraEsperada);
        edMotorista = findViewById(R.id.edMotorista);
        edClassificacaoViagemSeparada = findViewById(R.id.edClassificacaoViagemSeparada);
    }

    //---------- Database ----------

    public void salvarConfiguracoes(View view) {
        if (todosCamposPreenchidos()) {
            if (edClassificacaoViagemSeparada.getText().toString().isEmpty())
                edClassificacaoViagemSeparada.setText("Comum");

            Configuracao configuracao = new Configuracao(
                    1,
                    Float.parseFloat(edValorKmRodado.getText().toString()),
                    Float.parseFloat(edValorHoraEsperada.getText().toString()),
                    edMotorista.getText().toString(),
                    edClassificacaoViagemSeparada.getText().toString());

            new ConfiguracaoDAO(getApplicationContext())
                    .configurar(configuracao, this);
            finish();
        } else {
            Toast.makeText(this, "Preencha todos os campos corretamente", Toast.LENGTH_SHORT).show();
        }


    }

    //---------- Listeners ----------

    private void listenerValorKm() {
        edValorKmRodado.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });
    }

    //---------- Lógica ----------

    private boolean todosCamposPreenchidos() {
        if (edValorKmRodado.getText().toString().isEmpty() &&
                edValorHoraEsperada.getText().toString().isEmpty() &&
                edMotorista.getText().toString().isEmpty()) {
            return false;
        } // verifica se o usuário preencheu os campos

        if ((edValorKmRodado.getText().toString().equals(".") &&
                edValorHoraEsperada.getText().toString().equals("."))
        ) {
            return false;
        } // verifica se o usuário preencheu os campos com pontos (inválido)

        // verifica se o usuário preencheu os campos com zeros
        return (Float.parseFloat(edValorKmRodado.getText().toString()) != 0f) ||
                (Float.parseFloat(edValorHoraEsperada.getText().toString()) != 0f);
    }
}