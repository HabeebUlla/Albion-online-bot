package com.github.ElgorTheWanderer.AlbionDataClient;

import java.util.List;

public interface ItemPriceTableGenerator {
    public ItemPriceTableStructure fillItemTableStructure (List<String> itemIdList, List<ItemPriceResponseEntry> responseEntryList, String itemName);
}
