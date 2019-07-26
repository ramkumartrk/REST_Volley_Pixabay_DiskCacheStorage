package com.example.kotlinrestapijson

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.util.LruCache
import com.android.volley.Cache
import com.android.volley.Network
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.ImageLoader

class CustomVolleyRequestQueue(context: Context?) {

    var mcontext: Context?;
    var mRequestQueue: RequestQueue;
    var mImageLoader: ImageLoader?;


    /*init*/
    init {
        mcontext = context;
        mRequestQueue = getRequestQueue();
        mImageLoader = ImageLoader(mRequestQueue, object : ImageLoader.ImageCache {

            private val cache = LruCache<String, Bitmap>(20);

            override fun getBitmap(url: String): Bitmap? {
                return cache.get(url);
            }

            override fun putBitmap(url: String, bitmap: Bitmap) {
                cache.put(url, bitmap);
            }
        });
    }


    /*Static method to create Singleton Object --> getInstance() ->companion Object*/
    companion object {

        var mInstance : CustomVolleyRequestQueue? = null;

        fun getInstance(context : Context): CustomVolleyRequestQueue {

            if (mInstance == null) {
                Log.d("KotlinRestAPIJson","Called once.....instance created ");

                mInstance = CustomVolleyRequestQueue(context);
            }
            return mInstance as CustomVolleyRequestQueue;
        }
    }



    fun getRequestQueue(): RequestQueue {

      // if (mRequestQueue == null) {
            val cache: Cache = DiskBasedCache(mcontext?.cacheDir, 10 * 1024 * 1024);

            val network: Network = BasicNetwork(HurlStack());

            mRequestQueue = RequestQueue(cache, network);
            mRequestQueue.start();
       // }
        return mRequestQueue;
    }


   fun getImageLoader() : ImageLoader?
    {
        return mImageLoader;
    }
}
