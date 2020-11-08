package com.github.ElgorTheWanderer.AlbionDataClient;

import java.util.List;

public class ItemPriceTable {

    static class Entry {

        static class CityEntry {
            String name;
            int minSellPrice;
            int maxBuyPrice;
        }

        String itemId;
        String localizedItemName;
        List<CityEntry> cityEntryList;
    }
    List<Entry> entryList;
}
