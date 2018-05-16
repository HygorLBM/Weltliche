package com.example.hygor.weltliche

import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.FirebaseStorage
import com.mvc.imagepicker.ImagePicker
import android.widget.RatingBar
import android.widget.RatingBar.OnRatingBarChangeListener
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.SetOptions


class EditarAtributos : AppCompatActivity() {

    lateinit var wam_characterName: TextView
    lateinit var wam_characterSkill: TextView
    lateinit var wam_characterFOR: RatingBar
    lateinit var wam_characterDES: RatingBar
    lateinit var wam_characterVIG: RatingBar
    lateinit var wam_characterINT: RatingBar
    lateinit var wam_characterRAC: RatingBar
    lateinit var wam_characterPER: RatingBar
    lateinit var wam_characterPRE: RatingBar
    lateinit var wam_characterMAN: RatingBar
    lateinit var wam_characterAUT: RatingBar
    lateinit var character: Personagem
    lateinit var salvaPersonagem: Button
    var ready : Boolean = false
    lateinit var imageOp: RequestOptions



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_atributos)



        //Esconder Action-bar
        getSupportActionBar()!!.hide()

        //Opcoes de imagem
        imageOp = RequestOptions().centerCrop()

        //Recebendo informações de personagem da tela anterior
        val ProfileInfo = getIntent()
        character = ProfileInfo.getSerializableExtra("character") as Personagem
        val characterName = character.getNome()
        val characterPic = character.getProfilePic()
        val characterSkill = character.getSkill()
        ready = false;

        //Adicionando instancia ao banco de dados
        val db = FirebaseFirestore.getInstance()
        if (FirebaseApp.getApps(this).isEmpty())
        {
            val settings = FirebaseFirestoreSettings.Builder()
                    .setPersistenceEnabled(true)
                    .build()
            db.setFirestoreSettings(settings)
        }


        //Adicionando elementos da interface
        wam_characterName = findViewById<TextView>(R.id.NomePersonagem)
        val characterEXP = findViewById<TextView>(R.id.EXPPersonagem)
        wam_characterSkill = findViewById<TextView>(R.id.SkillPersonagem)
        val wam_characterPic = findViewById<ImageView>(R.id.AvatarPersonagem)
        salvaPersonagem = findViewById(R.id.SalvarPersonagem)
        wam_characterFOR = findViewById(R.id.ratingFOR)
        wam_characterDES = findViewById(R.id.ratingDES)
        wam_characterVIG = findViewById(R.id.ratingVIG)
        wam_characterINT = findViewById(R.id.ratingINT)
        wam_characterRAC = findViewById(R.id.ratingRAC)
        wam_characterPER = findViewById(R.id.ratingPER)
        wam_characterPRE = findViewById(R.id.ratingPRE)
        wam_characterMAN = findViewById(R.id.ratingMAN)
        wam_characterAUT = findViewById(R.id.ratingAUT)

        //Setando o texto dos elementos:
        wam_characterName.setText(characterName)
        wam_characterSkill.setText(characterSkill)
        Glide.with(this).load(characterPic).apply(imageOp).into(wam_characterPic);


        //Meétodos para mudança de status: Verifica se >= 1 e se a configuração já está pronta
        wam_characterFOR.setOnRatingBarChangeListener(OnRatingBarChangeListener { ratingBar, rating, fromUser ->

            //Minimo de um status é 1:
            if (rating < 1.0f) {
                ratingBar.rating = 1.0f
            }
            verify_stats()
        })
        wam_characterDES.setOnRatingBarChangeListener(OnRatingBarChangeListener { ratingBar, rating, fromUser ->

            //Minimo de um status é 1:
            if (rating < 1.0f) {
                ratingBar.rating = 1.0f
            }
            verify_stats()
        })
        wam_characterVIG.setOnRatingBarChangeListener(OnRatingBarChangeListener { ratingBar, rating, fromUser ->

            //Minimo de um status é 1:
            if (rating < 1.0f) {
                ratingBar.rating = 1.0f
            }
            verify_stats()
        })
        wam_characterINT.setOnRatingBarChangeListener(OnRatingBarChangeListener { ratingBar, rating, fromUser ->

            //Minimo de um status é 1:
            if (rating < 1.0f) {
                ratingBar.rating = 1.0f
            }
            verify_stats()
        })
        wam_characterRAC.setOnRatingBarChangeListener(OnRatingBarChangeListener { ratingBar, rating, fromUser ->

            //Minimo de um status é 1:
            if (rating < 1.0f) {
                ratingBar.rating = 1.0f
            }
            verify_stats()
        })
        wam_characterPER.setOnRatingBarChangeListener(OnRatingBarChangeListener { ratingBar, rating, fromUser ->

            //Minimo de um status é 1:
            if (rating < 1.0f) {
                ratingBar.rating = 1.0f
            }
            verify_stats()
        })
        wam_characterPRE.setOnRatingBarChangeListener(OnRatingBarChangeListener { ratingBar, rating, fromUser ->

            //Minimo de um status é 1:
            if (rating < 1.0f) {
                ratingBar.rating = 1.0f
            }
            verify_stats()
        })
        wam_characterMAN.setOnRatingBarChangeListener(OnRatingBarChangeListener { ratingBar, rating, fromUser ->

            //Minimo de um status é 1:
            if (rating < 1.0f) {
                ratingBar.rating = 1.0f
            }
            verify_stats()
        })
        wam_characterAUT.setOnRatingBarChangeListener(OnRatingBarChangeListener { ratingBar, rating, fromUser ->

            //Minimo de um status é 1:
            if (rating < 1.0f) {
                ratingBar.rating = 1.0f
            }
            verify_stats()
        })

        salvaPersonagem.setOnClickListener{

            if (!ready){
                Toast.makeText(getApplicationContext(), "Atributos inválidos", Toast.LENGTH_SHORT).show()
            }
            else{
                character.setAtributos(wam_characterFOR.rating.toInt(), wam_characterDES.rating.toInt(), wam_characterVIG.rating.toInt(),
                                       wam_characterINT.rating.toInt(), wam_characterRAC.rating.toInt(), wam_characterPER.rating.toInt(),
                                       wam_characterPRE.rating.toInt(), wam_characterMAN.rating.toInt(), wam_characterAUT.rating.toInt())

                db.collection("characters").document(character.getNome())
                        .set(character, SetOptions.merge())
                        .addOnSuccessListener {
                            Toast.makeText(getApplicationContext(), "Personagem atualizado!", Toast.LENGTH_SHORT).show()
                            if(character.sexo == "") {
                                val gotoCriarPersonagemPersonagem = Intent(this, Personagens::class.java)
                                gotoCriarPersonagemPersonagem.putExtra("CRIAR_TYPE", 2)
                                startActivity(gotoCriarPersonagemPersonagem)
                            }
                            else{
                                val goToPersonagens = Intent (this, Personagens::class.java)
                                goToPersonagens.putExtra("character" , character)
                                startActivity(goToPersonagens)
                            }
                        }
                        .addOnFailureListener { e -> Log.w("DatabaseAdd", "Error writing document", e)
                        }



            }

        }


    }

    fun onStatsChange (view: View) {
        // Click on image button
        ImagePicker.pickImage(this, "Selecione o avatar do seu personagem:")
    }


    private fun verify_stats() {

        var fisico = wam_characterFOR.rating.toInt() + wam_characterDES.rating.toInt() + wam_characterVIG.rating.toInt()
        var mental = wam_characterINT.rating.toInt() + wam_characterRAC.rating.toInt() + wam_characterPER.rating.toInt()
        var social = wam_characterPRE.rating.toInt() + wam_characterMAN.rating.toInt() + wam_characterAUT.rating.toInt()


        ready = false

        if (((fisico == 7) && (mental == 8) && (social == 9)) || ((fisico == 7) &&  (social == 8) && (mental == 9)))
        {
            ready = true
            salvaPersonagem.setBackgroundResource(R.drawable.roundedbutton)

        }
        else if (((mental == 7) && (fisico == 8) && (social == 9)) || ((mental == 7) && (social == 8) && (fisico == 9)))
        {
            ready = true
            salvaPersonagem.setBackgroundResource(R.drawable.roundedbutton)
        }
        else if (((social == 7) && (fisico == 8) && (mental == 9)) || ((social == 7) && (mental == 8) && (fisico == 9)))
        {
            ready = true
            salvaPersonagem.setBackgroundResource(R.drawable.roundedbutton)
        }

    }
}
