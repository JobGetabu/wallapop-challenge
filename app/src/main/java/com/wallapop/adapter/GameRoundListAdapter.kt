package com.wallapop.adapter

import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wallapop.R

class GameRoundListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: ArrayList<Pair<String, Boolean?>> = arrayListOf()

    fun getItems() = items

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_list_play_item, parent, false)
        return MyVH(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as MyVH
        holder.setUI(items[position])
    }

    fun addItem(item: Pair<String, Boolean?>) {
        this.items.plus(item)
        notifyItemInserted(items.size)
    }

    fun setItems(items: ArrayList<Pair<String, Boolean?>>) {
        this.items = items
        notifyDataSetChanged()

    }

    fun clear() {
        this.items.clear()
        notifyDataSetChanged()
    }

    fun refresh() {
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    internal class MyVH(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private var model: Pair<String, Boolean?>? = null
        private var name: TextView = itemView.findViewById(R.id.name)


        fun setUI(model: Pair<String, Boolean?>?) {
            this.model = model
            name.text = model?.first

            when (model?.second) {
                true -> {
                    name.setTextColor(Color.BLACK)
                    name.gravity = Gravity.START
                }
                false -> {
                    name.setTextColor(Color.YELLOW)
                    name.gravity = Gravity.END
                }
                else -> {
                    name.setTextColor(Color.BLACK)
                    name.gravity = Gravity.CENTER
                }
            }
        }
    }
}