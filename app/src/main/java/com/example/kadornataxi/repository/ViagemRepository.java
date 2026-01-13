package com.example.kadornataxi.repository;

import com.example.kadornataxi.model.Viagem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ViagemRepository {
    private static Map<String, List<Viagem>> viagensPorMes = new HashMap<>();

    public static void addViagem(Viagem viagem) {
        // verifica se existe o mês onde a viagem está sendo gravada, se não existir cria uma lista para armazenar a viagem.
        getTodasViagens()
                .computeIfAbsent(viagem.getRecorteViagem(), k -> new ArrayList<>());
        // grava a viagem na lista do mês.
        Objects.requireNonNull(ViagemRepository.getTodasViagens().get(viagem.getRecorteViagem())).add(viagem);
    }

    public static Map<String, List<Viagem>> getTodasViagens() {
        return viagensPorMes;
    }

    public static List<Viagem> getViagensDoMes(String mes) {
        return Objects.requireNonNull(ViagemRepository.getTodasViagens().get(mes));
    }

    public static Map<String, List<Viagem>> getTodasViagensSeparadas() {
        HashMap<String, List<Viagem>> viagensSeparadas = new HashMap<>();
        for (String mes : ViagemRepository.getTodasViagens().keySet()) {
            if (mes.contains("TRECHO 2")) {
                viagensSeparadas.computeIfAbsent(mes, k -> ViagemRepository.getViagensDoMes(mes));
            }
        }
        return viagensSeparadas;
    }
}
