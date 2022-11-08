package com.example.deviceconnector.ui.news.fragmentClasses

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.deviceconnector.util.Constants.INITIAL_POSITION
import com.example.deviceconnector.util.Constants.NEWS_URL
import com.example.deviceconnector.util.Constants.TOP_HEADLINES_COUNT
import com.example.deviceconnector.R
import com.example.deviceconnector.adapter.CustomAdapter
import com.example.deviceconnector.ui.news.NewsActivity
import com.example.deviceconnector.ui.news.NewsModel
import com.example.deviceconnector.ui.news.ReadNewsActivity
import com.example.deviceconnector.util.Constants.NEWS_CONTENT
import com.example.deviceconnector.util.Constants.NEWS_DESCRIPTION
import com.example.deviceconnector.util.Constants.NEWS_IMAGE_URL
import com.example.deviceconnector.util.Constants.NEWS_PUBLICATION_TIME
import com.example.deviceconnector.util.Constants.NEWS_SOURCE
import com.example.deviceconnector.util.Constants.NEWS_TITLE


class GeneralFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
//    private lateinit var carouselView: CarouselView
    private lateinit var adapter: CustomAdapter
    private lateinit var newsDataForTopHeadlines: List<NewsModel>
    private lateinit var newsDataForDown: List<NewsModel>
    var position = INITIAL_POSITION

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_general, container, false)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager

        // Setting recyclerViews adapter
        if(NewsActivity.generalNews.size >= TOP_HEADLINES_COUNT)
               newsDataForTopHeadlines = NewsActivity.generalNews.slice(0 until TOP_HEADLINES_COUNT)
        else
            newsDataForTopHeadlines = NewsActivity.generalNews.slice(0 until NewsActivity.generalNews.size)
        newsDataForDown = NewsActivity.generalNews.slice(TOP_HEADLINES_COUNT until NewsActivity.generalNews.size - TOP_HEADLINES_COUNT)
        adapter = CustomAdapter(newsDataForDown)
        recyclerView.adapter = adapter


        // listitem onClick
        adapter.setOnItemClickListener(object : CustomAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(context, ReadNewsActivity::class.java).apply {
                    putExtra(NEWS_URL, newsDataForDown[position].url)
                    putExtra(NEWS_TITLE, newsDataForDown[position].headLine)
                    putExtra(NEWS_IMAGE_URL, newsDataForDown[position].image)
                    putExtra(NEWS_DESCRIPTION, newsDataForDown[position].description)
                    putExtra(NEWS_SOURCE, newsDataForDown[position].source)
                    putExtra(NEWS_PUBLICATION_TIME, newsDataForDown[position].time)
                    putExtra(NEWS_CONTENT, newsDataForDown[position].content)
                }

                startActivity(intent)
            }
        })

        // Ignore
        adapter.setOnItemLongClickListener(object : CustomAdapter.OnItemLongClickListener {
            override fun onItemLongClick(position: Int) = Unit
        })

        return view
    }

}