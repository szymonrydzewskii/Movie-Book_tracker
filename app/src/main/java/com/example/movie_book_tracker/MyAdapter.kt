package com.example.movie_book_tracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter (private val movieList: List<movie_book>): RecyclerView.Adapter<MyAdapter.MovieViewHolder>() {
    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val movieTytul: TextView = itemView.findViewById(R.id.movie_tytul)
        val movieGatunek: TextView = itemView.findViewById(R.id.movie_gatunek)
        val movieOpis: TextView = itemView.findViewById(R.id.movie_opis)
        val movieOcena: TextView = itemView.findViewById(R.id.movie_ocena)
        val movieFilmCzyKsiazka: TextView = itemView.findViewById(R.id.movie_filmCzyKsiazka)
        val movieCzyObejrzane: TextView = itemView.findViewById(R.id.movie_czyObejrzane)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)

        return  MovieViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentMovie = movieList[position]
        holder.movieTytul.text = currentMovie.tytul
        holder.movieGatunek.text = currentMovie.gatunek
        holder.movieOpis.text = currentMovie.opis
        holder.movieOcena.text = "${currentMovie.ocena}/10"
        holder.movieFilmCzyKsiazka.text = currentMovie.filmCzyKsiazka
        holder.movieCzyObejrzane.text = "${currentMovie.czyObejrzane}"
    }

    override fun getItemCount(): Int = movieList.size
}