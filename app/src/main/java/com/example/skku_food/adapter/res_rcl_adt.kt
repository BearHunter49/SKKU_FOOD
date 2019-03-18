package com.example.skku_food.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skku_food.R
import com.example.skku_food.data.res_data
import com.example.skku_food.menuActivity.ResMenuActivity
import kotlinx.android.synthetic.main.res_item.view.*

class res_rcl_adt(private val items:ArrayList<res_data>):RecyclerView.Adapter<res_rcl_adt.ResViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ResViewHolder(parent)

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ResViewHolder, position: Int) {

        items[position].let { item ->
            with(holder){
                nm.text = item.res_nm
                ph.text = item.phone
                itemView.btn_res.setOnClickListener{
                    with(itemView.context) {
                        val intent = Intent(this, ResMenuActivity::class.java)
                        intent.putExtra("res_nm", item.res_nm)
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