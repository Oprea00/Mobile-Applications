package com.example.accessapp.activity

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.accessapp.R
import com.example.accessapp.adapter.ItemsAdapter
import com.example.accessapp.adapter.ManufacturerAdapter
import com.example.accessapp.logd
import com.example.accessapp.model.Car
import com.example.accessapp.network.InternetConnection
import com.example.accessapp.network.NetworkApiAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_manage.*
import kotlinx.android.synthetic.main.activity_reports.*
import kotlinx.android.synthetic.main.activity_reports.typesRecyclerView

class ManageActivity: AppCompatActivity() {
    private lateinit var adapter: ManufacturerAdapter
    private var arrayElements: ArrayList<String> = ArrayList()
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter2: ItemsAdapter
    private var arrayElements2: ArrayList<Car> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage)

        progressBar = findViewById(R.id.progressBarManufacturer)
        progressBar.visibility = View.GONE

        this.adapter = ManufacturerAdapter(arrayElements)
        this.adapter2 = ItemsAdapter(arrayElements2)

        typesRecyclerView.layoutManager = LinearLayoutManager(this)
        typesRecyclerView.adapter = this.adapter
        colorsRecyclerView.layoutManager = LinearLayoutManager(this)
        colorsRecyclerView.adapter = this.adapter2

        buttonSearch.setOnClickListener {
            this@ManageActivity.runOnUiThread(java.lang.Runnable {
                progressBar.visibility = View.VISIBLE
            })
            val level = inputColor.text.toString()
            if (InternetConnection.getInstance(this).isOnline) {
                getDocumentsByLevel(level)
            }
            else
                Toast.makeText(InternetConnection.context, "No internet connection!", Toast.LENGTH_SHORT).show()
        }

        getColors.setOnClickListener {
            this@ManageActivity.runOnUiThread(java.lang.Runnable {
                progressBar.visibility = View.VISIBLE
            })
            if (InternetConnection.getInstance(this).isOnline) {
                getTypes()
            }
            else
                Toast.makeText(InternetConnection.context, "No internet connection!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getTypes(){
        this@ManageActivity.runOnUiThread(java.lang.Runnable {
            progressBar.visibility = View.VISIBLE
        })
        // Fetch all docs from server
        val networkApiAdapter = NetworkApiAdapter.instance

        val getRequest = networkApiAdapter.getColors()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { serverDocs ->
                            for (serverDoc in serverDocs) {
                                arrayElements.add(serverDoc)
                            }
                            //Toast.makeText(baseContext, "GET Types succeeded!", Toast.LENGTH_LONG).show()

                            //val docs: List<Plane> = arrayElements.sortedByDescending { it.size }.take(10)
                            //val docs: List<Plane> = arrayElements.take(10).sortedByDescending { it.size }

                            //adapter.items = docs as ArrayList<Plane>
                            adapter.items = arrayElements
                            adapter.notifyDataSetChanged()

                            logd("GET Colors Request complete")
                            this@ManageActivity.runOnUiThread(java.lang.Runnable {
                                progressBar.visibility = View.GONE
                            })
                        },
                        { err ->
                            logd(err)
                            Toast.makeText(baseContext, "GET failed!", Toast.LENGTH_LONG).show()
                        }
                )
    }

    private fun getDocumentsByLevel(color: String) {
        // Fetch all docs from server
        val networkApiAdapter = NetworkApiAdapter.instance


        val getRequest = networkApiAdapter.getVehicles(color)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { serverDocs ->
                            arrayElements2.clear()
                            for (serverDoc in serverDocs) {
                                arrayElements2.add(serverDoc)
                            }
                            Toast.makeText(baseContext, "GET By Level succeeded!", Toast.LENGTH_LONG).show()
                            logd("Get by color $color succeeded")

                            //val docs: List<Car> = arrayElements.sortedBy { it.from }

                           // var list: java.util.ArrayList<Access> = ArrayList()
                            //list.clear()
                            //for (element in docs){
                           //     list.add(element)
                           // }

                            adapter2.items = arrayElements2
                            adapter2.notifyDataSetChanged()
                            this@ManageActivity.runOnUiThread(java.lang.Runnable {
                                progressBar.visibility = View.GONE
                            })
                        },
                        { err ->
                            logd(err)
                            Toast.makeText(baseContext, "GET failed!", Toast.LENGTH_LONG).show()
                            this@ManageActivity.runOnUiThread(java.lang.Runnable {
                                progressBar.visibility = View.GONE
                            })
                        }
                )
    }



}