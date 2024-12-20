package ru.floppastar.shopcashier

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.floppastar.shopcashier.Adapters.GoodsTypeAdapter
import ru.floppastar.shopcashier.DataClasses.GoodsType
import ru.floppastar.shopcashier.db.DatabaseHelper
import ru.floppastar.shopcashier.db.dbRepository

class GoodsTypeFragment : Fragment() {
    private lateinit var gridView: GridView
    private lateinit var repository: dbRepository
    private lateinit var goodsTypeAdapter: GoodsTypeAdapter
    private var goodsTypeList: MutableList<GoodsType> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_goods_type, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repository = dbRepository(DatabaseHelper(view.context))
        gridView = view.findViewById(R.id.goodsTypeGridView)
        goodsTypeList = repository.getAllGoodsType()
        goodsTypeAdapter = GoodsTypeAdapter(goodsTypeList) { goodsType ->
            (activity as? MainActivity)?.openEditGoodsTypeFragment(goodsType)
        }

        gridView.adapter = goodsTypeAdapter

        val btAddGoodsType = view.findViewById<FloatingActionButton>(R.id.fabAddGoodsType)
        btAddGoodsType.setOnClickListener {
            (activity as? MainActivity)?.openEditGoodsTypeFragment(null)
        }
    }
}