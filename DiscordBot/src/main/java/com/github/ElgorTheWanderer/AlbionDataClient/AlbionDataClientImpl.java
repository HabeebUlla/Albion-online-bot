package com.github.ElgorTheWanderer.AlbionDataClient;

import reactor.netty.http.client.HttpClient;

import java.util.List;

public class AlbionDataClientImpl implements AlbionDataClient {

    private static final String PRICE_ENDPOINT = "https://www.albion-online-data.com/api/v2/stats/prices/";
    private static final String ITEM_QUALITY = "?locations=&qualities=1";


    AlbionDataClientResponseParser responseParser = new AlbionDataClientResponseParser();
    ItemPriceTableMapper ItemPriceTableMapper = new ItemPriceTableMapperImpl();
    ItemInfoRepositoryImpl itemInfoRepository;

    public AlbionDataClientImpl(ItemInfoRepositoryImpl itemInfoRepository) {
        this.itemInfoRepository = itemInfoRepository;
    }

    @Override
    public ItemPriceTableStructure getItemPrice(String itemName) {

        List<String> itemIdList = itemInfoRepository.getItemIdList(itemName);
        String targetUrl = buildUrl(itemIdList);
        String jsonReplyString = HttpClient.create().get().uri(targetUrl).responseContent().aggregate().asString().block();
        List<ItemPriceResponseEntry> responseEntryList = responseParser.getListOfEntries(jsonReplyString);
        return ItemPriceTableMapper.generateItemTableStructure(itemIdList, responseEntryList, itemName);
    }

    private String buildUrl(List list) {
        String s = String.join(",", list);
        return (PRICE_ENDPOINT + s + ITEM_QUALITY);
    }
}