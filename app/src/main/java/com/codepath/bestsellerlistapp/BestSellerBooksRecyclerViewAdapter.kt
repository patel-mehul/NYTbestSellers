package com.codepath.bestsellerlistapp

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class BestSellerBooksRecyclerViewAdapter(
    private val books: List<BestSellerBook>
) : RecyclerView.Adapter<BestSellerBooksRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_best_seller_book, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = books[position]

        holder.titleText.text = book.title
        holder.authorText.text = "By ${book.author}"
        holder.descriptionText.text = book.description

        Glide.with(holder.bookImage.context)
            .load(book.bookImageUrl)
            .into(holder.bookImage)

        holder.buyButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(book.amazonUrl))
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = books.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleText: TextView = view.findViewById(R.id.book_title)
        val authorText: TextView = view.findViewById(R.id.book_author)
        val descriptionText: TextView = view.findViewById(R.id.book_description)
        val bookImage: ImageView = view.findViewById(R.id.book_image)
        val buyButton: Button = view.findViewById(R.id.buy_button)
    }
}
