package ru.floppastar.shopcashier.DataClasses

data class GoodsInStock(
    val goodId: Int,
    var name: String,
    var goodsCount: Int,
    var price: Double,
    var goodsTypeId: Int
)