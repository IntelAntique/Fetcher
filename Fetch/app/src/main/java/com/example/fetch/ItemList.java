package com.example.fetch;

import java.util.List;

public class ItemList {
    private int listId;
    private List<Item> items;

    public ItemList(int listId, List<Item> items) {
        this.listId = listId;
        this.items = items;
    }

    public int getListId() {
        return listId;
    }

    public List<Item> getItems() {
        return items;
    }
}
