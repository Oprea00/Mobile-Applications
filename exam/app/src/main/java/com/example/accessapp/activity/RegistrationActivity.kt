package com.example.accessapp.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.accessapp.R
import com.example.accessapp.adapter.ItemsAdapter
import com.example.accessapp.logd
import com.example.accessapp.model.Car
import com.example.accessapp.network.InternetConnection
import com.example.accessapp.network.NetworkApiAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_staff.*

class RegistrationActivity : AppCompatActivity() {
    private lateinit var adapter: ItemsAdapter
    private val realm: Realm by lazy { Realm.getDefaultInstance() } //reference to DB
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_staff)

        progressBar = findViewById(R.id.progressBarSection1)
        progressBar.visibility = View.GONE

        this.adapter = ItemsAdapter(getItemsLocally())

        buttonSync.setOnClickListener {
            if (InternetConnection.getInstance(this).isOnline) {
                getAllItems()
                logd("Synchronize")
            }
            else
                Toast.makeText(InternetConnection.context, "No internet connection!", Toast.LENGTH_SHORT).show()
        }

        staffRecyclerView.layoutManager = LinearLayoutManager(this)
        staffRecyclerView.adapter = this.adapter
    }


    override fun onResume() {
       super.onResume()
        //if (InternetConnection.getInstance(this).isOnline) {
            //getAllItems()
            //this.adapter = ItemsAdapter(getItemsLocally())
        //}
        //else
         //   Toast.makeText(InternetConnection.context, "No internet connection!", Toast.LENGTH_SHORT).show()
   }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.addItem ->{
                val intent = Intent(this, AddItemActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getItemsLocally(): ArrayList<Car> {
        return ArrayList(this.realm.where(Car::class.java).findAll())
    }

    private fun getAllItems(){
        // Fetch all docs from server
        val networkApiAdapter = NetworkApiAdapter.instance

        val getRequest = networkApiAdapter.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { serverDocs ->
                            val realm = Realm.getDefaultInstance()
                            // syncronize with the server the objects that were added offline
                           synchronize()

                            // Delete locally all
                            realm.executeTransaction(Realm::deleteAll)

                            for (serverDoc in serverDocs) {
                                realm.executeTransaction {
                                    val obj = realm.createObject(Car::class.java, serverDoc.id)
                                    obj.license = serverDoc.license
                                    obj.status = serverDoc.status
                                    obj.seats = serverDoc.seats
                                    obj.driver = serverDoc.driver
                                    obj.color = serverDoc.color
                                    obj.cargo = serverDoc.cargo
                                }
                            }
                            logd("Added items locally")

                            adapter.items = getItemsLocally()
                            adapter.notifyDataSetChanged()
                            logd("GET Request complete")
                        },
                        { err ->
                            logd(err)
                            Toast.makeText(baseContext, "GET failed!", Toast.LENGTH_LONG).show()
                        }
                )
    }

    private fun synchronize(){
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            val id = bundle.getString("id")?.toInt()
            val name = bundle.getString("name")
            val status = bundle.getString("status")
            val size = bundle.getString("size")?.toInt()
            val owner = bundle.getString("owner")
            val manufacturer = bundle.getString("manufacturer")
            val capacity = bundle.getString("capacity")?.toInt()
            val car = Car(id, name, status, size, owner, manufacturer, capacity)
            logd("Make post request for ne-synced item with id $id")
            //Bundle.clear()
            intent.removeExtra("id")
            intent.removeExtra("name")
            intent.removeExtra("status")
            intent.removeExtra("size")
            intent.removeExtra("owner")
            intent.removeExtra("manufacturer")
            intent.removeExtra("capacity")
            makePostRequest(car)
        }
    }

    private fun makePostRequest(car: Car){
        val networkApiAdapter = NetworkApiAdapter.instance

        val callPost = networkApiAdapter.insert(car)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            logd("Post succeeded for ne-sync object")
                        },
                        { err ->
                            logd(err)
                            Toast.makeText(baseContext, "POST failed! $err", Toast.LENGTH_LONG).show()
                        }
                )
    }
}