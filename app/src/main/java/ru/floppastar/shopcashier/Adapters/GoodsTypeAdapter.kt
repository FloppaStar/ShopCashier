package ru.floppastar.shopcashier.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.floppastar.shopcashier.DataClasses.GoodsType
import ru.floppastar.shopcashier.R


class GoodsTypeAdapter(
    private val goodsTypeList: MutableList<GoodsType>,
    private val itemClickListener: (GoodsType) -> Unit) : BaseAdapter() {
    override fun getCount(): Int = goodsTypeList.size

    override fun getItem(position: Int): GoodsType = goodsTypeList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(parent?.context).inflate(R.layout.goods_type_item, parent, false)
        val goodsTypeName = view.findViewById<TextView>(R.id.tvGoodsTypeName)
        val goodsType = getItem(position)
        goodsTypeName.text = goodsType.name
        view.setOnClickListener {
            itemClickListener(goodsType)
        }
        return view
    }
}