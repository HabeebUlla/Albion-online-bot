package com.github.ElgorTheWanderer.AlbionDataClient;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ItemPriceTableMessageFormatterImpl implements ItemPriceTableMessageFormatter {

    private static final String BACKTICKS = "```";
    private static final int MAX_STRING_LENGTH = 77;

    private final NumberFormat fmt = NumberFormat.getCompactNumberInstance(
            new Locale("EN", "US"), NumberFormat.Style.SHORT);

    {
        fmt.setRoundingMode(RoundingMode.HALF_UP);
        fmt.setMinimumFractionDigits(2);
        fmt.setMaximumFractionDigits(2);
    }

    private String formatCityColumnCell(String content) {
        return String.format("%-13s", content);
    }

    private String formatItemColumnCell(String content) {
        return String.format("%-15s", content);
    }

    @Override
    public String formatItemPriceTable(ItemPriceTableStructure result) {

        String header = formatCityColumnCell("City name");
        for (ItemPriceTableStructure.ItemEntry itemEntry : result.entryList) {
            String itemCell;
            if (itemEntry.itemId.contains("@")) {
                itemCell = itemEntry.itemId.substring(0, 2) + "."
                        + itemEntry.itemId.substring(itemEntry.itemId.indexOf("@") + 1);
            } else {
                itemCell = itemEntry.itemId.substring(0, 2) + ".0";
            }
            header = header.concat("|" + formatItemColumnCell(itemCell));
        }

        header = header.concat("\n");
        String responseBody = "";
        List<String> cityNameList = getCityNameList(result);
        for (String cityName : cityNameList) {
            String cityRow = formatCityColumnCell(cityName);
            for (ItemPriceTableStructure.ItemEntry itemEntry : result.entryList) {
                ItemPriceTableStructure.ItemEntry.CityEntry entry = null;
                for (ItemPriceTableStructure.ItemEntry.CityEntry cityEntry : itemEntry.cityEntryList) {
                    if (cityEntry.name.equals(cityName)) {
                        entry = cityEntry;
                        break;
                    }
                }
                if (entry != null) {
                    cityRow = cityRow.concat("|" + formatItemColumnCell((fmt.format(entry.minSellPrice))
                            + "/" + (fmt.format(entry.maxBuyPrice))));
                } else {
                    cityRow = cityRow.concat("|" + formatItemColumnCell("n/a"));
                }
            }
            cityRow = cityRow.concat("\n");
            responseBody = responseBody.concat(cityRow);
        }


        String divider = "=".repeat(MAX_STRING_LENGTH);
        final String responseMessage = BACKTICKS + result.entryList.get(0).localizedItemName + "\n" + header + divider
                + "\n" + responseBody + BACKTICKS;
        return responseMessage;
    }

    @Override
    public List<String> getCityNameList(ItemPriceTableStructure result) {

        List<String> cityNameList = new ArrayList<>();
        for (ItemPriceTableStructure.ItemEntry itemEntry : result.entryList) {
            for (ItemPriceTableStructure.ItemEntry.CityEntry cityEntry : itemEntry.cityEntryList) {
                if (cityNameList.contains(cityEntry.name)) {
                    break;
                }
                cityNameList.add(cityEntry.name);
            }

        }
        return cityNameList;
    }
}