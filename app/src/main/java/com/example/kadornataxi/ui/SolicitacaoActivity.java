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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;

public class SolicitacaoActivity extends AppCompatActivity {
    EditText edOrigem, edDataOrigem, edHoraOrigem, edDestino, edDescricao, edKmsRodados, edValorViagem, edHoraEspera, edValorHoraEspera, edMotorista, edValorTotal;
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
        preencherCampos();
    }

    public void voltarMenu(View view) {
        finish();
    }

    public void salvarViagemDb(View view) {
        Viagem viagem = criarViagemObjeto();
        new ViagemDAO(this).inserirNoDatabase(viagem);
        Toast.makeText(this, "Viagem criada com sucesso!", Toast.LENGTH_SHORT).show();
    }

    @NonNull
    private Viagem criarViagemObjeto() {

    }

    private void preencherCampos() {
        receberDataHoraDoSistema();
        edMotorista.setText(new ConfiguracaoDAO(this).getConfiguracao().getMotorista());
    }

    private void receberDataHoraDoSistema() {
        LocalDate data = LocalDateTime.now().toLocalDate();
        LocalTime hora = LocalDateTime.now().toLocalTime();

        String horaStr = hora.toString().replaceAll(":", "").substring(0,4);
        String[] partesData = data.toString().split("-");
        String dataStr = partesData[2] + partesData[1] + partesData[0];

        edHoraOrigem.setText(horaStr);
        edDataOrigem.setText(dataStr);
    }

    private void configurarListeners() {
        viewBinding();

        listenerData();
        listenerHora();

        listenerKmsRodados();

        listenerHoraEspera();
    }

    private void listenerHora() {
        edHoraOrigem.addTextChangedListener(new TextWatcher() {
            private boolean isUpdating = false;
            @Override
            public void afterTextChanged(Editable s) {
                if (isUpdating) { return;}
                isUpdating = true;

                String clean = s.toString().replaceAll("[^\\d]", "");

                if (clean.isEmpty()) {
                    edHoraOrigem.setText("");
                    isUpdating = false;
                    return;
                }

                if (clean.length() > 4) {
                    clean = clean.substring(clean.length() - 4);
                } else {
                    clean = String.format(Locale.getDefault(), "%04d", Long.parseLong(clean));
                }

                String horas = clean.substring(0,2);
                String minutos = clean.substring(2,4);

                String formatado = horas + ":" + minutos;

                edHoraOrigem.setText(formatado);
                edHoraOrigem.setSelection(formatado.length());

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

    private void listenerData() {
        edDataOrigem.addTextChangedListener(new TextWatcher() {
            private boolean isUpdating = false;
            @Override
            public void afterTextChanged(Editable s) {
                if (isUpdating) { return;}
                isUpdating = true;

                String clean = s.toString().replaceAll("[^\\d]", "");

                if (clean.isEmpty()) {
                    edDataOrigem.setText("");
                    isUpdating = false;
                    return;
                }

                if (clean.length() > 8) {
                    clean = clean.substring(clean.length() - 8);
                } else {
                    clean = String.format(Locale.getDefault(), "%08d", Long.parseLong(clean));
                }

                String dia = clean.substring(0,2);
                String mes = clean.substring(2,4);
                String ano = clean.substring(4,8);

                String formatado = dia + "/" + mes + "/" + ano;

                edDataOrigem.setText(formatado);
                edDataOrigem.setSelection(formatado.length());
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

    private void listenerKmsRodados(){
        edKmsRodados.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                calcularValorKm(s.toString(), edValorViagem);
                atualizarValorTotal();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
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

    private void listenerHoraEspera(){
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
                atualizarValorTotal();

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

    private void atualizarValorTotal(){
        try {
            float valorViagem = Float.parseFloat(edValorViagem.getText().toString().replace(",", "."));
            float valorEspera = Float.parseFloat(edValorHoraEspera.getText().toString().replace(",", "."));
            float valorTotal = valorViagem + valorEspera;
            edValorTotal.setText(String.format(Locale.getDefault(), "%.2f", valorTotal));
        } catch (NumberFormatException e) {
            edValorTotal.setText("0,00");
        }
    }
    private void viewBinding() {

    }
}