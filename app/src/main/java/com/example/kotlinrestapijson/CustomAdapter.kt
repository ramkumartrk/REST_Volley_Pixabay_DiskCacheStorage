package com.example.kotlinrestapijson

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.NetworkImageView


class CustomAdapter(val context : Context, val list : ArrayList<CardItem>): RecyclerView.Adapter<CustomAdapter.ViewHolder>()
{

    private var onItemClickListener : OnItemClickListener? = null;
    private var imageLoader : ImageLoader? = null;


    init {

        val customVolleyRequestQueue: CustomVolleyRequestQueue = CustomVolleyRequestQueue.getInstance(context);
        imageLoader = customVolleyRequestQueue.getImageLoader()!!;
    }

    public interface OnItemClickListener {
        fun onItemClick(position: Int);
    }

    fun setOnItemClickListenter(monItemClickListener: OnItemClickListener) {
        this.onItemClickListener = monItemClickListener;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder  {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_item,parent,false);
        return ViewHolder(v);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         val currentItem : CardItem = list.get(position);

         holder.creatorName.text = "Creator Name: " + currentItem.getCreatorName();

         val count = currentItem.getLikes();
         holder.likesCount.text  = "Lies: " + count.toString();


         val imgUrl = currentItem.getImageURL();

         imageLoader?.get(imgUrl,ImageLoader.getImageListener(holder.imageUrlView,R.mipmap.ic_launcher,android.R.drawable.alert_dark_frame));

         holder.imageUrlView.setImageUrl(imgUrl,imageLoader);

    }


    override fun getItemCount(): Int
    {
        return list.size;
    }


   inner class ViewHolder(itemView  : View) :RecyclerView.ViewHolder(itemView)
    {
        val imageUrlView    =  itemView.findViewById<NetworkImageView>(R.id.flowerNetworkImageView) as NetworkImageView;
        val creatorName =  itemView.findViewById<TextView>(R.id.creatorNameTextView) as TextView;
        var likesCount  =  itemView.findViewById<TextView>(R.id.likesTextView) as TextView;

        init {

            itemView.setOnClickListener(View.OnClickListener {

                if(onItemClickListener != null)
                {
                   onItemClickListener!!.onItemClick(adapterPosition);
                }
            });

            }//init
    }
}

