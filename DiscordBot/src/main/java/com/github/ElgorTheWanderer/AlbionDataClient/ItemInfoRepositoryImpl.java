package com.github.ElgorTheWanderer.AlbionDataClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class ItemInfoRepositoryImpl implements ItemInfoRepository {

    private final ItemInfoRepositoryStructure database = new ItemInfoRepositoryStructure();

    public List<String> getItemIdList(String itemName) {

        List<String> itemIdList = database.mapOfIds.get(itemName);
        return itemIdList;
    }

    @Override
    public void initializeDatabase(String databasePath) {

        try {
            JSONArray jsonArray = new JSONArray(Files.readString(Paths.get(databasePath)));
            for (int i = 0; i < jsonArray.length(); i++) {
                if (!jsonArray.getJSONObject(i).isNull("LocalizedNames")) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    ItemInfoRepositoryStructure.UniqueItemEntry uniqueItemEntry = new ItemInfoRepositoryStructure.UniqueItemEntry();
                    uniqueItemEntry.index = object.getInt("Index");
                    uniqueItemEntry.uniqueName = object.getString("UniqueName");
                    JSONObject names = object.getJSONObject("LocalizedNames");
                    uniqueItemEntry.localizedNames.add(names.getString("EN-US"));
                    uniqueItemEntry.localizedNames.add(names.getString("RU-RU"));
                    database.itemEntryList.add(uniqueItemEntry);
                }
            }

            for (ItemInfoRepositoryStructure.UniqueItemEntry entry : database.itemEntryList) {
                for (String name : entry.localizedNames) {
                    if (database.mapOfIds.containsKey(name)) {
                        database.mapOfIds.get(name).add(entry.uniqueName);
                    } else {
                        ArrayList<String> l = new ArrayList<>();
                        database.mapOfIds.put(name, l);
                        l.add(entry.uniqueName);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}