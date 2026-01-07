package com.example.kadornataxi.repository;

import com.example.kadornataxi.model.Viagem;

import java.util.ArrayList;

public class ViagemRepository {
    private static ArrayList<Viagem> viagens = new ArrayList<>();

    public static ArrayList<Viagem> getViagens() {
        return viagens;
    }

    public static void setViagens(ArrayList<Viagem> viagens) {ViagemRepository.viagens = viagens;
    }

    public static void adicionarViagem(Viagem viagem) {
        viagens.add(viagem);
    }

    public static void listarViagens(){
        if(viagens.isEmpty()) {
            System.out.println("Não há viagens cadastradas");
            return;
        }
        for (Viagem viagem : viagens) {
            System.out.println(viagem.toString());
        }
    }
}
