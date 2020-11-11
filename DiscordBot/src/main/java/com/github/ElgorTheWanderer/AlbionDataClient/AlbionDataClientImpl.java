package com.github.ElgorTheWanderer.AlbionDataClient;

import reactor.netty.http.client.HttpClient;
import java.util.List;

public class AlbionDataClientImpl implements AlbionDataClient{

    private static final AlbionDataClientImpl instance = new AlbionDataClientImpl();
    private AlbionDataClientImpl() {
    }
    public static AlbionDataClientImpl getInstance() {
        return instance;
    }

   ItemInfoRepository dataClient = new ItemInfoRepositoryImpl();


    @Override
    public String findItemPrice(String itemName){
    String targetUrl = buildUrl(dataClient.getUniqueIdByLocalizedName(itemName));
    String jsonReplyString = HttpClient.create().get().uri(targetUrl).responseContent().aggregate().asString().block();
    System.out.println(jsonReplyString); //Delete.

    /*  1 - get item IDs from ItemInfoRepository. -
    2 - build URL to get item prices. +
    3 - send request to albion-data-project. +
    4 - parse response. -
    5 - create price table from parsed result. -
 */
        return jsonReplyString.substring(0, 100);
    }

    private String buildUrl(List list) {
        StringBuilder s = new StringBuilder();
        s.append(list.toString());
        String url = ("https://www.albion-online-data.com/api/v2/stats/prices/" + s +"?locations=&qualities=1").replace("[", "").replace("]", "").replace(" ", "");
        System.out.println(url); //Delete
        return url;
    }
}
