package com.example.tripletriadwiki

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.color.MaterialColors

class ListCardAdapter(private val listCard: ArrayList<Card>) : RecyclerView.Adapter<ListCardAdapter.ListViewHolder>(){
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_list_card, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val card = listCard[position]
        Glide.with(holder.itemView.context)
            .load(card.photo)
            .apply(RequestOptions().override(80, 80))
            .into(holder.imgPhoto)
        holder.txtName.text = card.name
        holder.txtDetail.text = "Click to show details"
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listCard[holder.adapterPosition]) }
        //if(card.id %2 != 0){
            //holder.itemView.setBackgroundColor(Color.parseColor("#95AACD"))
        //}else if(card.id %2 == 0){
            //holder.itemView.setBackgroundColor(Color.parseColor("#FF91A7"))
        //}
    }

    override fun getItemCount(): Int {
        return listCard.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var layout: LinearLayout = itemView.findViewById(R.id.item_layout)
        var txtName: TextView = itemView.findViewById(R.id.txt_item_name)
        var imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        var txtDetail: TextView = itemView.findViewById(R.id.txt_item_detail)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Card)
    }

}