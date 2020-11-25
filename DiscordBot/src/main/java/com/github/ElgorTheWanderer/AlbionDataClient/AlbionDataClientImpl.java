package com.github.ElgorTheWanderer.AlbionDataClient;

import reactor.netty.http.client.HttpClient;

import java.util.List;

public class AlbionDataClientImpl implements AlbionDataClient {

    private static final String PRICE_ENDPOINT = "https://www.albion-online-data.com/api/v2/stats/prices/";
    private static final String ITEM_QUALITY = "?locations=&qualities=1";

    AlbionDataClientResponseParser responseParser = new AlbionDataClientResponseParser();
    ItemPriceTableMapper ItemPriceTableMapper = new ItemPriceTableMapperImpl();
    ItemInfoRepositoryImpl itemInfoRepository = new ItemInfoRepositoryImpl();
    @Override
    public ItemPriceTableStructure getItemPrice(String itemName) {

        List<String> itemIdList = itemInfoRepository.getItemIdList(itemName);
        String targetUrl = buildUrl(itemIdList);
        String jsonReplyString = HttpClient.create().get().uri(targetUrl).responseContent().aggregate().asString().block();
        List<ItemPriceResponseEntry> responseEntryList = responseParser.getListOfEntries(jsonReplyString);
        return ItemPriceTableMapper.generateItemTableStructure(itemIdList, responseEntryList, itemName);
    }

    private String buildUrl(List list) {
        StringBuilder s = new StringBuilder();
        s.append(list.toString());
        return (PRICE_ENDPOINT + s + ITEM_QUALITY).replace("[", "")
                .replace("]", "").replace(" ", "");
    }
}