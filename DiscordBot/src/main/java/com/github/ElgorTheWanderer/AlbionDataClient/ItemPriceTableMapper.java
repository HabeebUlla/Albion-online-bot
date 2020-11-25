package com.github.ElgorTheWanderer.AlbionDataClient;

import java.util.List;

public interface ItemPriceTableMapper {
    public ItemPriceTableStructure generateItemTableStructure (List<String> itemIdList, List<ItemPriceResponseEntry> responseEntryList, String itemName);
}
