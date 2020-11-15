package com.github.ElgorTheWanderer.AlbionDataClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AlbionDataClientResponseParser {

    public List<ItemPriceResponseEntry> getListOfEntries(String jsonString){
        List<ItemPriceResponseEntry> entryList = new ArrayList<>();
        JSONArray jsonArray= new JSONArray(jsonString);
        for(int i = 0; i < jsonArray.length(); i++){
            ItemPriceResponseEntry entry = new ItemPriceResponseEntry();
            JSONObject object = jsonArray.getJSONObject(i);
            entry.item_id = object.getString("item_id");
            entry.city = object.getString("city");;
            entry.quality = object.getInt("quality");
            entry.buy_price_min = object.getInt("buy_price_min");
            entry.buy_price_min_date = object.getString("buy_price_min_date");
            entry.buy_price_max = object.getInt("buy_price_max");
            entry.buy_price_max_date = object.getString("buy_price_max_date");
            entry.sell_price_min = object.getInt("sell_price_min");
            entry.sell_price_min_date = object.getString("sell_price_min_date");
            entry.sell_price_max = object.getInt("sell_price_max");
            entry.sell_price_max_date = object.getString("sell_price_max_date");
            entryList.add(entry);
        }
        return entryList;
    }

}
