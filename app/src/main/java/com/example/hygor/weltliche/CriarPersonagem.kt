package com.example.hygor.weltliche

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.mvc.imagepicker.ImagePicker
import android.content.Intent
import android.view.View
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.google.android.gms.tasks.OnSuccessListener
import android.support.annotation.NonNull
import com.google.android.gms.tasks.OnFailureListener
import android.R.attr.bitmap
import java.io.ByteArrayOutputStream
import com.google.firebase.storage.StorageReference
import android.provider.SyncStateContract.Helpers.update
import android.support.v4.app.FragmentActivity
import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import org.w3c.dom.Text
import java.util.*


class CriarPersonagem : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    var ready: Boolean = false;
    var loadedPicture: Bitmap? = null
    lateinit var storage: FirebaseStorage
    lateinit var initialPicture: Bitmap
    lateinit var editaHistoria: Button
    lateinit var explicaHistoria: TextView
    lateinit var editaAtributo: Button
    lateinit var explicaAtributo: TextView
    lateinit var editaMagias: Button
    lateinit var explicaMagias: TextView
    lateinit var wam_characterPic: ImageView
    lateinit var wam_characterName: EditText
    lateinit var profileURL: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_criar_personagem)

        //Adicionando instancia do Firebase
        mAuth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()

        //Adicionando instancia ao banco de dados
        val db = FirebaseFirestore.getInstance()
        if (FirebaseApp.getApps(this).isEmpty())
        {
            val settings = FirebaseFirestoreSettings.Builder()
                    .setPersistenceEnabled(true)
                    .build()
            db.setFirestoreSettings(settings)
        }

        //Esconder Action-bar
        getSupportActionBar()!!.hide()


        //Adicionando elementos da interface
        wam_characterName = findViewById<EditText>(R.id.Nickname)
        val wam_characterSkill = findViewById<EditText>(R.id.PersonalSkill)
        wam_characterPic= findViewById<ImageView>(R.id.ProfileImage)
        val salvaPersonagem = findViewById<Button>(R.id.SalvarPersonagem)
         editaHistoria = findViewById<Button>(R.id.EditarHistória)
         editaAtributo = findViewById<Button>(R.id.EditarAtributos)
         editaMagias = findViewById<Button>(R.id.EditarMagias)
         explicaHistoria = findViewById<TextView>(R.id.ExplicarHistoria)
         explicaAtributo = findViewById<TextView>(R.id.ExplicarAtributos)
         explicaMagias = findViewById<TextView>(R.id.ExplicarMagias)

        //Pegando a imagem inicial (sem mudança)
        initialPicture = (wam_characterPic.getDrawable() as BitmapDrawable).getBitmap()


        //Tratando criação do personagem
        salvaPersonagem.setOnClickListener{
            val charName = wam_characterName.text.toString()
            val charSkill = wam_characterSkill.text.toString()
            Toast.makeText(this@CriarPersonagem, "Salvando... aguarde!", Toast.LENGTH_LONG).show()

            if(!ready){
                Toast.makeText(this@CriarPersonagem, " Atenção: preencha nome, skill e imagem", Toast.LENGTH_SHORT).show()
            }
            else{

                //Verifica se esse personagem já existe
                val characterRef = db.collection("characters").document(charName)


                characterRef.update("npc", false)
                        .addOnSuccessListener {
                            Toast.makeText(this@CriarPersonagem, "Esse personagem já existe!", Toast.LENGTH_SHORT).show() }


                        //Personagem não existe, então upadte não foi possível
                        .addOnFailureListener {

                            Toast.makeText(this@CriarPersonagem, "Salvando... aguarde!", Toast.LENGTH_SHORT).show()
                            // Criando referencia para a foto do personagem no Storage
                            val storageRef = storage.reference
                            val profilepicRef = storageRef.child("characters/" + charName + "/profile")

                            //Save character in firestore and picture in storage
                            val baos = ByteArrayOutputStream()
                            loadedPicture!!.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                            val data = baos.toByteArray()

                            val uploadTask = profilepicRef.putBytes(data)
                            uploadTask.addOnFailureListener(OnFailureListener {
                                Toast.makeText(this@CriarPersonagem, " Falha no upload da imagem! ", Toast.LENGTH_SHORT).show()
                            }).addOnSuccessListener(OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                                profileURL = taskSnapshot.downloadUrl.toString()
                                val user = mAuth!!.currentUser
                                //Adicionando o personagem ao banco de dados
                                val new_char = HashMap<String, Any>()
                                new_char.put("uid", user!!.uid)
                                new_char.put("cid", UUID.randomUUID().toString())
                                new_char.put("nome", charName)
                                new_char.put("skill", charSkill)
                                new_char.put("profilePic", profileURL)
                                new_char.put("npc", false)
                                db.collection("characters").document(charName)
                                        .set(new_char)
                                        .addOnSuccessListener {
                                            Toast.makeText(this@CriarPersonagem, " Personagem criado", Toast.LENGTH_SHORT).show()
                                            salvaPersonagem.visibility = View.INVISIBLE
                                            editaHistoria.visibility = View.VISIBLE
                                            editaAtributo.visibility = View.VISIBLE
                                            editaMagias.visibility = View.VISIBLE
                                            explicaHistoria.visibility = View.VISIBLE
                                            explicaAtributo.visibility = View.VISIBLE
                                            explicaMagias.visibility = View.VISIBLE
                                            val userRef = db.collection("users").document(user!!.uid)
                                            userRef.get().addOnCompleteListener { task->
                                                val ab: DocumentSnapshot = task.result
                                                var chars = ab.getDouble("characters").toInt()
                                                chars++;
                                                userRef.update("characters", chars)
                                                Toast.makeText(this@CriarPersonagem, "Characters: " + chars.toString(), Toast.LENGTH_SHORT).show()
                                            }
                                        }

                                        .addOnFailureListener { e -> Log.w("DatabaseAdd", "Error writing document", e)
                                            Toast.makeText(this@CriarPersonagem, " Erro criando personagem", Toast.LENGTH_SHORT).show()}
                            })
                        }
            }
        }

        editaHistoria.setOnClickListener {
            val goToEditarHistoria = Intent (this, EditarHistoria::class.java)
            goToEditarHistoria.putExtra("charName", wam_characterName.text.toString())
            goToEditarHistoria.putExtra("charPic", profileURL)
            startActivity(goToEditarHistoria)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        val bitmap = ImagePicker.getImageFromResult(this, requestCode, resultCode, data)
        loadedPicture = bitmap
        wam_characterPic.setImageBitmap(loadedPicture)
        verifyComplete()
    }

    fun onPickImage(view: View) {
        // Click on image button
        ImagePicker.pickImage(this, "Selecione o avatar do seu personagem:")
    }

     fun verifyComplete() {
        val charName = findViewById<EditText>(R.id.Nickname).text.toString()
        val charSkill = findViewById<EditText>(R.id.PersonalSkill).text.toString()
        val salvaPersonagem = findViewById<Button>(R.id.SalvarPersonagem)

        //Pronto pra salvar personagem
        if( (!charName.isEmpty()) && (!charSkill.isEmpty()) && (!loadedPicture!!.equals(initialPicture))){
            salvaPersonagem.setBackgroundResource(R.drawable.roundedbutton)
            ready = true;
        }
        else{
            ready = false;
        }

    }

}
