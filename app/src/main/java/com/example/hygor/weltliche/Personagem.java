package com.example.hygor.weltliche;

/**
 * Created by Hygor on 5/6/2018.
 */

public class Personagem {

    public String uid;
    public String cid;
    public String nome;
    public String skill;
    public String profilePic;
    public boolean npc;
    public int idade;
    public String sexo;
    public String nacionalidade;
    public String vicio;
    public String virtude;
    public String formado;
    public String casa;
    public String ocupacao;
    public String historia;

    public Personagem() {}

    public Personagem(String uid, String cid, String nome, String skill, String profilePic, boolean npc ){

        this.uid = uid;
        this.cid = cid;
        this.nome = nome;
        this.skill = skill;
        this.profilePic = profilePic;
        this.npc = npc;

    }

    public Personagem(int idade, String sexo, String nacionalidade, String vicio, String virtude, String formado,
                      String casa, String ocupacao, String historia){

        this.idade = idade;
        this.sexo = sexo;
        this.nacionalidade = nacionalidade;
        this.vicio = vicio;
        this.virtude = virtude;
        this.formado = formado;
        this.casa = casa;
        this.ocupacao = ocupacao;
        this.historia = historia;

    }

    public String getUid() {
        return uid;
    }
    public String getCid() {
        return cid;
    }
    public String getNome() {
        return nome;
    }
    public String getSkill() {
        return skill;
    }
    public String getProfilePic() {
        return profilePic;
    }
    public boolean isNpc() {
        return npc;
    }
    public int getIdade() {
        return idade;
    }
    public String getNacionalidade() {
        return nacionalidade;
    }
    public String getVicio() {
        return vicio;
    }
    public String getVirtude() {
        return virtude;
    }
    public String getFormado() {
        return formado;
    }
    public String getCasa() {
        return casa;
    }
    public String getOcupacao() {
        return ocupacao;
    }
    public String getHistoria() {
        return historia;
    }

}
