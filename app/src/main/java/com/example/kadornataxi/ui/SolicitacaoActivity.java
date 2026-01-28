package com.example.kadornataxi.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.kadornataxi.dao.ConfiguracaoDAO;
import com.example.kadornataxi.dao.ViagemDAO;
import com.example.kadornataxi.model.Viagem;

import java.util.Locale;

public class SolicitacaoActivity extends AppCompatActivity {
    EditText edOrigem, edDataOrigem, edHoraOrigem, edDestino, edDescricao, edKmsRodados, edValorViagem, edHoraEspera, edValorHoraEspera, edMotorista;
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
        configurarListeners();
    }

    private void configurarListeners() {
        viewBinding();

        edKmsRodados.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                calcularValorKm(s.toString(), edValorViagem);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        edHoraEspera.addTextChangedListener(new TextWatcher() {
            private boolean isUpdating = false;
            @Override
            public void afterTextChanged(Editable s) {
                if (isUpdating) return;
                isUpdating = true;

                String clean = s.toString().replaceAll("[^\\d]", "");

                if (clean.isEmpty()) {
                    edHoraEspera.setText("");
                    calcularValorEspera("", edValorHoraEspera); // Passa vazio pra zerar o valor
                    isUpdating = false;
                    return;
                }

                // 3. Limita a 4 números (lógica de fila)
                if (clean.length() > 4) {
                    // Pega apenas os últimos 4 dígitos digitados
                    clean = clean.substring(clean.length() - 4);
                } else {
                    // Padding com zeros à esquerda (ex: "2" vira "0002")
                    clean = String.format(Locale.getDefault(), "%04d", Long.parseLong(clean));
                }

                // 4. Monta a máscara HH:MM
                String hora = clean.substring(0, 2);
                String minuto = clean.substring(2, 4);
                String formatado = hora + ":" + minuto;

                // 5. Aplica o texto e reposiciona o cursor no final
                edHoraEspera.setText(formatado);
                edHoraEspera.setSelection(formatado.length());

                // 6. Calcula o valor financeiro
                calcularValorEspera(clean, edValorHoraEspera);

                isUpdating = false;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

        });
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
                edDescricao.getText().toString(),
                checkViagemSeparada.isChecked());
    }

    private void viewBinding() {
        edOrigem = findViewById(R.id.edOrigem);
        edDataOrigem = findViewById(R.id.edDataOrigem);
        edHoraOrigem = findViewById(R.id.edHoraOrigem);
        edDestino = findViewById(R.id.edDestino);
        edDescricao = findViewById(R.id.edDescricao);
        edKmsRodados = findViewById(R.id.edKmRodados);
        edValorViagem = findViewById(R.id.edValorViagem);
        edHoraEspera = findViewById(R.id.edHoraEspera);
        edValorHoraEspera = findViewById(R.id.edValorHoraEspera);
        edMotorista = findViewById(R.id.edMotorista);
        checkViagemSeparada = findViewById(R.id.checkViagemSeparada);
    }

    private void calcularValorKm(String kmStr, EditText output) {
        if (kmStr.isEmpty()) {
            output.setText("0,00");
            return;
        }
        try {
            float km = Float.parseFloat(kmStr.replace(",", "."));
            float total = km * (new ConfiguracaoDAO(this).getConfiguracao().getValorKmRodado());
            output.setText(String.format(Locale.getDefault(), "%.2f", total));
        } catch (NumberFormatException e) {
            output.setText("0,00");
        }
    }

    private void calcularValorEspera(String timeClean, EditText output) {
        if (timeClean.length() < 4) {
            output.setText("0,00");
            return;
        }
        try {
            float horas = Float.parseFloat(timeClean.substring(0,2));
            float minutos = Float.parseFloat(timeClean.substring(2,4));

            float tempoEmHoras = horas + (minutos / 60.0f);

            float total = tempoEmHoras * (new ConfiguracaoDAO(this).getConfiguracao().getValorHoraEspera());

            output.setText(String.format(Locale.getDefault(), "%.2f", total));
        } catch (Exception e) {
            output.setText("0,00");
        }
    }
    public void voltarMenu(View view) {
        finish();
    }
}