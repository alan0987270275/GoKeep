package com.example.gokeep.data.model


data class CategoryViewData (
    val imageId: Int,
    val title: String,
    var isSelected: Boolean = false
)

fun getTagByPosition(position: Int) = categoryDataList[position].title

fun getTagImageByTitle(name: String) = categoryDataList.find { data -> data.title.equals(name) }?.imageId

val categoryDataList = kotlin.collections.listOf(
    CategoryViewData(
        com.example.gokeep.R.drawable.ic_icon_income,
        "Income"
    ),
    CategoryViewData(
        com.example.gokeep.R.drawable.ic_outlined_flag_black_24dp,
        "Goal"
    ),
    CategoryViewData(
        com.example.gokeep.R.drawable.ic_card_giftcard_black_24dp,
        "Shopping"
    ),
    CategoryViewData(
        com.example.gokeep.R.drawable.ic_commute_black_24dp,
        "Transport"
    ),
    CategoryViewData(
        com.example.gokeep.R.drawable.ic_local_grocery_store_black_24dp,
        "Grocery"
    ),
    CategoryViewData(
        com.example.gokeep.R.drawable.ic_restaurant_black_24dp,
        "Restaurant"
    ),
    CategoryViewData(
        com.example.gokeep.R.drawable.ic_request_quote_black_24dp,
        "Billing"
    )
)