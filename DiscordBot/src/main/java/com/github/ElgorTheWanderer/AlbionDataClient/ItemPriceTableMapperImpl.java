package com.github.ElgorTheWanderer.AlbionDataClient;

import java.util.List;

class ItemPriceTableMapperImpl implements ItemPriceTableMapper {

    @Override
    public ItemPriceTableStructure generateItemTableStructure(List<String> itemIdList, List<ItemPriceResponseEntry> responseEntryList, String itemName) {
        ItemPriceTableStructure itemPriceTableStructure = new ItemPriceTableStructure();

        for (String itemId : itemIdList) {
            ItemPriceTableStructure.ItemEntry itemEntry = new ItemPriceTableStructure.ItemEntry();
            itemEntry.itemId = itemId;
            itemEntry.localizedItemName = itemName;
            for (ItemPriceResponseEntry responseEntry : responseEntryList) {
                if (responseEntry.item_id.equals(itemId)) {
                    ItemPriceTableStructure.ItemEntry.CityEntry cityEntry = new ItemPriceTableStructure.ItemEntry.CityEntry();
                    cityEntry.maxBuyPrice = responseEntry.buy_price_max;
                    cityEntry.minSellPrice = responseEntry.sell_price_min;
                    cityEntry.name = responseEntry.city;
                    itemEntry.cityEntryList.add(cityEntry);
                }
            }
            itemPriceTableStructure.entryList.add(itemEntry);
        }
        return itemPriceTableStructure;
    }
}
