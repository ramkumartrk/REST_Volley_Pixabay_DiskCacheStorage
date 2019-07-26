package com.example.kotlinrestapijson

class CardItem
{

    private var imgurl : String = "";
    private var creatorName : String="";
    private var likes : Int = 0;


    constructor(url : String,creator : String, like : Int)
    {
        imgurl =url;
        creatorName = creator;
        likes = like;
    }
    fun  getImageURL() :String
    {
            return imgurl;
    }

    fun  getCreatorName() : String
    {
        return creatorName;
    }

    fun getLikes() : Int
    {
        return likes;
    }

}