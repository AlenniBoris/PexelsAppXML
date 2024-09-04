package com.example.pexelsproject

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pexelsproject.data.model.TestPhoto
import com.example.pexelsproject.utils.PhotosMainRecyclerAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var testData: ArrayList<TestPhoto>
    private lateinit var rvPhotosMain: RecyclerView
    private lateinit var photosMainRecyclerAdapter: PhotosMainRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        setTestData()
        rvPhotosMain = findViewById(R.id.rvPhotosMain)
        photosMainRecyclerAdapter = PhotosMainRecyclerAdapter(testData, this)
        rvPhotosMain.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        rvPhotosMain.adapter = photosMainRecyclerAdapter

    }



    private fun setTestData(){



        testData = ArrayList<TestPhoto>()
        testData.add(
            TestPhoto(28039617, "Esra Korkmaz",  "https://images.pexels.com/photos/28039617/pexels-photo-28039617.jpeg?auto=compress&cs=tinysrgb&h=350")
        )
        testData.add(
            TestPhoto(28039617, "Esra Korkmaz",  "https://images.pexels.com/photos/27978911/pexels-photo-27978911.jpeg?auto=compress&cs=tinysrgb&h=350")
        )
        testData.add(
            TestPhoto(28039617, "Esra Korkmaz",  "https://images.pexels.com/photos/27945902/pexels-photo-27945902.jpeg?auto=compress&cs=tinysrgb&h=350")
        )
        testData.add(
            TestPhoto(28039617, "Esra Korkmaz",  "https://images.pexels.com/photos/27945905/pexels-photo-27945905.jpeg?auto=compress&cs=tinysrgb&h=350")
        )
        testData.add(
            TestPhoto(28039617, "Esra Korkmaz",  "https://images.pexels.com/photos/27405284/pexels-photo-27405284.jpeg?auto=compress&cs=tinysrgb&h=350")
        )
        testData.add(
            TestPhoto(28039617, "Esra Korkmaz",  "https://images.pexels.com/photos/27926212/pexels-photo-27926212.jpeg?auto=compress&cs=tinysrgb&h=350")
        )
        testData.add(
            TestPhoto(28039617, "Esra Korkmaz",  "https://images.pexels.com/photos/27925463/pexels-photo-27925463.jpeg?auto=compress&cs=tinysrgb&h=350")
        )
        testData.add(
            TestPhoto(28039617, "Esra Korkmaz",  "https://images.pexels.com/photos/27890863/pexels-photo-27890863.jpeg?auto=compress&cs=tinysrgb&h=350")
        )
        testData.add(
            TestPhoto(28039617, "Esra Korkmaz",  "https://images.pexels.com/photos/27912713/pexels-photo-27912713.jpeg?auto=compress&cs=tinysrgb&h=350")
        )
        testData.add(
            TestPhoto(28039617, "Esra Korkmaz",  "https://images.pexels.com/photos/27926122/pexels-photo-27926122.jpeg?auto=compress&cs=tinysrgb&h=350")
        )
    }
}