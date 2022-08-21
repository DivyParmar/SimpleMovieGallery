package com.myapp.simplegallery.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.myapp.simplegallery.R
import com.myapp.simplegallery.databinding.ActivityMovieDetailsBinding
import com.myapp.simplegallery.extentions.getFileFromFiles

class MovieDetailsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMovieDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()


        settingClickListener()
    }

    private fun initData(){
        binding.textViewMovieName.text = intent.extras?.get("movieName").toString()
        binding.textViewMovieDesc.text = intent.extras?.get("movieDesc").toString()

        val file =
            binding.root.context.getFileFromFiles("/Images/"+intent.extras?.get("movieImage").toString())

        if(file.exists()){
            Glide.with(binding.root.context).load(file).into(binding.imageViewMovieImage)
        }
    }

    private fun settingClickListener(){
        binding.imageViewBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v!=null){
            when(v.id){
                R.id.imageViewBack->{
                    finish()
                }
            }
        }
    }
}