package ru.floppastar.shopcashier.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ru.floppastar.shopcashier.DataClasses.GoodsInStock
import ru.floppastar.shopcashier.R
import ru.floppastar.shopcashier.db.dbRepository

class GoodsInCartAdapter(
    var goodsInStockList: MutableList<GoodsInStock>,
    private val GoodsInStockAddListener: (GoodsInStock, Int) -> Unit,
    private val GoodsInStockRemoveListener: (GoodsInStock, Int) -> Unit,
    private val repository: dbRepository)
    : RecyclerView.Adapter<GoodsInCartAdapter.ViewHolder>()  { var saleId: Int = repository.getOrCreateSaleId()
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvGoodsInStockName = itemView.findViewById<TextView>(R.id.tvGoodsInStockName)
        val tvGoodsInStockType = itemView.findViewById<TextView>(R.id.tvGoodsInStockType)
        val tvGoodsAddedCount = itemView.findViewById<TextView>(R.id.tvGoodsAddedCount)
        val tvGoodsInStockPrice = itemView.findViewById<TextView>(R.id.tvGoodsInStockPrice)
        val tvGoodsInStockCount = itemView.findViewById<TextView>(R.id.tvGoodsInStockCount)
        val btRemove = itemView.findViewById<ImageView>(R.id.imRemove)
        val btAdd = itemView.findViewById<ImageView>(R.id.imAdd)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.goods_in_cart_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return goodsInStockList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var goodsInStock = goodsInStockList[position]
        holder.tvGoodsInStockName.text = goodsInStock.name
        holder.tvGoodsInStockType.text = repository.getGoodsTypeName(goodsInStock.goodsTypeId)
        holder.tvGoodsAddedCount.text = repository.getGoodsCountInCart(goodsInStock.goodId, saleId).toString()
        holder.tvGoodsInStockPrice.text = "Стоимость: ${goodsInStock.price.toString()} руб."
        holder.tvGoodsInStockCount.text = "На складе: ${goodsInStock.goodsCount.toString()} шт."
        holder.btAdd.setOnClickListener {
            val success = repository.addGoodsToCart(goodsInStock.goodId, saleId)
            if (success) {
                GoodsInStockAddListener(goodsInStock, position)
                notifyItemChanged(position)
            } else {
                Toast.makeText(holder.itemView.context, "На складе недостаточно товаров", Toast.LENGTH_SHORT).show()
            }
        }
        holder.btRemove.setOnClickListener {
            repository.removeGoodsFromCart(goodsInStock.goodId, saleId)
            GoodsInStockRemoveListener(goodsInStock, position)
            notifyItemChanged(position)
        }
    }
}