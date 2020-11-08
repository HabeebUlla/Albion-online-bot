package com.github.ElgorTheWanderer.AlbionDataClient;

import java.util.Arrays;
import java.util.List;

public class ItemInfoRepositoryImpl implements ItemInfoRepository{


    @Override
    public List<String> getUniqueIdByLocalizedName(String itemName) {
        if (itemName.toLowerCase().equals("bag")){
            return Arrays.asList("T4_BAG", "T4_BAG@1", "T4_BAG@2", "T4_BAG@3");
        }
        return null;
    }
}
