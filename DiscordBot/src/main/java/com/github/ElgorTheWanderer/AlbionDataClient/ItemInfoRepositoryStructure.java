package com.github.ElgorTheWanderer.AlbionDataClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemInfoRepositoryStructure {

    List<UniqueItemEntry> itemEntryList = new ArrayList<>();
    Map<String, ArrayList<String>> mapOfIds = new HashMap<>();

    public static class UniqueItemEntry {

        public int index;
        public String uniqueName;
        public List<String> localizedNames = new ArrayList<>();
    }
}