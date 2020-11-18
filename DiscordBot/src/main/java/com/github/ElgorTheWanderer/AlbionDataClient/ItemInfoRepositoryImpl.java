package com.github.ElgorTheWanderer.AlbionDataClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ItemInfoRepositoryImpl implements ItemInfoRepository {

    private ItemInfoRepositoryStructure iterateItemDatabase() throws IOException {

        ItemInfoRepositoryStructure database = new ItemInfoRepositoryStructure();

        try {
            JSONArray jsonArray = new JSONArray(Files.readString(Paths.get("items.json")));
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
                for (String s : entry.localizedNames) {
                    if (database.mapOfIds.containsKey(s)) {
                        database.mapOfIds.get(s).add(entry.uniqueName);
                    } else {ArrayList<String> l = new ArrayList<>();
                        database.mapOfIds.put(s, l);
                        l.add(entry.uniqueName);
                    }

                }
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }

        return database;
    }

    @Override
    public ItemInfoRepositoryStructure iterateDatabase() throws IOException {
        return iterateItemDatabase();
    }
}


