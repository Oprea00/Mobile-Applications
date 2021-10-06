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
import com.example.accessapp.network.InternetConnection
import com.example.accessapp.network.NetworkApiAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.list_one_item.view.*

class ManufacturerAdapter(var items: ArrayList<String>): RecyclerView.Adapter<ManufacturerAdapter.ManufacturerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManufacturerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.manufacture_names, parent, false)

        return ManufacturerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ManufacturerViewHolder, position: Int) {
        holder.bind(this.items[position])
        /*
          val item = this.items[position]
          val itemId = item.id

          holder.itemView.setOnClickListener{ v ->
              //val access: Access? = itemId?.let { getOneItemRequest(it) }
              if (InternetConnection.getInstance(v.context).isOnline) {
                  if (itemId != null) {
                      getOneItemRequest(itemId)
                  }
                  val access = this.theOneItem
                  val context = v.context
                  val intent = Intent(context, ItemDetailsActivity::class.java)

                  if (access != null) {
                      if (access.id != null) {
                          intent.putExtra("Id", access.id!!)
                          intent.putExtra("Name", access.name)
                          intent.putExtra("Level", access.level.toString())
                          intent.putExtra("Status", access.status)
                          intent.putExtra("From", access.from.toString())
                          intent.putExtra("To", access.to.toString())
                      }
                  }
                  context.startActivity(intent)
              }
              else
                  Toast.makeText(v.context, "No internet connection!", Toast.LENGTH_SHORT).show()
          }

         */

    }

    override fun getItemCount() = items.size
/*
    private fun getOneItemRequest(id: Int) {
        val networkApiAdapter = NetworkApiAdapter.instance

        val getRequest = networkApiAdapter.getOne(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            //Toast.makeText(baseContext, "Get one success ${it.id}", Toast.LENGTH_LONG).show()
                            logd("Get one item from server")
                            theOneItem = Access(it.id, it.name, it.level, it.status, it.from, it.to)
                            logd(theOneItem!!.name)
                        },
                        { err ->
                            //Toast.makeText(baseContext, "Get one failed! $err", Toast.LENGTH_LONG).show()
                            logd(err)
                        }
                )
    }

 */


    class ManufacturerViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private val context: Context = itemView.context



        fun bind(itemObj: String) {
            // Get string representations of Access's fields
            val nameText: String = itemObj
            itemView.manufacturerNameText.text = nameText
            /*
            val statusText: String = itemObj.status.toString()
            val sizeText: String = itemObj.size.toString()
            val ownerText: String = itemObj.owner.toString()
            val capacityText: String = itemObj.capacity.toString()
            val idText: String = itemObj.id.toString()

            // Set text for the recycler view items
            itemView.manufacturerNameText.text = nameText
            itemView.statusText.text = statusText
            itemView.sizeText.text = sizeText
            itemView.ownerText.text = ownerText
            itemView.capacityText.text = capacityText
            itemView.idText.text = idText

             */


/*
//            // Edit button pressed
            this.itemView.deleteButton.setOnClickListener {
//                // Check internet connection
                if (InternetConnection.getInstance(context).isOnline)  {
//                    // start Update activity
                    val intent = Intent(context, UpdateActivity::class.java)
                    intent.putExtra("id", itemObj.id)
                    this.context.startActivity(intent)
                } else
                    Toast.makeText(context, "No internet connection!", Toast.LENGTH_SHORT).show()
           }

 */
/*
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
                                Toast.makeText(this.context, "Delete success! $s", Toast.LENGTH_LONG).show()
                                logd("Item with id $id deleted")
                            },
                            { err ->
                                Toast.makeText(this.context, "Delete failed! $err", Toast.LENGTH_LONG).show()
                                logd(err)
                            }
                    )

 */
        }


    }


}