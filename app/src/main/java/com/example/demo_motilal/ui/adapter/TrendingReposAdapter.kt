package com.example.demo_motilal.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.demo_motilal.R
import com.example.demo_motilal.data.models.Repos

class TrendingReposAdapter (val fragment: Fragment, val arrayList: ArrayList<Repos>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_repos, parent, false)
        return ReposListHolder(view)
    }

    override fun getItemCount() = arrayList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val reposHolder = holder as ReposListHolder
        reposHolder.bindItems(position = position, context = fragment, model = arrayList[position])
    }

    inner class ReposListHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bindItems(position: Int, context: Fragment, model: Repos) {
            with(itemView) {
                val cardMain = findViewById<CardView>(R.id.card_main)
                val tvName = findViewById<TextView>(R.id.tv_name)
                val tvFullName = findViewById<TextView>(R.id.tv_full_name)
                val btnDetails = findViewById<TextView>(R.id.tv_details)
                val callback = context as ReposCallback


                tvName.text = model.name
                tvFullName.text = model.full_name
                btnDetails.setOnClickListener{
                    callback.repoClicked(model.id ?: 0)
                }
            }

        }
    }

    fun addItems(arrayList: ArrayList<Repos>) {
        this.arrayList.clear()
        this.arrayList.addAll(arrayList)
        notifyDataSetChanged()
    }

    interface ReposCallback {
        fun repoClicked(id: Int)
    }
}