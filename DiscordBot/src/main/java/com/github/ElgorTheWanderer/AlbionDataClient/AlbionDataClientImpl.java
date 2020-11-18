package com.github.ElgorTheWanderer.AlbionDataClient;

import reactor.netty.http.client.HttpClient;
import java.util.List;

public class AlbionDataClientImpl implements AlbionDataClient {

    private static final AlbionDataClientImpl instance = new AlbionDataClientImpl();
    PriceSearchEngine searchEngine = new PriceSearchEngineImpl();
    AlbionDataClientResponseParser responseParser = new AlbionDataClientResponseParser();
    ItemPriceTableGenerator itemPriceTableGenerator = new ItemPriceTableGeneratorImpl();
    ResponseFormatter responseFormatter = new ResponseFormatterImpl();


    private AlbionDataClientImpl() {

    }

    public static AlbionDataClientImpl getInstance() {
        return instance;
    }

    private String getItemPrice(String itemName, ItemInfoRepositoryStructure database){


        List<String> itemIdList = searchEngine.getUniqueIdByLocalizedName(itemName, database);
        String targetUrl = buildUrl(itemIdList);
        String jsonReplyString = HttpClient.create().get().uri(targetUrl).responseContent().aggregate().asString().block();
        List<ItemPriceResponseEntry> responseEntryList = responseParser.getListOfEntries(jsonReplyString);
        return responseFormatter.createResponse(itemPriceTableGenerator.fillItemTableStructure(itemIdList, responseEntryList, itemName), itemName);
    }

    private String buildUrl(List list) {
        StringBuilder s = new StringBuilder();
        s.append(list.toString());
        System.out.println(list.toString());
        String url = ("https://www.albion-online-data.com/api/v2/stats/prices/" + s + "?locations=&qualities=1").replace("[", "").replace("]", "").replace(" ", "");
        System.out.println(url);
        return url;
    }
    @Override
    public String findItemPrice(String itemName, ItemInfoRepositoryStructure database) {
    return getItemPrice(itemName, database);
    }
}
