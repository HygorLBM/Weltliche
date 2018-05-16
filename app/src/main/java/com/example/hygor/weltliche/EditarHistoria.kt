package com.example.hygor.weltliche

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.R.array
import android.util.Log
import android.widget.*
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.SetOptions
import android.content.Intent
import com.bumptech.glide.Glide


class EditarHistoria : AppCompatActivity() {

    lateinit var character: Personagem
    lateinit var idadePersonagem: EditText
    lateinit var sexoPersonagem: Spinner
    lateinit var vicio: Spinner
    lateinit var virtude: Spinner
    lateinit var formado: Spinner
    lateinit var casa: Spinner
    lateinit var ocupacao: Spinner
    lateinit var nacionalidade: EditText
    lateinit var historia: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_historia)


        //Esconder Action-bar
        getSupportActionBar()!!.hide()


        val ProfileInfo = getIntent()
        character = ProfileInfo.getSerializableExtra("character") as Personagem
        val characterName = character.getNome()
        val characterPic = character.getProfilePic()




        //Adicionando instancia ao banco de dados
        val db = FirebaseFirestore.getInstance()
        if (FirebaseApp.getApps(this).isEmpty())
        {
            val settings = FirebaseFirestoreSettings.Builder()
                    .setPersistenceEnabled(true)
                    .build()
            db.setFirestoreSettings(settings)
        }

        //Elementos de UI
        val avatarPersonagem = findViewById<ImageView>(R.id.ProfileImage)
        val nomePersonagem = findViewById<TextView>(R.id.NomePersonagem)
        val xpPersonagem = findViewById<TextView>(R.id.EXP_Personagem)
        val salvaDetalhes = findViewById<Button>(R.id.SalvarCaracteristicas)
        idadePersonagem = findViewById<EditText>(R.id.IdadePersonagem)
        sexoPersonagem = findViewById<Spinner>(R.id.SexoPersonagem)
        vicio = findViewById<Spinner>(R.id.Vicio)
        virtude = findViewById<Spinner>(R.id.Virtude)
        formado = findViewById<Spinner>(R.id.Formado)
        casa = findViewById<Spinner>(R.id.EscolherCasa)
        ocupacao = findViewById<Spinner>(R.id.EscolherOcupação)
        nacionalidade = findViewById<EditText>(R.id.Nacionalidade)
        historia = findViewById<EditText>(R.id.ResumoHistoria)

        //Setando o texto dos elementos:
        nomePersonagem.setText(characterName)
        Glide.with(this).load(characterPic).into(avatarPersonagem);





        // Spinner Sexo/Vicio/Virtude/Formado/Casa/Ocupacao
        val staticAdapter0 = ArrayAdapter
                .createFromResource(this, R.array.sexo,
                        android.R.layout.simple_spinner_item)
        val staticAdapter1 = ArrayAdapter
                .createFromResource(this, R.array.vicio,
                        android.R.layout.simple_spinner_item)
        val staticAdapter2 = ArrayAdapter
                .createFromResource(this, R.array.virtude,
                        android.R.layout.simple_spinner_item)
        val staticAdapter3 = ArrayAdapter
                .createFromResource(this, R.array.formado,
                        android.R.layout.simple_spinner_item)
        val staticAdapter4 = ArrayAdapter
                .createFromResource(this, R.array.casas,
                        android.R.layout.simple_spinner_item)
        val staticAdapter5 = ArrayAdapter
                .createFromResource(this, R.array.ocupacao,
                        android.R.layout.simple_spinner_item)

        // Specify the layout to use when the list of choices appears
        staticAdapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        staticAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        staticAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        staticAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        staticAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        staticAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the spinner
        sexoPersonagem.adapter = staticAdapter0
        vicio.adapter = staticAdapter1
        virtude.adapter = staticAdapter2
        formado.adapter = staticAdapter3
        casa.adapter = staticAdapter4
        ocupacao.adapter = staticAdapter5

        salvaDetalhes.setOnClickListener{
            //Adicionando a conta ao banco de dados com atuais 0 personagens
            character.setHistoria(idadePersonagem.text.toString().toInt(), sexoPersonagem.selectedItem.toString(), nacionalidade.text.toString(),
                                                     vicio.selectedItem.toString().replace("Vício: ", ""), virtude.selectedItem.toString().replace("Virtude: ", ""),
                                                     formado.selectedItem.toString(), casa.selectedItem.toString(),  ocupacao.selectedItem.toString(), historia.text.toString())

           /* val caracteristicas = HashMap<String, Any>()
            caracteristicas.put("idade", idadePersonagem.text.toString().toInt())
            caracteristicas.put("sexo", sexoPersonagem.selectedItem.toString())
            caracteristicas.put("nacionalidade", nacionalidade.text.toString())
            caracteristicas.put("vicio", vicio.selectedItem.toString().replace("Vício: ", ""))
            caracteristicas.put("virtude", virtude.selectedItem.toString().replace("Virtude: ", ""))
            caracteristicas.put("formado", formado.selectedItem.toString())
            caracteristicas.put("casa", casa.selectedItem.toString())
            caracteristicas.put("ocupacao", ocupacao.selectedItem.toString())
            caracteristicas.put("história",historia.text.toString())*/
            db.collection("characters").document(nomePersonagem.text.toString())
                    .set(character, SetOptions.merge())
                    .addOnSuccessListener {
                        Toast.makeText(this@EditarHistoria, "Características salvas", Toast.LENGTH_SHORT).show()
                        if (character.forca == 0) {
                            val gotoCriarPersonagem = Intent(this, CriarPersonagem::class.java)
                            gotoCriarPersonagem.putExtra("CRIAR_TYPE", 1)
                            gotoCriarPersonagem.putExtra("character", character)
                            startActivity(gotoCriarPersonagem)
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
