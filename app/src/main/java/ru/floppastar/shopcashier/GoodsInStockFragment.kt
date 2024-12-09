package ru.floppastar.shopcashier

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.floppastar.shopcashier.Adapters.GoodsInStockAdapter
import ru.floppastar.shopcashier.Adapters.GoodsTypeAdapter
import ru.floppastar.shopcashier.DataClasses.GoodsInStock
import ru.floppastar.shopcashier.DataClasses.GoodsType
import ru.floppastar.shopcashier.db.DatabaseHelper
import ru.floppastar.shopcashier.db.dbRepository

class GoodsInStockFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var repository: dbRepository
    private lateinit var goodsInStockAdapter: GoodsInStockAdapter
    private var goodsInStockList: MutableList<GoodsInStock> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_goods_in_stock, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repository = dbRepository(DatabaseHelper(view.context))
        recyclerView = view.findViewById(R.id.goodsInStockRecyclerView)
        goodsInStockList = repository.getAllGoodsInStock()
        goodsInStockAdapter = GoodsInStockAdapter(goodsInStockList, { position ->
            repository.deleteGoodsInStock(goodsInStockList[position].goodId)
        }, { goodsInStock, position ->
            (activity as? MainActivity)?.openEditGoodsInStockFragment(goodsInStock)
        }, repository)

        recyclerView.adapter = goodsInStockAdapter

        val btAddGoodsInStock = view.findViewById<FloatingActionButton>(R.id.fabAddGoodsInStock)
        btAddGoodsInStock.setOnClickListener {
            (activity as? MainActivity)?.openEditGoodsInStockFragment(null)
        }
    }
}