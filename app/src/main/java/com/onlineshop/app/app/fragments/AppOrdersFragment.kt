package com.onlineshop.app.app.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.onlineshop.app.app.R
import com.onlineshop.app.app.adapters.AppOrder_Adapter
import com.onlineshop.app.app.models.AppCartItemAddedModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OrdersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OrdersFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var  sharedPreferences: SharedPreferences

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      val view= inflater.inflate(R.layout.app_fragment_orders, container, false)
        recyclerView=view.findViewById(R.id.app_order_frag_rec)
        sharedPreferences=requireActivity().getSharedPreferences(requireActivity().packageName, Context.MODE_PRIVATE)
        recyclerView.layoutManager= LinearLayoutManager(requireContext())
        val type=object: TypeToken<MutableList<AppCartItemAddedModel>>(){}.type
        if(sharedPreferences.getString("orderItems","")?.isNotEmpty() == true) {
            val dataList = Gson().fromJson(
                sharedPreferences.getString("orderItems", ""),
                type
            ) as MutableList<AppCartItemAddedModel>
            val adapter = AppOrder_Adapter(requireContext(), dataList)
            recyclerView.adapter = adapter

        }
        return  view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OrdersFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OrdersFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}