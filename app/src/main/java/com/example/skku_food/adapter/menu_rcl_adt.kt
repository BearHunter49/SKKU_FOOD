package com.example.skku_food.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skku_food.R
import com.example.skku_food.data.menu_catagoryData
import com.example.skku_food.menuActivity.RestaurantActivity
import kotlinx.android.synthetic.main.menu_item.view.*

class menu_rcl_adt:RecyclerView.Adapter<menu_rcl_adt.MenuViewHolder>(){

    private val items: MutableList<menu_catagoryData> = mutableListOf(menu_catagoryData(R.drawable.ic_menu_school, "학식"),
        menu_catagoryData(R.drawable.ic_menu_rice, "한식"), menu_catagoryData(R.drawable.ic_menu_chine, "중식"),
        menu_catagoryData(R.drawable.ic_menu_cutlet, "돈까스"), menu_catagoryData(R.drawable.ic_menu_chicken, "치킨"),
        menu_catagoryData(R.drawable.ic_menu_pizza, "피자"), menu_catagoryData(R.drawable.ic_menu_dduck, "분식"),
        menu_catagoryData(R.drawable.ic_menu_burger, "햄버거"), menu_catagoryData(R.drawable.ic_menu_doshi, "도시락"),
        menu_catagoryData(R.drawable.ic_menu_soup, "국밥"), menu_catagoryData(R.drawable.ic_menu_noodle, "면류"),
        menu_catagoryData(R.drawable.ic_menu_sushi, "초밥"), menu_catagoryData(R.drawable.ic_menu_gogi, "고기"),
        menu_catagoryData(R.drawable.ic_menu_restau, "레스토랑"), menu_catagoryData(R.drawable.ic_menu_curry, "카레"))

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