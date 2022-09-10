package com.hrisheekesh.hrishee.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.hrisheekesh.hrishee.Content
import com.hrisheekesh.hrishee.adapter.ImageRecyclerAdapter
import com.hrisheekesh.hrishee.R


class BestFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter: ImageRecyclerAdapter
    val imageList = arrayListOf<Content>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        layoutManager = LinearLayoutManager(activity as Context)


        val queue = Volley.newRequestQueue(activity as Context)

        val url =
            "https://pixabay.com/api/?key=29801636-0fa803a0877468193320982b4&q=yellow+flowers&image_type=photo&pretty=true"

        val jsonObjectRequest =
            object : JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {

                val hits = it.getJSONArray("hits")
                for (i in 0 until 20) {
                    val contentJsonObject = hits.getJSONObject(i)
                    val contentObject = Content(
                        contentJsonObject.getString("webformatURL")
                    )
                    imageList.add(contentObject)

                    recyclerAdapter = ImageRecyclerAdapter(activity as Context, imageList)

                    recyclerView.adapter = recyclerAdapter

                    recyclerView.layoutManager = layoutManager

                }


            }, Response.ErrorListener {

                println("Error is $it")
                //Errors

            }) {

                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-type"] = "application/json"
                    headers["token"] = "29801636-0fa803a0877468193320982b4"
                    return headers
                }
            }

        queue.add(jsonObjectRequest)

        return view


    }
}