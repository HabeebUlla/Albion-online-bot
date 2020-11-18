package com.github.ElgorTheWanderer.AlbionDataClient;

import java.util.List;

public class PriceSearchEngineImpl implements PriceSearchEngine {

    private List<String> setListOfItems(String itemName, ItemInfoRepositoryStructure database) {

        List<String> list = database.mapOfIds.get(itemName);

        return list;
    }

    @Override
    public List<String> getUniqueIdByLocalizedName(String itemName, ItemInfoRepositoryStructure database) {
        return setListOfItems(itemName, database);
    }
}

