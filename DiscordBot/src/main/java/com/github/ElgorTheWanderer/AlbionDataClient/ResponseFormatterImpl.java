package com.github.ElgorTheWanderer.AlbionDataClient;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ResponseFormatterImpl implements ResponseFormatter {

    NumberFormat fmt = NumberFormat.getCompactNumberInstance(
            new Locale("EN", "US"), NumberFormat.Style.SHORT);


    private String getItemTableStructureFields(ItemPriceTableStructure result, String itemName) {
        int n = 0;
        String header = String.format("%-13s", "City name");
        for (ItemPriceTableStructure.ItemEntry itemEntry : result.entryList) {

            String s = String.format("|%-15s", itemEntry.itemId.substring(0, 2) + "." + n);
            header = header.concat(s);
            n++;
        }
        header = header.concat("\n");

        List<String> cityNameList = new ArrayList<>();
        for (ItemPriceTableStructure.ItemEntry itemEntry : result.entryList) {
            for (ItemPriceTableStructure.ItemEntry.CityEntry cityEntry : itemEntry.cityEntryList) {
                if (cityNameList.contains(cityEntry.name)) {
                    break;
                }
                cityNameList.add(cityEntry.name);
            }

        }

        String responseBody = "";

        for (String cityName : cityNameList) {
            String cityRow = String.format("%-13s", cityName);

            for (ItemPriceTableStructure.ItemEntry itemEntry : result.entryList) {
                boolean foundCityEntry = false;
                for (ItemPriceTableStructure.ItemEntry.CityEntry cityEntry : itemEntry.cityEntryList) {
                    if (cityEntry.name.equals(cityName)) {
                        fmt.setRoundingMode(RoundingMode.HALF_UP);
                        fmt.setMinimumFractionDigits(2);
                        fmt.setMaximumFractionDigits(2);
                        String s = String.format("|%-15s", (fmt.format(cityEntry.minSellPrice)) + "/" + (fmt.format(cityEntry.maxBuyPrice)));
                        cityRow = cityRow.concat(s);
                        foundCityEntry = true;
                        break;
                    }
                }
                if (!foundCityEntry) {
                    String s = String.format("|%-15s", "n/a");
                    cityRow = cityRow.concat(s);

                }
            }
            cityRow = cityRow.concat("\n");
            responseBody = responseBody.concat(cityRow);
        }

        StringBuffer outputBuffer = new StringBuffer(77);
        for (int i = 0; i < 77; i++) {
            outputBuffer.append("=");
        }
        String divider = outputBuffer.toString();

        final String responseMessage = "```" + itemName + "\n" + header + divider + "\n" + responseBody + "```";
        return responseMessage;
    }

    @Override
    public String createResponse(ItemPriceTableStructure itemPriceTableStructure, String itemName) {
        return getItemTableStructureFields(itemPriceTableStructure, itemName);
    }
}
