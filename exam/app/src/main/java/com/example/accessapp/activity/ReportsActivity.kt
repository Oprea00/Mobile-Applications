package com.example.accessapp.activity

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.accessapp.R
import com.example.accessapp.adapter.ItemsAdapter
import com.example.accessapp.logd
import com.example.accessapp.model.Car

import com.example.accessapp.network.NetworkApiAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_reports.*

class ReportsActivity: AppCompatActivity() {
    private lateinit var adapter: ItemsAdapter
    private var arrayElements: ArrayList<Car> = ArrayList()
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reports)

        progressBar = findViewById(R.id.progressBarsection3)
        progressBar.visibility = View.GONE

        this.adapter = ItemsAdapter(arrayElements)

        typesRecyclerView.layoutManager = LinearLayoutManager(this)
        typesRecyclerView.adapter = this.adapter

        getAllItems()
    }

    private fun getAllItems(){
        this@ReportsActivity.runOnUiThread(java.lang.Runnable {
            progressBar.visibility = View.VISIBLE
        })
        // Fetch all docs from server
        val networkApiAdapter = NetworkApiAdapter.instance

        val getRequest = networkApiAdapter.getDrivers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { serverDocs ->
                            for (serverDoc in serverDocs) {
                                arrayElements.add(serverDoc)
                            }

                            val drivers: ArrayList<String> = ArrayList()
                            for (element in arrayElements){
                                if (element.driver in drivers){

                                }
                                else{
                                    element.driver?.let { drivers.add(it) }
                                }
                            }
                            logd("Drivers: $drivers")

                            //for (driver in drivers)
                            //    for (element in arrayElements){
                             //       if (driver == element.driver){

                             //       }
                             //   }
                            //Toast.makeText(baseContext, "GET succeeded!", Toast.LENGTH_LONG).show()

                            //val docs: List<Car> = arrayElements.sortedByDescending { it. }.take(10)
                            //val docs: List<Plane> = arrayElements.take(10).sortedByDescending { it.size }
                            val docs: List<Car> = arrayElements.take(10)
                            adapter.items = docs as ArrayList<Car>
                            adapter.notifyDataSetChanged()

                            logd("GET Request complete - Reports")
                            this@ReportsActivity.runOnUiThread(java.lang.Runnable {
                                progressBar.visibility = View.GONE
                            })
                        },
                        { err ->
                            logd(err)
                            Toast.makeText(baseContext, "GET failed!", Toast.LENGTH_LONG).show()
                        }
                )
    }
}