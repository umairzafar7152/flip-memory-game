package com.chumairzafar.flipmemory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomRecyclerAdapter(private val listTitles: Array<String>, private val listImages: Array<Int>,
                            var onItemClick : (Int) ->Unit = {})
    : RecyclerView.Adapter<CustomRecyclerAdapter.CardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val rowView : View = LayoutInflater.from(parent.context).inflate(R.layout.types_list_member,
            parent, false)
        return CardViewHolder(rowView)
    }

    override fun getItemCount() = listImages.size

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.cardText!!.text = listTitles[position]
        holder.cardImage!!.setImageResource(listImages[position])
        holder.myContainer.setOnClickListener {
            onItemClick(position)
        }
    }


    class CardViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val myContainer: View = v.findViewById(R.id.optAnimals)
        val cardText: TextView? = v.findViewById(R.id.lText)
        val cardImage: ImageView? = v.findViewById(R.id.lIcon)
    }
}