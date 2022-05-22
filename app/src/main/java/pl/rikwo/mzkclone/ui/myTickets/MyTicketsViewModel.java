package pl.rikwo.mzkclone.ui.myTickets;

import android.app.Application;
import android.database.Cursor;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.rikwo.mzkclone.DatabaseHelper;

public class MyTicketsViewModel  extends AndroidViewModel {
    private static final String TAG = "MyTicketsViewModel";
    private final MutableLiveData<String> mText;
    DatabaseHelper mDatabaseHelper;

    Cursor data;

    private List<String> groupList;
    private List<String> childList;
    Map<String, List<String>> ticketCollection;

    ArrayList<String> listDataInactive = new ArrayList<>();
    ArrayList<String> listDataActive = new ArrayList<>();
    ArrayList<String> listDataUsed = new ArrayList<>();

    public MyTicketsViewModel(@NonNull Application application) {
        super(application);
        mDatabaseHelper = new DatabaseHelper(application);

        mText = new MutableLiveData<>();
        mText.setValue("This is MyTickets fragment");



        populateListView();
        createGroupList();
        createCollection();
    }

    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView");

        //get the data and append to a list
        data = mDatabaseHelper.getTickets("inactive");

        String temp;

        while(data.moveToNext()){
            //get the calue from the database in column 0 and 1
            //then add it to the ArrayList
            //temp = data.getString(data.getColumnIndex("name"));
            listDataInactive.add(data.getString(data.getColumnIndex("_id"))+"/"+data.getString(data.getColumnIndex("name")));
        }

        data = mDatabaseHelper.getTickets("active");
        while(data.moveToNext()){
            listDataActive.add(data.getString(data.getColumnIndex("_id"))+"/"+data.getString(data.getColumnIndex("name")));
        }

        data = mDatabaseHelper.getTickets("used");
        while(data.moveToNext()){
            listDataUsed.add(data.getString(data.getColumnIndex("_id"))+"/"+data.getString(data.getColumnIndex("name")));
        }
    }

    private void createCollection(){
        //String[] activeTickets = listDataActive;{"Bilet miejski ulgowy jednorazowy 30min"};
        //String[] inactiveTickets = {"Bilet miejski ulgowy jednorazowy 30min","Bilet miejski ulgowy jednorazowy 30min"};
        //String[] usedTickets = {"Bilet miejski ulgowy jednorazowy 30min","Bilet miejski ulgowy jednorazowy 30min","Bilet miejski ulgowy jednorazowy 30min"};
        ticketCollection = new HashMap<String, List<String>>();
        for(String group :groupList){
            if(group.equals("ACTIVE")){
                //loadChild(listDataActive);
                childList = listDataActive;
                //Log.d(TAG, listDataInactive.get(0).toString());
            }else if(group.equals("INACTIVE")){
                //loadChild(inactiveTickets);
                childList = listDataInactive;
                //Log.d(TAG, listDataActive.get(0).toString());
            }else if(group.equals("USED")){
                //loadChild(usedTickets);
                childList = listDataUsed;
                //Log.d(TAG, listDataUsed.get(0).toString());
            }
            ticketCollection.put(group, childList);
        }
    }

    private void loadChild(String[] type){
        childList = new ArrayList<>();
        for(String model : type){
            childList.add(model);
        }
    }

    private void createGroupList(){
        groupList = new ArrayList<>();
        groupList.add("ACTIVE");
        groupList.add("INACTIVE");
        groupList.add("USED");
    }


    public LiveData<String> getText() {
        return mText;
    }

    public Map<String, List<String>> getTicketCollection() { return ticketCollection; }

    public List<String> getGroupList() { return groupList; }
}
