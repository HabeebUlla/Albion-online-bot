package com.github.ElgorTheWanderer.AlbionDataClient;

import java.util.ArrayList;
import java.util.List;

public class ItemPriceTableStructure {

    public List<ItemEntry> entryList = new ArrayList<>();

    public static class ItemEntry {

        public String itemId;
        public String localizedItemName;
        public List<CityEntry> cityEntryList = new ArrayList<>();

        public static class CityEntry {
            public String name;
            public int minSellPrice;
            public int maxBuyPrice;
        }
    }
}
