package com.example.kadornataxi.repository;

import com.example.kadornataxi.model.Viagem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViagemRepository {
    private static Map<String, List<Viagem>> viagensPorMes = new HashMap<>();

    public static Map<String, List<Viagem>> getViagensPorMes() {
        return viagensPorMes;
    }

    public static List<Viagem> getViagemDoMes(String mes){
        return viagensPorMes.get(mes);
    }
}
