package com.aukde.proveedor.Activitys

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aukde.proveedor.Adapters.AdapterOrders
import com.aukde.proveedor.Models.Orders
import com.aukde.proveedor.Providers.AuthProviders
import com.aukde.proveedor.R
import com.google.firebase.database.*
import es.dmoral.toasty.Toasty
import kotlin.collections.ArrayList
import androidx.appcompat.widget.SearchView


class OrderList : AppCompatActivity() {

    lateinit var mDatabase    : DatabaseReference
    lateinit var orderList    : ArrayList<Orders>
    lateinit var recyclerView : RecyclerView
    lateinit var searchView   : SearchView
    lateinit var adapter      : AdapterOrders
    lateinit var manager      : LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppThemePink)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_list)
        val mAuthID : String = AuthProviders().userID()

        recyclerView               = findViewById(R.id.recyclerOrders)
        manager                    = LinearLayoutManager(this)
        recyclerView.layoutManager = manager
        orderList                  = ArrayList()
        adapter                    = AdapterOrders(orderList)
        recyclerView.adapter       = adapter
        searchView                 = findViewById(R.id.searchOrders)
        searchView.setBackgroundColor(Color.WHITE)

        mDatabase = FirebaseDatabase.getInstance().reference
            .child("Orders")
            .child(mAuthID)

        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    orderList.clear()
                    for(s : DataSnapshot in snapshot.children) {
                        val list : Orders? = s.getValue(Orders::class.java)
                        orderList.add(list!!)
                    }
                    orderList.reverse()
                    adapter.notifyDataSetChanged()
                } else {
                    Toasty.error(applicationContext, "Server error", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toasty.error(applicationContext, "Server error", Toast.LENGTH_SHORT).show()
            }
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(numOrder: String?): Boolean {
                search(numOrder.toString())
                return true
            }

        })

    }

    @SuppressLint("DefaultLocale")
    private fun search(numOrder : String){
        val list : ArrayList<Orders> = ArrayList()
        for(obj : Orders in orderList){
            if(obj.numOrder.toLowerCase().contains(numOrder.toLowerCase())){
                list.add(obj)
            }
        }

        val viewSearchAdapter = AdapterOrders(list)
        recyclerView.adapter = viewSearchAdapter

    }

}