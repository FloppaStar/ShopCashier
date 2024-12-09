package ru.floppastar.shopcashier

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import ru.floppastar.shopcashier.DataClasses.GoodsType
import ru.floppastar.shopcashier.db.DatabaseHelper
import ru.floppastar.shopcashier.db.dbRepository

class EditGoodsTypeFragment(var goodType: GoodsType?) : Fragment() {
    private lateinit var repository: dbRepository
    lateinit var name: EditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_goods_type, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        name = view.findViewById(R.id.etGoodsTypeName)
        if (goodType != null)
            initializeFragment()
        else
            goodType = GoodsType(0, "")
        val btSave = view.findViewById<Button>(R.id.btSaveGoodsType)
        repository = dbRepository(DatabaseHelper(view.context))
        btSave.setOnClickListener {
            saveData()
            try {
                if(goodType!!.goodsTypeId != 0)
                    repository.editGoodsType(goodType!!)
                else
                    repository.insertGoodsType(goodType!!.name)
                parentFragmentManager.popBackStack()
            }
            catch (e: Exception){
                Log.d("uwu", "Error ${e.message}")
            }
        }
    }
    private fun saveData(){
        goodType!!.name = name.text.toString()
    }
    private fun initializeFragment(){
        name.setText(goodType!!.name)
    }
}