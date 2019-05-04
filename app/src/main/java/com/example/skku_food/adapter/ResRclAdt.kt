package com.example.skku_food.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skku_food.R
import com.example.skku_food.data.ResSimpleData
import com.example.skku_food.menuActivity.ResMenuActivity
import kotlinx.android.synthetic.main.res_item.view.*

class ResRclAdt(private val items:List<ResSimpleData>, private val menu: String):RecyclerView.Adapter<ResRclAdt.ResViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ResViewHolder(parent)

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ResViewHolder, position: Int) {

        items[position].let { item ->
            with(holder){
                nm.text = item.name
                ph.text = item.phone
                itemView.const_layout.setOnClickListener{
                    with(itemView.context) {
                        val intent = Intent(this, ResMenuActivity::class.java)
                        intent.putExtra("name", item.name)
                        intent.putExtra("menu", menu)
                        intent.putExtra("phone", item.phone)
                        this.startActivity(intent)
                    }
                }
            }
        }
    }


    inner class ResViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(
        LayoutInflater
        .from(parent.context)
        .inflate(R.layout.res_item, parent, false)){

        val nm = itemView.text_resNM
        val ph = itemView.text_resPH
    }
}