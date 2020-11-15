package com.github.ElgorTheWanderer.AlbionDataClient;

import java.util.ArrayList;
import java.util.List;

public class ItemPriceTable {

    static class ItemEntry {

        static class CityEntry {
            String name;
            int minSellPrice;
            int maxBuyPrice;
        }

        String itemId;
        String localizedItemName;
        List<CityEntry> cityEntryList = new ArrayList<>();
    }
    List<ItemEntry> entryList = new ArrayList<>();
}
