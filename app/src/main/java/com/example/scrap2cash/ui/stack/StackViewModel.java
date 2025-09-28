package com.example.scrap2cash.ui.stack;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class StackViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private final MutableLiveData<ArrayList<stackmodel>> scrapItemsLiveData;
    private ArrayList<stackmodel> scrapItemsList;

    public StackViewModel() {
        scrapItemsLiveData = new MutableLiveData<>();
        scrapItemsList = new ArrayList<>();
        scrapItemsLiveData.setValue(scrapItemsList);
    }

    //LiveData getter //(yeh return kar rha hai)
    public LiveData<ArrayList<stackmodel>> getScrapItems() {
        return scrapItemsLiveData;
    }

    // Load items from DB (yeh load kar rha hai)
    public void loadItemsFromDB(StackDB db) {
        scrapItemsList = db.getAllScrap();
        scrapItemsLiveData.setValue(scrapItemsList);
    }

    // Add new item (yeh add kar rha hai)
    public void addItem(stackmodel item, StackDB db) {
        // Insert into DB
        long newId = db.insertScrap(item);
        item.id = (int) newId; // assign DB id to object

        // Add to list and update LiveData
        scrapItemsList.add(item);
        scrapItemsLiveData.setValue(scrapItemsList);
    }

    // Delete item by id  (yeh delete kar rha hai)
    public void deleteItem(int id, StackDB db) {
        db.deleteItem(id);

        // Remove from list and update LiveData
        for (int i = 0; i < scrapItemsList.size(); i++) {
            if (scrapItemsList.get(i).id == id) {
                scrapItemsList.remove(i);
                break;
            }
        }
        scrapItemsLiveData.setValue(scrapItemsList);
    }

    // Optional: Update item (like sellLater checkbox) yeh tere uss check box ke liye hai
    public void updateItem(stackmodel item, StackDB db) {
        db.updateScrap(item); // you can implement updateScrap in StackDB
        if (scrapItemsList == null) scrapItemsList = new ArrayList<>();

        for (int i = 0; i < scrapItemsList.size(); i++) {
            if (scrapItemsList.get(i).id == item.id) {
                scrapItemsList.set(i, item);
                scrapItemsLiveData.setValue(new ArrayList<>(scrapItemsList));
                return;
            }
        }
        // If item not found, reload full list
        loadItemsFromDB(db);

    }
}