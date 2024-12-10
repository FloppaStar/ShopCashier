package ru.floppastar.shopcashier

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ru.floppastar.shopcashier.Adapters.GoodsInCartAdapter
import ru.floppastar.shopcashier.Adapters.GoodsInStockAdapter
import ru.floppastar.shopcashier.DataClasses.GoodsInStock
import ru.floppastar.shopcashier.db.DatabaseHelper
import ru.floppastar.shopcashier.db.dbRepository

class CashRegisterFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var repository: dbRepository
    private lateinit var goodsInCartAdapter: GoodsInCartAdapter
    private var goodsInStockList: MutableList<GoodsInStock> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cash_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repository = dbRepository(DatabaseHelper(view.context))
        recyclerView = view.findViewById(R.id.goodsInCartRecyclerView)
        goodsInStockList = repository.getAllGoodsInStock()
        goodsInCartAdapter = GoodsInCartAdapter(goodsInStockList, { goodsInStock, position ->
            updateTotalCost()
        }, { goodsInStock, position ->
            updateTotalCost()
        }, repository)

        recyclerView.adapter = goodsInCartAdapter

        val btSell = view.findViewById<Button>(R.id.btSell)
        btSell.setOnClickListener {
            completeSale()
        }

        updateTotalCost()
    }

    private fun completeSale() {
        val saleId = goodsInCartAdapter.saleId
        val success = repository.completeSale(saleId)
        if (success) {
            Toast.makeText(context, "Продажа завершена успешно", Toast.LENGTH_SHORT).show()
            goodsInStockList = repository.getAllGoodsInStock()
            goodsInCartAdapter.goodsInStockList = goodsInStockList
            goodsInCartAdapter.saleId = repository.getOrCreateSaleId()
            goodsInCartAdapter.notifyDataSetChanged()
            updateTotalCost()
        } else {
            Toast.makeText(context, "Ошибка при завершении продажи", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateTotalCost() {
        val totalCost = goodsInStockList.sumOf { goodsInStock ->
            val count = repository.getGoodsCountInCart(goodsInStock.goodId, goodsInCartAdapter.saleId)
            goodsInStock.price * count
        }

        view?.findViewById<TextView>(R.id.textView)?.text = "Общая стоимость: $totalCost рублей"
    }
}