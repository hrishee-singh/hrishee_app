package com.hrisheekesh.hrishee.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.hrisheekesh.hrishee.Content
import com.hrisheekesh.hrishee.R
import com.squareup.picasso.Picasso

class ImageRecyclerAdapter(val context: Context, val itemList: ArrayList<Content>) :
    RecyclerView.Adapter<ImageRecyclerAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_single, parent, false)

        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val content = itemList[position]
        //holder.imageView.setImageResource(content.myImage)
        Picasso.get().load(content.myImage).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imageView: ImageView = view.findViewById(R.id.imgRecylerRowItem)
    }

}



