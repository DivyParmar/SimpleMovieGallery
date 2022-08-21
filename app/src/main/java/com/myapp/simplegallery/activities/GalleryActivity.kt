package com.myapp.simplegallery.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.myapp.simplegallery.R
import com.myapp.simplegallery.adapters.GalleryRvAdapter
import com.myapp.simplegallery.database.AppViewModel
import com.myapp.simplegallery.database.MoviesTable
import com.myapp.simplegallery.database.Repository
import com.myapp.simplegallery.database.ViewModelFactory
import com.myapp.simplegallery.databinding.ActivityGalleryBinding
import com.myapp.simplegallery.extentions.isOnline
import com.myapp.simplegallery.extentions.noInternetDialog
import com.myapp.simplegallery.interfaces.Dao
import com.myapp.simplegallery.interfaces.GalleryClickListener
import com.myapp.simplegallery.interfaces.RetrofitService
import com.myapp.simplegallery.models.MoviesModelItem

class GalleryActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityGalleryBinding

    private lateinit var galleryRvAdapter: GalleryRvAdapter
    lateinit var appViewModel: AppViewModel
    private var moviesList = ArrayList<MoviesModelItem>()
    private val retrofitService = RetrofitService.getInstance()

    lateinit var moviesTable: MoviesTable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appViewModel = ViewModelProvider(this, ViewModelFactory(Repository(application,retrofitService))).get(AppViewModel::class.java)

        observingData()
        initData()

        settingClickListener()
    }
    private fun initData(){

        settingDataInRecyclerView()

        appViewModel.getMoviesTable()
    }

    private fun observingData(){

        appViewModel.movieList.observe(this ,Observer{
            moviesList.clear()

            it.forEach { movieModel->
                moviesList.add(
                    MoviesModelItem(
                        movieModel.category,
                        movieModel.desc,
                        movieModel.imageUrl.substring(34,movieModel.imageUrl.length),
                        movieModel.name
                )
                )
            }
            galleryRvAdapter.notifyDataSetChanged()

            moviesList.forEach {
                moviesTable = MoviesTable(it.category,it.desc,it.imageUrl,it.name)
                appViewModel.insertInMoviesTable(moviesTable)
            }
        })

        appViewModel.resultMoviesTable.observe(this, Observer {
            if (it.isEmpty()){
                if (isOnline()){
                    appViewModel.getAllMovies()
                }
                else{
                    noInternetDialog {
                        finish()
                    }
                }
            }
            else{
                moviesList.clear()
               it.forEach { movieTable->
                    moviesList.add(MoviesModelItem(
                        movieTable.category,movieTable.desc,movieTable.imageUrl,movieTable.name
                    ))
               }
                galleryRvAdapter.notifyDataSetChanged()
            }
        })
    }


    private fun settingDataInRecyclerView(){

        galleryRvAdapter = GalleryRvAdapter(moviesList,object : GalleryClickListener {
            override fun onClick(adapterPosition: Int) {

                val intent = Intent(this@GalleryActivity,MovieDetailsActivity::class.java)
                intent.putExtra("movieName",moviesList[adapterPosition].name)
                intent.putExtra("movieDesc",moviesList[adapterPosition].desc)
                intent.putExtra("movieImage",moviesList[adapterPosition].imageUrl)
                startActivity(intent)
            }
        })

        binding.recyclerViewGallery.adapter = galleryRvAdapter
    }

    private fun settingClickListener(){
        binding.textViewMyProfile.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v!=null){
            when(v.id){
                R.id.textViewMyProfile ->{
                    val intent = Intent(this,MyProfileActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}