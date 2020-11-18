package com.github.ElgorTheWanderer.AlbionDataClient;

import java.util.List;

public interface PriceSearchEngine {
    public List<String> getUniqueIdByLocalizedName(String itemName, ItemInfoRepositoryStructure database);
}
