package com.example.hygor.weltliche

import android.content.Intent
import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class Personagens : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    lateinit var criarPersonagem: ImageView
    lateinit var limitePersonagens: TextView
    lateinit var card1: LinearLayout
    lateinit var card2: LinearLayout
    lateinit var character1: Personagem
    lateinit var character2: Personagem
    lateinit var char1Nome: TextView
    lateinit var char1Avatar : ImageView
    lateinit var char1Skill: TextView
    lateinit var char1Idade: TextView
    lateinit var char1Sexo: ImageView
    lateinit var char1FOR: TextView
    lateinit var char1INT: TextView
    lateinit var char1PRE: TextView
    lateinit var char1DEX: TextView
    lateinit var char1RAC: TextView
    lateinit var char1MAN: TextView
    lateinit var char1VIG: TextView
    lateinit var char1PER: TextView
    lateinit var char1AUT: TextView
    lateinit var char1Formado: TextView
    lateinit var char1Ocupacao: TextView
    lateinit var char2Nome: TextView
    lateinit var char2Avatar: ImageView
    lateinit var char2Skill: TextView
    lateinit var char2Idade: TextView
    lateinit var char2Sexo: ImageView
    lateinit var char2FOR: TextView
    lateinit var char2INT: TextView
    lateinit var char2PRE: TextView
    lateinit var char2DEX: TextView
    lateinit var char2RAC: TextView
    lateinit var char2MAN: TextView
    lateinit var char2VIG: TextView
    lateinit var char2PER: TextView
    lateinit var char2AUT: TextView
    lateinit var char2Formado: TextView
    lateinit var char2Ocupacao: TextView
    lateinit var fim: TextView
    var numChars: Int = 0
    lateinit var imageOp: RequestOptions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personagens)



        //Esconder Action-bar
        getSupportActionBar()!!.hide()

        //Adicionando instancia ao banco de dados
        val db = FirebaseFirestore.getInstance()
        if (FirebaseApp.getApps(this).isEmpty())
        {
            val settings = FirebaseFirestoreSettings.Builder()
                    .setPersistenceEnabled(true)
                    .build()
            db!!.setFirestoreSettings(settings)
        }

        //Adicionando instancia ao BD de autenticação
        mAuth = FirebaseAuth.getInstance()

        //Adicionando elementos da interface
        criarPersonagem = findViewById(R.id.CriarPersonagem)
        limitePersonagens = findViewById(R.id.LimiteChars)
        card1 = findViewById(R.id.Card)
        card2 = findViewById(R.id.Card2)
        char1Nome = findViewById(R.id.Char1Nome)
        char1Avatar = findViewById(R.id.Char1Avatar)
        char1Skill = findViewById(R.id.Char1Skill)
        char1Idade = findViewById(R.id.Char1Idade)
        char1Sexo = findViewById(R.id.Char1Sexo)
        char1FOR = findViewById(R.id.Char1FOR)
        char1INT = findViewById(R.id.Char1INT)
        char1PRE = findViewById(R.id.Char1PRE)
        char1DEX = findViewById(R.id.Char1DEX)
        char1RAC = findViewById(R.id.Char1RAC)
        char1MAN = findViewById(R.id.Char1MAN)
        char1VIG = findViewById(R.id.Char1VIG)
        char1PER = findViewById(R.id.Char1PER)
        char1AUT = findViewById(R.id.Char1AUT)
        char1Formado = findViewById(R.id.Char1Formado)
        char1Ocupacao = findViewById(R.id.Char1Ocupacao)
        char2Nome = findViewById(R.id.Char2Nome)
        char2Avatar = findViewById(R.id.Char2Avatar)
        char2Skill = findViewById(R.id.Char2Skill)
        char2Idade = findViewById(R.id.Char2Idade)
        char2Sexo = findViewById(R.id.Char2Sexo)
        char2FOR = findViewById(R.id.Char2FOR)
        char2INT = findViewById(R.id.Char2INT)
        char2PRE = findViewById(R.id.Char2PRE)
        char2DEX = findViewById(R.id.Char2DEX)
        char2RAC = findViewById(R.id.Char2RAC)
        char2MAN = findViewById(R.id.Char2MAN)
        char2VIG = findViewById(R.id.Char2VIG)
        char2PER = findViewById(R.id.Char2PER)
        char2AUT = findViewById(R.id.Char2AUT)
        char2Formado = findViewById(R.id.Char2Formado)
        char2Ocupacao = findViewById(R.id.Char2Ocupacao)
        fim = findViewById(R.id.fim)

        criarPersonagem.setOnClickListener{
            val goToCriarPersonagem = Intent (getApplicationContext(), CriarPersonagem::class.java)
            startActivity(goToCriarPersonagem)
        }

        //Verificando numero de personagens do usuario
        val user = mAuth!!.currentUser
        val userRef = db.collection("users").document(user!!.uid)

        //Opcoes de imagem
       imageOp = RequestOptions().centerCrop()

        userRef.get().addOnCompleteListener { task ->
            var ab: DocumentSnapshot = task.result
            numChars = ab.getDouble("characters").toInt()
            Toast.makeText(getApplicationContext(), "Carregando personagens...", Toast.LENGTH_SHORT).show()
            if (numChars != 0) {
                //Fazendo a busca pelos personagens do usuario
                val todosPersonagens = db.collection("characters")
                val procuraPersonagens = todosPersonagens.whereEqualTo("uid", user.uid)

                //Tratando mudanças na UI para 1 ou 2 personagens
                procuraPersonagens.get().addOnCompleteListener { task2 ->
                    val meusPersonagens = task2.result
                    var x = 1;
                    for (snaps: DocumentSnapshot in meusPersonagens) {
                        if (x == 1) {
                            character1 = snaps.toObject(Personagem::class.java)
                        } else if (x == 2) {
                            character2 = snaps.toObject(Personagem::class.java)
                        }
                        x++
                    }
                    x--
                    // Um personagem
                    if (x >= 1) {
                        char1Nome.text = character1.getNome()
                        char1Skill.text = character1.getSkill()
                        Glide.with(this).load(character1.getProfilePic()).apply(imageOp).into(char1Avatar);

                        if (character1.sexo.equals("Feminino")) {
                            Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/weltliche-17c33.appspot.com/o/utils%2Ffem.png?alt=media&token=62418c66-7c0d-4251-9169-f9ea1b453179").into(char1Sexo)
                        } else if (character1.sexo.equals("Masculino")) {
                            Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/weltliche-17c33.appspot.com/o/utils%2Fmasc.png?alt=media&token=b32fe333-52ea-421c-aa68-6ba92de22b51").into(char1Sexo)
                        }

                        char1Idade.text = character1.getIdade().toString() + " anos"
                        char1FOR.text = character1.getForca().toString()
                        char1INT.text = character1.getInteligencia().toString()
                        char1PRE.text = character1.getPresenca().toString()
                        char1DEX.text = character1.getDestreza().toString()
                        char1RAC.text = character1.getRaciocinio().toString()
                        char1MAN.text = character1.getManipulacao().toString()
                        char1VIG.text = character1.getVigor().toString()
                        char1PER.text = character1.getPerseveranca().toString()
                        char1AUT.text = character1.getAutocontrole().toString()
                        char1Formado.text = character1.getFormado()
                        char1Ocupacao.text = character1.getOcupacao()
                        card1.visibility = View.VISIBLE
                        limitePersonagens.visibility = View.VISIBLE
                        criarPersonagem.visibility = View.VISIBLE

                    }
                    //Dois personagens
                    if (x >= 2) {
                        char2Nome.text = character2.getNome()
                        char2Skill.text = character2.getSkill()
                        Glide.with(this).load(character2.getProfilePic()).into(char2Avatar)

                        if (character2.sexo.equals("Feminino")) {
                            Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/weltliche-17c33.appspot.com/o/utils%2Ffem.png?alt=media&token=62418c66-7c0d-4251-9169-f9ea1b453179").into(char2Sexo)
                        } else if (character2.sexo.equals("Masculino")) {
                            Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/weltliche-17c33.appspot.com/o/utils%2Fmasc.png?alt=media&token=b32fe333-52ea-421c-aa68-6ba92de22b51").into(char2Sexo)
                        }

                        char2Idade.text = character2.getIdade().toString() + " anos"
                        char2FOR.text = character2.getForca().toString()
                        char2INT.text = character2.getInteligencia().toString()
                        char2PRE.text = character2.getPresenca().toString()
                        char2DEX.text = character2.getDestreza().toString()
                        char2RAC.text = character2.getRaciocinio().toString()
                        char2MAN.text = character2.getManipulacao().toString()
                        char2VIG.text = character2.getVigor().toString()
                        char2PER.text = character2.getPerseveranca().toString()
                        char2AUT.text = character2.getAutocontrole().toString()
                        char2Formado.text = character2.getFormado()
                        char2Ocupacao.text = character2.getOcupacao().toString()
                        card2.visibility = View.VISIBLE
                        criarPersonagem.visibility = View.GONE
                        limitePersonagens.visibility = View.GONE
                        fim.visibility = View.VISIBLE

                    }
                }
            } else {
                limitePersonagens.visibility = View.VISIBLE
                criarPersonagem.visibility = View.VISIBLE
            }

        }

        card1.setOnClickListener {
            val gotoCriarPersonagem = Intent(this, CriarPersonagem::class.java)
            gotoCriarPersonagem.putExtra("CRIAR_TYPE", 3)
            gotoCriarPersonagem.putExtra("character", character1)
            startActivity(gotoCriarPersonagem)
        }

        card2.setOnClickListener {
            val gotoCriarPersonagem = Intent(this, CriarPersonagem::class.java)
            gotoCriarPersonagem.putExtra("CRIAR_TYPE", 3)
            gotoCriarPersonagem.putExtra("character", character2)
            startActivity(gotoCriarPersonagem)
        }

    }
}
