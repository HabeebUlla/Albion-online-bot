package com.github.ElgorTheWanderer.AlbionDataClient;

import java.util.ArrayList;
import java.util.List;


public class ResponseFormatterImpl implements ResponseFormatter{

    private String getItemTableStructureFields(ItemPriceTableStructure result){

        String header = String.format("|%-13s|","City name, ");
            for (ItemPriceTableStructure.ItemEntry itemEntry : result.entryList) {
                String s = String.format("%-15s|", itemEntry.itemId);
                header = header.concat(s);
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
                String cityRow = String.format("|%-13s|", cityName);

                for (ItemPriceTableStructure.ItemEntry itemEntry : result.entryList) {
                    boolean foundCityEntry = false;
                    for (ItemPriceTableStructure.ItemEntry.CityEntry cityEntry : itemEntry.cityEntryList) {
                        if (cityEntry.name.equals(cityName)) {
                            String s = String.format("%-15s|", (cityEntry.minSellPrice + "/" + cityEntry.maxBuyPrice));
                            cityRow = cityRow.concat(s);
                            foundCityEntry = true;
                            break;
                        }
                    }
                    if (!foundCityEntry){
                        String s = String.format("%-15s|", "n/a");
                        cityRow = cityRow.concat(s);

                    }
                }
                cityRow = cityRow.concat("\n");
                responseBody = responseBody.concat(cityRow);
            }
            final String responseMessage = "```" + header + responseBody + "```";
            return responseMessage;
    }

    @Override
    public String createResponse(ItemPriceTableStructure itemPriceTableStructure) {
        return getItemTableStructureFields(itemPriceTableStructure);
    }
}
