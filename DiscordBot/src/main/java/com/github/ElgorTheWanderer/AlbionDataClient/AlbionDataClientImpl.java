package com.github.ElgorTheWanderer.AlbionDataClient;

import reactor.netty.http.client.HttpClient;

import java.util.List;

public class AlbionDataClientImpl implements AlbionDataClient {

    private static final AlbionDataClientImpl instance = new AlbionDataClientImpl();
    ItemInfoRepository itemInfoRepository = new ItemInfoRepositoryImpl();
    AlbionDataClientResponseParser responseParser = new AlbionDataClientResponseParser();

    private AlbionDataClientImpl() {
    }

    public static AlbionDataClientImpl getInstance() {
        return instance;
    }

    @Override
    public ItemPriceTable findItemPrice(String itemName) {
        List<String> itemIdList = itemInfoRepository.getUniqueIdByLocalizedName(itemName);
        String targetUrl = buildUrl(itemIdList);
        String jsonReplyString = HttpClient.create().get().uri(targetUrl).responseContent().aggregate().asString().block();
        List<ItemPriceResponseEntry> responseEntryList = responseParser.getListOfEntries(jsonReplyString);


        ItemPriceTable itemPriceTable = new ItemPriceTable();
        for (String itemId : itemIdList) {
            ItemPriceTable.ItemEntry itemEntry = new ItemPriceTable.ItemEntry();
            itemEntry.itemId = itemId;
            itemEntry.localizedItemName = itemName;
            for (ItemPriceResponseEntry responseEntry : responseEntryList) {
                if (responseEntry.item_id.equals(itemId)) {
                    ItemPriceTable.ItemEntry.CityEntry cityEntry = new ItemPriceTable.ItemEntry.CityEntry();
                    cityEntry.maxBuyPrice = responseEntry.buy_price_max;
                    cityEntry.minSellPrice = responseEntry.sell_price_min;
                    cityEntry.name = responseEntry.city;
                    itemEntry.cityEntryList.add(cityEntry);
                }
            }
            itemPriceTable.entryList.add(itemEntry);
        }

        return itemPriceTable;
    }

    private String buildUrl(List list) {
        StringBuilder s = new StringBuilder();
        s.append(list.toString());
        String url = ("https://www.albion-online-data.com/api/v2/stats/prices/" + s + "?locations=&qualities=1").replace("[", "").replace("]", "").replace(" ", "");
        System.out.println(url); //Delete
        return url;
    }
}
