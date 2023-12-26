package com.pearshadow.saymeow;

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class InfiniteRecyclerAdapter(val allItems: List<Cat>, private val itemChangeCallback: (Int, MeowDirection) -> Unit) :
    RecyclerView.Adapter<InfiniteRecyclerAdapter.InfiniteRecyclerViewHolder>() {

    var swipeCount = 0

    var newList: List<Cat> = listOf(allItems[swipeCount + 1], allItems[swipeCount], allItems[swipeCount + 1], allItems[swipeCount])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfiniteRecyclerViewHolder = InfiniteRecyclerViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.custom_infinite_pager_layout, parent, false),
        ::incrementSwipeCount,
        itemChangeCallback
    )

    override fun onBindViewHolder(holder: InfiniteRecyclerViewHolder, position: Int) {
        holder.bind(newList[position])
    }

    override fun getItemCount(): Int = 4 // always

    class InfiniteRecyclerViewHolder(
        itemView: View,
        val incSwipeCount: (Int) -> Unit,
        val itemChangeCallback: (Int, MeowDirection) -> Unit
    ) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(cat: Cat) {
            Log.e("MEOW", "Binding $adapterPosition")
            val pagerTextView: TextView = itemView.findViewById(R.id.pagerTextView)
            val pagerImageView: ImageView = itemView.findViewById(R.id.pagerImageView)

            pagerTextView.text = cat.name
            Glide.with(itemView).load(cat.imageUrl).into(pagerImageView)

            itemView.findViewById<Button>(R.id.yesButton).setOnClickListener {
                itemChangeCallback(adapterPosition, MeowDirection.RIGHT)
                incSwipeCount(adapterPosition + 1)
            }

            itemView.findViewById<Button>(R.id.noButton).setOnClickListener {
                itemChangeCallback(adapterPosition, MeowDirection.LEFT)
                incSwipeCount(adapterPosition - 1)
            }
        }
    }

    private fun incrementSwipeCount(newAdapterPosition: Int) {
        Log.e("MEOW", "Incrementing for $newAdapterPosition")
        incrementSwipeCount()
        when (newAdapterPosition) {
            1, 3 -> {
                newList = listOf(allItems[swipeCount + 1], allItems[swipeCount], allItems[swipeCount + 1], allItems[swipeCount])
                Log.e("MEOW", "Notifying 0 and 2")
                notifyItemChanged(0)
                notifyItemChanged(2)
            }
            0, 2 -> {
                newList = listOf(allItems[swipeCount], allItems[swipeCount + 1], allItems[swipeCount], allItems[swipeCount + 1])
                Log.e("MEOW", "Notifying 1 and 3")
                notifyItemChanged(1)
                notifyItemChanged(3)
            }
        }
    }

    private fun incrementSwipeCount() {
        swipeCount += 1
        Log.e("MEOW COUNT", "Setting to $swipeCount")
    }

    enum class MeowDirection(val directionValue: Int) {
        LEFT(-1),
        RIGHT(1)
    }

}