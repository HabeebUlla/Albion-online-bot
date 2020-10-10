package com.github.ElgorTheWanderer.AlbionClient;

import com.github.ElgorTheWanderer.PlayerStructure;

public interface AlbionClient {
    public PlayerStructure findPlayerByName(String accountName) throws Exception;
}

