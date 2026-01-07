package com.example.kadornataxi.repository;

import com.example.kadornataxi.model.Viagem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViagemRepository {
//    private static ArrayList<Viagem> viagens = new ArrayList<>();
    private static Map<String, List<Viagem>> viagensPorMes = new HashMap<>();

    public static Map<String, List<Viagem>> getViagensPorMes() {
        return viagensPorMes;
    }

    public static List<Viagem> getViagemDoMes(String mes){
        return viagensPorMes.get(mes);
    }

    //    public static ArrayList<Viagem> getViagens() {
//        return viagens;
//    }

//    public static void adicionarViagem(Viagem viagem) {
////        viagens.add(viagem);
//        viagensPorMes.put(viagem.getDataOrigem().substring(3, 10), viagens);
//    }

//    public static void listarViagens(){
//        if(viagens.isEmpty()) {
//            System.out.println("Não há viagens cadastradas");
//            return;
//        }
//        for (Viagem viagem : viagens) {
//            System.out.println(viagem.toString());
//        }
//    }
}
