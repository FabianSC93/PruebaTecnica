package com.example.examen.data.model

data class TopMoviesModel(
    val page:Int,
    val results:List<Result>,
    val total_pages:Int,
    val total_results:Int)

data class Result(
    val adult:Boolean,
    val backdrop_path:String,
    val genre_ids:List<Int>,
    val id:Int,
    val original_language:String,
    val original_title:String,
    val overview:String,
    val popularity:String,
    val poster_path:String,
    val release_date:String,
    val title:String,
    val video:Boolean,
    val vote_average:String,
    val vote_count:String)