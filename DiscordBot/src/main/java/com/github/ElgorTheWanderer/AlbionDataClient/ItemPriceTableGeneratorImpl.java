package com.github.ElgorTheWanderer.AlbionDataClient;

import java.util.List;

public class ItemPriceTableGeneratorImpl implements ItemPriceTableGenerator{

    private ItemPriceTableStructure generateItemTableStructure (List<String> itemIdList, List<ItemPriceResponseEntry> responseEntryList, String itemName){
        ItemPriceTableStructure itemPriceTableStructure = new ItemPriceTableStructure();
        OUTER:  for (String itemId : itemIdList) {
            ItemPriceTableStructure.ItemEntry itemEntry = new ItemPriceTableStructure.ItemEntry();
            itemEntry.itemId = itemId;
            itemEntry.localizedItemName = itemName;

            INNER:      for (ItemPriceResponseEntry responseEntry : responseEntryList) {

                if (responseEntry.item_id.equals(itemId)) {
                    ItemPriceTableStructure.ItemEntry.CityEntry cityEntry = new ItemPriceTableStructure.ItemEntry.CityEntry();
                    cityEntry.maxBuyPrice = responseEntry.buy_price_max;
                    cityEntry.minSellPrice = responseEntry.sell_price_min;
                    cityEntry.name = responseEntry.city;
                    for (ItemPriceTableStructure.ItemEntry.CityEntry city : itemEntry.cityEntryList) {
                        if (city.name.equals(responseEntry.city)) {
                            break INNER;
                        }
                    }
                    itemEntry.cityEntryList.add(cityEntry);
                }
            }
            itemPriceTableStructure.entryList.add(itemEntry);
        }
        return itemPriceTableStructure;
    }

    @Override
    public ItemPriceTableStructure fillItemTableStructure(List<String> itemIdList, List<ItemPriceResponseEntry> responseEntryList, String itemName){
        return generateItemTableStructure(itemIdList, responseEntryList, itemName);
    }

}
