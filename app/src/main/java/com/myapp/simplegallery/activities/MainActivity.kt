package com.myapp.simplegallery.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.firebase.auth.FirebaseAuth
import com.myapp.simplegallery.R
import com.myapp.simplegallery.database.AppPreference
import com.myapp.simplegallery.databinding.ActivityMainBinding
import com.myapp.simplegallery.fragments.LootieProgreeFragment

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityMainBinding

    lateinit var name:String
    lateinit var email:String
    lateinit var idToken:String
    lateinit var photoUrl:String
    private var firebaseAuth: FirebaseAuth? = null
    private lateinit var appPreference : AppPreference


    val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val results = Auth.GoogleSignInApi.getSignInResultFromIntent(result?.data!!)
            handleSignInResult(results!!)
        }
    }

    private lateinit var googleSignInClient:GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        appPreference = AppPreference.getInstance(this)!!

        if (appPreference.readBoolean("login",false)){
            gotoGalleryActivity()
        }
        settingClickListener()
    }


    fun googleSignIn(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id)) //you can also use R.string.default_web_client_id
            .requestEmail()
            .build()
        googleSignInClient= GoogleSignIn.getClient(this,gso)
        resultLauncher.launch(googleSignInClient.signInIntent)
    }


    private fun handleSignInResult(result: GoogleSignInResult) {
        if (result.isSuccess) {
            val account = result.signInAccount
            idToken = account!!.idToken.toString()
            name = account.displayName.toString()
            email = account.email.toString()
            photoUrl = account.photoUrl.toString()
            appPreference.writeString("email",email)
            appPreference.writeString("name",name)
            appPreference.writeString("photoUrl",photoUrl)
            appPreference.writeBoolean("login",true)
            gotoGalleryActivity()
        } else {
            LootieProgreeFragment.newInstance().dismiss()
            Toast.makeText(this, "Login Unsuccessful", Toast.LENGTH_SHORT).show()
        }
    }

    private fun gotoGalleryActivity() {
        val intent = Intent(this, GalleryActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun settingClickListener(){
        binding.googleSignup.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v!=null){
            when(v.id){
                R.id.googleSignup ->{
                    LootieProgreeFragment.newInstance().show(supportFragmentManager,"Progress")
                    googleSignIn()
                }
            }
        }
    }
}