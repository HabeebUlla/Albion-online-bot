package com.github.ElgorTheWanderer.AlbionDataClient;

import java.util.List;

public interface ItemPriceTableMessageFormatter {
    String formatItemPriceTable(ItemPriceTableStructure itemPriceTableStructure);

    List<String> getCityNameList(ItemPriceTableStructure itemPriceTableStructure);
}
