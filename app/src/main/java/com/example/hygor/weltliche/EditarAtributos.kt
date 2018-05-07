package com.example.hygor.weltliche

import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.FirebaseStorage
import com.mvc.imagepicker.ImagePicker
import android.widget.RatingBar
import android.widget.RatingBar.OnRatingBarChangeListener



class EditarAtributos : AppCompatActivity() {

    lateinit var wam_characterName: TextView
    lateinit var wam_characterSkill: TextView
    lateinit var wam_characterPic: ImageView
    lateinit var wam_characterFOR: RatingBar
    lateinit var wam_characterDES: RatingBar
    lateinit var wam_characterVIG: RatingBar
    lateinit var wam_characterINT: RatingBar
    lateinit var wam_characterRAC: RatingBar
    lateinit var wam_characterPER: RatingBar
    lateinit var wam_characterPRE: RatingBar
    lateinit var wam_characterMAN: RatingBar
    lateinit var wam_characterAUT: RatingBar
    var ready : Boolean = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_atributos)


        //Esconder Action-bar
        getSupportActionBar()!!.hide()


        val ProfileInfo = getIntent()
        val characterName = ProfileInfo.getStringExtra("charName")
        val characterSkill = ProfileInfo.getStringExtra("charSkill")
        val characterPic = ProfileInfo.getStringExtra("charPic")
        ready = false;

        //Adicionando elementos da interface
        wam_characterName = findViewById<EditText>(R.id.Nickname)
        wam_characterSkill = findViewById<EditText>(R.id.PersonalSkill)
        wam_characterPic= findViewById<ImageView>(R.id.ProfileImage)
        val salvaPersonagem = findViewById<Button>(R.id.SalvarPersonagem)
        wam_characterFOR = findViewById(R.id.ratingFOR)
        wam_characterDES = findViewById(R.id.ratingDES)
        wam_characterVIG = findViewById(R.id.ratingVIG)
        wam_characterINT = findViewById(R.id.ratingINT)
        wam_characterRAC = findViewById(R.id.ratingRAC)
        wam_characterPER = findViewById(R.id.ratingPER)
        wam_characterPRE = findViewById(R.id.ratingPRE)
        wam_characterMAN = findViewById(R.id.ratingMAN)
        wam_characterAUT = findViewById(R.id.ratingAUT)

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
                Toast.makeText(this@EditarAtributos, "Atributos inválidos", Toast.LENGTH_SHORT).show()
            }
            else{


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
        }
        else if (((mental == 7) && (fisico == 8) && (social == 9)) || ((mental == 7) && (social == 8) && (fisico == 9)))
        {
            ready = true
        }
        else if (((social == 7) && (fisico == 8) && (mental == 9)) || ((social == 7) && (mental == 8) && (fisico == 9)))
        {
            ready = true
        }

    }
}
