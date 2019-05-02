package com.example.skku_food.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skku_food.R
import com.example.skku_food.data.MenuCategoryURL
import com.example.skku_food.menuActivity.RestaurantActivity
import kotlinx.android.synthetic.main.menu_item.view.*

class MenuRclAdt:RecyclerView.Adapter<MenuRclAdt.MenuViewHolder>(){

    private val items = MenuCategoryURL.items

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MenuViewHolder(parent)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {

        items[position].let { item ->
            with(holder){
                img.setImageResource(item.menu_img)
                nm.text = item.menu_nm
                itemView.setOnClickListener{

                    with(itemView.context) {
                        val intent = Intent(this, RestaurantActivity::class.java)
                        intent.putExtra("menu", item.menu_nm)
                        this.startActivity(intent)
                    }
                }
            }
        }

    }

    inner class MenuViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(LayoutInflater
        .from(parent.context)
            .inflate(R.layout.menu_item, parent, false)){

        val img = itemView.menu_image
        val nm = itemView.menu_name
    }

}