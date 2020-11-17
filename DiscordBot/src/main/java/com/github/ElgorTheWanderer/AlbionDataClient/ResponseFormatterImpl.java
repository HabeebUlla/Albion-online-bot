package com.github.ElgorTheWanderer.AlbionDataClient;

import java.util.ArrayList;
import java.util.List;

public class ResponseFormatterImpl implements ResponseFormatter{

    private String getItemTableStructureFields(ItemPriceTableStructure result){

            String header = "City name ";
            for (ItemPriceTableStructure.ItemEntry itemEntry : result.entryList) {
                header = header.concat(itemEntry.itemId + " | ");
            }
            header = header.concat("\n");

            List<String> cityNameList = new ArrayList<>();
            for (ItemPriceTableStructure.ItemEntry itemEntry : result.entryList) {
                for (ItemPriceTableStructure.ItemEntry.CityEntry cityEntry : itemEntry.cityEntryList) {
                    if(cityNameList.contains(cityEntry.name)){
                        break;
                    }
                    cityNameList.add(cityEntry.name);
                }

            }

            String responseBody = "";

            for (String cityName : cityNameList) {
                String cityRow = cityName;

                for (ItemPriceTableStructure.ItemEntry itemEntry : result.entryList) {
                    boolean foundCityEntry = false;
                    for (ItemPriceTableStructure.ItemEntry.CityEntry cityEntry : itemEntry.cityEntryList) {
                        if (cityEntry.name.equals(cityName)) {
                            cityRow = cityRow.concat(" | " + cityEntry.minSellPrice + " / " + cityEntry.maxBuyPrice + " | ");
                            foundCityEntry = true;
                            break;
                        }
                    }
                    if (!foundCityEntry){
                        cityRow = cityRow.concat("n/a");
                    }
                }
                cityRow = cityRow.concat("\n");
                responseBody = responseBody.concat(cityRow);
            }
            final String responseMessage = header + responseBody;
            return responseMessage;
    }

    @Override
    public String createResponse(ItemPriceTableStructure itemPriceTableStructure) {
        return getItemTableStructureFields(itemPriceTableStructure);
    }
}
