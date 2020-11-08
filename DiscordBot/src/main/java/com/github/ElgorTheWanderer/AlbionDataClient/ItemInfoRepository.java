package com.github.ElgorTheWanderer.AlbionDataClient;


import java.util.List;

public interface ItemInfoRepository {
    public List<String> getUniqueIdByLocalizedName(String itemName);

}
