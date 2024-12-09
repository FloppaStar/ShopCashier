package ru.floppastar.shopcashier

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.floppastar.shopcashier.Adapters.GoodsTypeAdapter
import ru.floppastar.shopcashier.Adapters.SelectGoodTypeAdapter
import ru.floppastar.shopcashier.DataClasses.GoodsInStock
import ru.floppastar.shopcashier.DataClasses.GoodsType
import ru.floppastar.shopcashier.db.DatabaseHelper
import ru.floppastar.shopcashier.db.dbRepository

class SelectGoodTypeFragment(var good: GoodsInStock) : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var repository: dbRepository
    private lateinit var selectGoodTypeAdapter: SelectGoodTypeAdapter
    private var goodsTypeList: MutableList<GoodsType> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_good_type, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repository = dbRepository(DatabaseHelper(view.context))
        recyclerView = view.findViewById(R.id.selectGoodTypeRecyclerView)
        goodsTypeList = repository.getAllGoodsType()
        selectGoodTypeAdapter = SelectGoodTypeAdapter(goodsTypeList, {goodsType, position ->
            good.goodsTypeId = goodsType.goodsTypeId
            if(good.goodId != 0)
                repository.editGoodsInStock(good)
            else
                repository.insertGoodsInStock(good.name, good.goodsCount, good.price, good.goodsTypeId)
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerViewMain, WarehouseFragment())
                .commit()
        })
        recyclerView.adapter = selectGoodTypeAdapter
    }
}