package com.example.accessapp.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.accessapp.R
import com.example.accessapp.logd
import com.example.accessapp.model.Car
import com.example.accessapp.network.InternetConnection
import com.example.accessapp.network.NetworkApiAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_add_new.*

class AddItemActivity: AppCompatActivity() {
    val realm: Realm by lazy { Realm.getDefaultInstance() }
    var car: Car? = null
    private lateinit var progressBar: ProgressBar
    //private var neSynced: ArrayList<Plane> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new)
        progressBar = findViewById(R.id.progressBarAddActivity)
        progressBar.visibility = View.GONE

        buttonAdd.setOnClickListener {
            if (this.validInput()){
               this@AddItemActivity.runOnUiThread(java.lang.Runnable {
                   progressBar.visibility = View.VISIBLE
               })

                car = Car()
                car!!.license = editTextName.text.toString()
                car!!.status = editTextStatus.text.toString()
                car!!.seats = editTextSize.text.toString().toInt()
                car!!.driver = editTextOwner.text.toString()
                car!!.color = editTextManufacturer.text.toString()
                car!!.cargo = editTextCapacity.text.toString().toInt()

                addItem()

                if (InternetConnection.getInstance(applicationContext).isOnline) {
                    // If we have internet connection and movie is synced, then we also update the object on the server
                    sendPostRequest()
                    finish()
                }
                else {

                    val intent = Intent(baseContext, RegistrationActivity::class.java);
                    intent.putExtra("id", car!!.id.toString());
                    intent.putExtra("name", car!!.license);
                    intent.putExtra("status", car!!.status);
                    intent.putExtra("size", car!!.seats.toString());
                    intent.putExtra("owner", car!!.driver);
                    intent.putExtra("manufacturer", car!!.color);
                    intent.putExtra("capacity", car!!.cargo.toString());
                    startActivity(intent)
                }
                //finish()
            }
            else {
                Toast.makeText(this, "Wrong input", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun nextId(): Int{
        val accessId = realm.where(Car::class.java).max("id")
        val result: Int
        result = if (accessId == null) {
            1
        } else {
            accessId.toInt() + 1
        }
        return result
    }

    private fun addItem(){
        //add in locally realm db
        this.realm.executeTransaction{
            val item: Car = this.realm.createObject(Car::class.java, nextId())

            item.license = car!!.license
            item.status = car!!.status
            item.seats = car!!.seats
            item.driver = car!!.driver
            item.color = car!!.color
            item.cargo = car!!.cargo
            //Toast.makeText(baseContext, "New item saved locally ${item.id}", Toast.LENGTH_LONG).show()
            logd("New Item Saved Locally, id: ${item.id}")
            car = item
        }
    }

    private fun sendPostRequest(){
        // RxJava sending request
        val networkApiAdapter = NetworkApiAdapter.instance

        val callPost = networkApiAdapter.insert(car!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    //Toast.makeText(baseContext, "Post success ${it.id}", Toast.LENGTH_LONG).show()
                    logd("New Item added on server")
                },
                { err ->
                    Toast.makeText(baseContext, "POST failed! $err", Toast.LENGTH_LONG).show()
                    logd(err)
                }
            )
    }

    private fun validInput(): Boolean{
        if (editTextName.text.isEmpty() || editTextStatus.text.isEmpty() || editTextSize.text.isEmpty() || editTextOwner.text.isEmpty() || editTextManufacturer.text.isEmpty())
            return false
        return true
    }
}