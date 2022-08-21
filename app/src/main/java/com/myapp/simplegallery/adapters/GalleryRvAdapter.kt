package com.myapp.simplegallery.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.ixuea.android.downloader.DownloadService
import com.ixuea.android.downloader.callback.DownloadListener
import com.ixuea.android.downloader.domain.DownloadInfo
import com.ixuea.android.downloader.exception.DownloadException
import com.myapp.simplegallery.databinding.CustomGalleryBinding
import com.myapp.simplegallery.extentions.getFileFromFiles
import com.myapp.simplegallery.extentions.getFileInFiles
import com.myapp.simplegallery.extentions.showLogE
import com.myapp.simplegallery.interfaces.GalleryClickListener
import com.myapp.simplegallery.models.MoviesModelItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

class GalleryRvAdapter(private val moviesModel: ArrayList<MoviesModelItem>, private val galleryClickListener: GalleryClickListener) :
    RecyclerView.Adapter<GalleryRvAdapter.ViewHolder>() {

    val downloadUrl = "https://howtodoandroid.com/images/"
    inner class ViewHolder(private val binding: CustomGalleryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binddata(moviesModel: MoviesModelItem) {

            val circularProgressDrawable = CircularProgressDrawable(binding.root.context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()

            binding.root.setOnClickListener{
                galleryClickListener.onClick(adapterPosition)
            }

            binding.galleryImages.setImageDrawable(circularProgressDrawable)
            val file =
                binding.root.context.getFileFromFiles("/Images/"+moviesModel.imageUrl)

            if (file.exists()) {
                Glide.with(binding.root.context).load(file).into(binding.galleryImages)
            } else {
                    CoroutineScope(Dispatchers.IO).launch {
                            downloadManager(downloadUrl+moviesModel.imageUrl,moviesModel.imageUrl,adapterPosition)
                    }
            }
        }

        private fun downloadManager(url: String, imageName: String, adapterposition : Int) {
            val downloadManager =
                DownloadService.getDownloadManager(binding.root.context);

            val downloadInfo = DownloadInfo.Builder().setUrl(url)
                .setPath(binding.root.context.getFileInFiles(imageName,"Images").path)
                .build()
            downloadInfo.downloadListener = object : DownloadListener {
                override fun onStart() {
                    showLogE("Download status","Started")
                }

                override fun onWaited() {
                    showLogE("Download status","Waited")
                }

                override fun onPaused() {
                    showLogE("Download status","Paused")
                }

                override fun onDownloading(progress: Long, size: Long) {
                    showLogE("Download status","Downloading")
                }

                override fun onRemoved() {

                }

                override fun onDownloadSuccess() {
                    showLogE("Download status","Success")
                   notifyItemChanged(adapterPosition)
                }

                override fun onDownloadFailed(e: DownloadException?) {
                    showLogE("Download status","Failed ${e?.message}")
                }

            }
            downloadManager.download(downloadInfo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CustomGalleryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binddata(moviesModel[position])
    }

    override fun getItemCount(): Int {
        return moviesModel.size
    }
}
