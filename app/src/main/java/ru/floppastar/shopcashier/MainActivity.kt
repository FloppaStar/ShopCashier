package ru.floppastar.shopcashier

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.floppastar.shopcashier.DataClasses.GoodsInStock
import ru.floppastar.shopcashier.DataClasses.GoodsType

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bnv = findViewById<BottomNavigationView>(R.id.bottomMenu)
        bnv.setOnItemSelectedListener {
            when (it.itemId){
                R.id.cashRegister -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerViewMain, CashRegisterFragment())
                        .commit()
                    true
                }
                R.id.warehouse -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerViewMain, WarehouseFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }
    }
    fun openEditGoodsTypeFragment(goodsType: GoodsType?) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerViewMain, EditGoodsTypeFragment(goodsType))
            .addToBackStack(null)
            .commit()
    }
    fun openEditGoodsInStockFragment(good: GoodsInStock?) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerViewMain, EditGoodsInStockFragment(good))
            .addToBackStack(null)
            .commit()
    }
}