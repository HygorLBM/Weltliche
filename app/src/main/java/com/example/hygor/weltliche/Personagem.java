package com.example.hygor.weltliche;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Hygor on 5/6/2018.
 */

public class Personagem implements Serializable {

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
    public int forca;
    public int destreza;
    public int vigor;
    public int inteligencia;
    public int raciocinio;
    public int perseveranca;
    public int presenca;
    public int manipulacao;
    public int autocontrole;

    public Personagem() {}

    public Personagem(String uid, String cid, String nome, String skill, String profilePic, boolean npc ){

        this.uid = uid;
        this.cid = cid;
        this.nome = nome;
        this.skill = skill;
        this.profilePic = profilePic;
        this.npc = npc;
        this.idade = 0;
        this.sexo = "";
        this.nacionalidade = "";
        this.vicio = "";
        this.virtude = "";
        this.formado = "";
        this.casa = "";
        this.ocupacao = "";
        this.historia = "";
        destreza = 0;
        vigor = 0;
        inteligencia = 0;
        raciocinio = 0;
        perseveranca = 0;
        manipulacao = 0;
        autocontrole = 0;
    }

    public void setHistoria(int idade, String sexo, String nacionalidade, String vicio, String virtude, String formado,
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

    public void setAtributos(int forca, int destreza, int vigor, int inteligencia, int raciocinio, int perseveranca,
                             int presenca, int manipulacao, int autocontrole){
        this.forca = forca;
        this.destreza = destreza;
        this.vigor = vigor;
        this.inteligencia = inteligencia;
        this.raciocinio = raciocinio;
        this.perseveranca = perseveranca;
        this.presenca = presenca;
        this.manipulacao = manipulacao;
        this.autocontrole = autocontrole;
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
    public int getForca() {
        return forca;
    }
    public int getDestreza() {
        return destreza;
    }
    public int getVigor() {
        return vigor;
    }
    public int getInteligencia() {
        return inteligencia;
    }
    public int getRaciocinio() {
        return raciocinio;
    }
    public int getPerseveranca() {
        return perseveranca;
    }
    public int getPresenca() {
        return presenca;
    }
    public int getManipulacao() {
        return manipulacao;
    }
    public int getAutocontrole() {
        return autocontrole;
    }

}
