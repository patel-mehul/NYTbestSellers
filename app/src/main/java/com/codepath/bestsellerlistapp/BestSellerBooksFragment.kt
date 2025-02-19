package com.codepath.bestsellerlistapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.RequestParams
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class BestSellerBooksFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_best_seller_books_list, container, false)

        val progressBar = view.findViewById<ContentLoadingProgressBar>(R.id.progress)
        val recyclerView = view.findViewById<RecyclerView>(R.id.list)

        recyclerView.layoutManager = LinearLayoutManager(view.context)
        updateAdapter(progressBar, recyclerView)

        return view
    }

    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()

        val client = AsyncHttpClient()
        val params = RequestParams()

        val apiKey = getString(R.string.NYT_API_KEY)
        params.put("api-key", apiKey)

        client.get(
            "https://api.nytimes.com/svc/books/v3/lists/current/hardcover-fiction.json",
            params,
            object : JsonHttpResponseHandler() {

                override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONObject) {
                    progressBar.hide()
                    Log.d("BestSellerBooksFragment", "API response successful: $response")

                    try {
                        val resultsJSON: JSONObject = response.getJSONObject("results")
                        val booksRawJSON: String = resultsJSON.getJSONArray("books").toString()

                        val gson = Gson()
                        val arrayBookType = object : TypeToken<List<BestSellerBook>>() {}.type
                        val models: List<BestSellerBook> = gson.fromJson(booksRawJSON, arrayBookType)

                        recyclerView.adapter = BestSellerBooksRecyclerViewAdapter(models)

                    } catch (e: Exception) {
                        Log.e("BestSellerBooksFragment", "JSON Parsing error: ${e.message}")
                    }
                }

                override fun onFailure(
                    statusCode: Int, headers: Array<out Header>?, throwable: Throwable, errorResponse: JSONObject?
                ) {
                    progressBar.hide()
                    Log.e("BestSellerBooksFragment", "API Error: ${errorResponse.toString()}")
                    Toast.makeText(context, "Failed to load books. Check API key!", Toast.LENGTH_LONG).show()
                }
            }
        )
    }
}
