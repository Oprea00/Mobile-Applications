package com.example.accessapp.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.accessapp.R
import com.example.accessapp.logd
import com.example.accessapp.model.Car
import com.example.accessapp.network.InternetConnection
import com.example.accessapp.network.NetworkApiAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.list_one_item.view.*

class ItemsAdapter(var items: ArrayList<Car>): RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_one_item, parent, false)

        return ItemsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        holder.bind(this.items[position])

    }

    override fun getItemCount() = items.size


    class ItemsViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private val context: Context = itemView.context

        fun bind(itemObj: Car) {
            // Get string representations of Access's fields
            val nameText: String = itemObj.license.toString()
            val statusText: String = itemObj.status.toString()
            val sizeText: String = itemObj.seats.toString()
            val ownerText: String = itemObj.driver.toString()
            val capacityText: String = itemObj.cargo.toString()
            val idText: String = itemObj.id.toString()

            // Set text for the recycler view items
            itemView.manufacturerNameText.text = nameText
            itemView.statusText.text = statusText
            itemView.sizeText.text = sizeText
            itemView.ownerText.text = ownerText
            itemView.capacityText.text = capacityText
            itemView.idText.text = idText


            // Delete button pressed
            this.itemView.deleteButton.setOnClickListener {
                // Check internet connection
                if (InternetConnection.getInstance(context).isOnline) {
                    // Alert Dialog shows up
                    AlertDialog.Builder(this.context)
                        .setMessage("Do you want to delete this doc?")
                        .setPositiveButton("Yes") { _, _ ->
                            sendDeleteRequest(itemObj.id!!)
                        }.setNegativeButton("No", null)
                        .create()
                        .show()
                } else
                    Toast.makeText(context, "No internet connection!", Toast.LENGTH_SHORT).show()
            }




        }

        private fun sendDeleteRequest(id: Int) {
            // RxJava sending request
            val networkApiAdapter = NetworkApiAdapter.instance

            val callDelete = networkApiAdapter.delete(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { s ->
                                //Toast.makeText(this.context, "Delete success! $s", Toast.LENGTH_LONG).show()
                                logd("Item with id $id deleted")
                            },
                            { err ->
                                Toast.makeText(this.context, "Delete failed! $err", Toast.LENGTH_LONG).show()
                                logd(err)
                            }
                    )
        }
    }
}