package com.example.kotlinrestapijson

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.NetworkImageView

class DetailActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        var bundle : Bundle? = intent.extras;

        val creatorName : String = bundle?.get("CreatorName") as String;
        val likesCount : Int = bundle?.get("LikesCount") as Int;
        val imageURL  : String = bundle?.get("ImageURL") as String;

        var networkImageView :NetworkImageView = this.findViewById(R.id.detail_networkImageView);
        var creatorNameTextView : TextView = findViewById(R.id.detail_creatorNameTextView);
        var likesCountTextView : TextView = findViewById(R.id.detail_likesCountTextView);

        creatorNameTextView.text = creatorName.toString();
        likesCountTextView.text = likesCount.toString();

        var customVolleyRequestQueue = CustomVolleyRequestQueue.getInstance(applicationContext);
        var imageLoader : ImageLoader? = customVolleyRequestQueue.getImageLoader();

        imageLoader?.get(imageURL,ImageLoader.getImageListener(networkImageView,R.mipmap.ic_launcher,android.R.drawable.dark_header));
        networkImageView.setImageUrl(creatorName,imageLoader);

    }
}
