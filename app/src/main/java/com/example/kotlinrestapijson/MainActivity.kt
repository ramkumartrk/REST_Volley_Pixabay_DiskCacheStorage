package com.example.kotlinrestapijson

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity(),CustomAdapter.OnItemClickListener{



    var requestQueue : RequestQueue? = null;
    val itemsList : ArrayList<CardItem> = ArrayList() ;

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //RecyclerView
        val recyclerView : RecyclerView = findViewById(R.id.recyclerview);
        recyclerView.layoutManager = LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);

        //Initialising empty arraylist
        val itemsList = ArrayList<CardItem>();

        //Setting up RequestQueue
        requestQueue  = Volley.newRequestQueue(this);


        parseJson();
    }

    fun parseJson()
    {
        var url : String = "https://pixabay.com/api/?key=12805391-2a52283043ae4b47ccad55ba6&q=yellow+flowers";

        val request = JsonObjectRequest(Request.Method.GET,url,null, Response.Listener { response ->

            try {
                /*retrieving whole hit json array objects*/
                val jsonArray : JSONArray = response.getJSONArray("hits");

                /*Iterating over every ArrayObject*/
                for (i in 0..jsonArray.length()-1)
                {
                    val  hit : JSONObject = jsonArray.getJSONObject(i);

                    val creatorName : String = hit.getString("user") ;
                    val likes : Int = hit.getInt("likes");
                    val url : String = hit.getString("webformatURL");

                    itemsList.add(CardItem(url,creatorName,likes));
                }


                //Setting Adapter to recyclerView
                val madapter : CustomAdapter = CustomAdapter(this,itemsList);
                madapter.setOnItemClickListenter(this);
                recyclerview.adapter = madapter;
            }

            catch (e: Exception)
            {
                e.printStackTrace();
            }
        }, Response.ErrorListener { error ->
            error.printStackTrace();

        });

        requestQueue?.add(request);
    }


    override fun onItemClick(position : Int)
    {
        intent = Intent(applicationContext,DetailActivity::class.java);

        var cardItem : CardItem = itemsList.get(position);

        intent.putExtra("ImageURL",cardItem.getImageURL());
        intent.putExtra("CreatorName",cardItem.getCreatorName());
        intent.putExtra("LikesCount",cardItem.getLikes());
        startActivity(intent);
    }
}


