package com.myapp.simplegallery.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.myapp.simplegallery.R
import com.myapp.simplegallery.database.AppPreference
import com.myapp.simplegallery.databinding.ActivityMyProfileBinding

class MyProfileActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityMyProfileBinding
    lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var appPreference : AppPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appPreference = AppPreference.getInstance(this)!!

        initData()
        settingClickListener()
    }

    private fun initData(){

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val circularProgressDrawable = CircularProgressDrawable(binding.root.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        binding.textViewName.text = appPreference.readString("name","")
        binding.textViewEmail.text = appPreference.readString("email","")
        Glide.with(binding.root.context).load(appPreference.readString("photoUrl","")).placeholder(circularProgressDrawable).into(binding.imageViewProfilePicture)
    }

    private fun settingClickListener(){
        binding.buttonLogout.setOnClickListener(this)
        binding.imageViewBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v!=null){
            when(v.id){
                R.id.buttonLogout->{
                    mGoogleSignInClient.signOut().addOnCompleteListener {
                        appPreference.writeBoolean("login",false)
                        val intent = Intent(this, MainActivity::class.java)
                        Toast.makeText(this, "Logging Out", Toast.LENGTH_SHORT).show()
                        startActivity(intent)
                        finishAffinity()
                    }
                }
                R.id.imageViewBack->{
                    finish()
                }
            }
        }
    }
}