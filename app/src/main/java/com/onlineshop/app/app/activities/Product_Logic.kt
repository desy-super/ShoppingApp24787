package com.onlineshop.app.app.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.onlineshop.app.app.R
import com.onlineshop.app.app.adapters.CustomSpecificProductAdapter
import com.onlineshop.app.app.app_webapi.WebApiClient
import com.onlineshop.app.app.Api_Response.AppSpecificItemResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Product_Logic : AppCompatActivity() {
    private lateinit var recView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_activity_specific_product_item)
        findViews()
    }
    private fun findViews(){
        recView=findViewById(R.id.app_recycler_single_item)
        recView.layoutManager=LinearLayoutManager(this)
      businessLogic()
    }

    private fun businessLogic(){
        val title=intent.getStringExtra("category")
        title?.let {
            WebApiClient.getWebApiInterface().getProductCategoriesSpecific(it).enqueue(object:Callback<MutableList<AppSpecificItemResponse>>{
                override fun onResponse(
                    call: Call<MutableList<AppSpecificItemResponse>>,
                    response: Response<MutableList<AppSpecificItemResponse>>
                ) {
                    Log.d("specific", response.code().toString())
                    if (response.code() == 200) {

                        response.body()?.let { it1 ->
                          val adapter= getSharedPreferences(packageName,Context.MODE_PRIVATE).getString("display","")
                              ?.let { it2 ->
                                  CustomSpecificProductAdapter(
                                      this@Product_Logic,
                                      it1,
                                      it2
                                  )
                              }
                            recView.adapter=adapter
                        }
                    }
                }

                override fun onFailure(
                    call: Call<MutableList<AppSpecificItemResponse>>,
                    t: Throwable
                ) {
                    Log.d("specific", t.message!!)
                }

            })
        }
    }


}