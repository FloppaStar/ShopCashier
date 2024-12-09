package ru.floppastar.shopcashier.Adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.floppastar.shopcashier.GoodsInStockFragment
import ru.floppastar.shopcashier.GoodsTypeFragment

class TabLayoutAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->GoodsInStockFragment()
            1->GoodsTypeFragment()
            else->GoodsInStockFragment()
        }
    }

}