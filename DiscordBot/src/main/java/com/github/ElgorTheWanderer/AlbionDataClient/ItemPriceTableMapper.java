package com.github.ElgorTheWanderer.AlbionDataClient;

import java.util.List;

public interface ItemPriceTableMapper {
    ItemPriceTableStructure generateItemTableStructure(List<String> itemIdList, List<ItemPriceResponseEntry> responseEntryList, String itemName);
}
