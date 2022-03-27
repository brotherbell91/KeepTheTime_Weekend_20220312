package com.example.keepthetime_weekend_20220312.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.keepthetime_weekend_20220312.R
import com.example.keepthetime_weekend_20220312.datas.StartingPointData

class StartingPointSpinnerAdapter(
    val mContext : Context,
    resId : Int,
    val mList : List<StartingPointData>,
) : ArrayAdapter<StartingPointData>(mContext, resId, mList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var tempRow = convertView
        if (tempRow == null) {
            tempRow = LayoutInflater.from(mContext).inflate(R.layout.starting_point_list_item,null)
        }

        val row = tempRow!!

        val data = mList[position]

        val txtStartingPointName = row.findViewById<TextView>(R.id.txtStartingPointName)

        val txtPrimary = row.findViewById<TextView>(R.id.txtPrimary)

        txtStartingPointName.text = data.name

        if (data.is_primary) {
            txtPrimary.visibility = View.GONE
        }
        else {
            txtPrimary.visibility = View.GONE
        }

        return row
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {

        var tempRow = convertView
        if (tempRow == null) {
            tempRow = LayoutInflater.from(mContext).inflate(R.layout.starting_point_list_item,null)
        }

        val row = tempRow!!

        val data = mList[position]

        val txtStartingPointName = row.findViewById<TextView>(R.id.txtStartingPointName)
        val txtPrimary = row.findViewById<TextView>(R.id.txtPrimary)
        val imgMap = row.findViewById<ImageView>(R.id.imgMap)

        txtStartingPointName.text = data.name

        if (data.is_primary) {
            txtPrimary.visibility = View.GONE
        }
        else {
            txtPrimary.visibility = View.GONE
        }

        imgMap.visibility = View.GONE

        return row

    }

}