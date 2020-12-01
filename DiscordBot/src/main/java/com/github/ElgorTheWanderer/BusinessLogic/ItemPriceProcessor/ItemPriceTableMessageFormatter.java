package com.github.ElgorTheWanderer.BusinessLogic.ItemPriceProcessor;

import com.github.ElgorTheWanderer.AlbionDataClient.ItemPriceTableStructure;

import java.util.List;

public interface ItemPriceTableMessageFormatter {
    String formatItemPriceTable(ItemPriceTableStructure itemPriceTableStructure);

    List<String> getCityNameList(ItemPriceTableStructure itemPriceTableStructure);
}
