package com.github.ElgorTheWanderer.AlbionDataClient;

import java.util.HashMap;
import java.util.List;




public class ItemInfoRepositoryImpl implements ItemInfoRepository{

/*Create map.
Iterate through items.json.
Add "LocalizedNames" as keys to "UniqueName".
Write map to local file.
Read map from file.
Search through map for itemName.
*/

    HashMap<String, String> itemDataBase= new HashMap();
    


    @Override
    public List<String> getUniqueIdByLocalizedName(String itemName) {
        if (!itemName.toLowerCase().equals("bag")){

        }
        return List.of("T4_BAG", "T4_BAG@1");
//            return Arrays.asList("T4_BAG", "T4_BAG@1", "T4_BAG@2", "T4_BAG@3");
    }

}
