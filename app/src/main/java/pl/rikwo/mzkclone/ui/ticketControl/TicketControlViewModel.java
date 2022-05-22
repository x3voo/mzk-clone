package pl.rikwo.mzkclone.ui.ticketControl;

import android.app.Application;
import android.database.Cursor;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.rikwo.mzkclone.DatabaseHelper;

public class TicketControlViewModel extends AndroidViewModel {
    private static final String TAG = "TicketControlViewModel";
    private final MutableLiveData<String> mText;
    DatabaseHelper mDatabaseHelper;

    Cursor data;

    private List<String> groupList;
    private List<String> childList;
    Map<String, List<String>> ticketCollection;

    ArrayList<String> listDataActive = new ArrayList<>();

    public TicketControlViewModel(@NonNull Application application) {
        super(application);
        mDatabaseHelper = new DatabaseHelper(application);

        mText = new MutableLiveData<>();
        mText.setValue("This is TicketControl fragment");

        populateListView();
        createGroupList();
        createCollection();
    }

    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView");

        data = mDatabaseHelper.getTickets("active");
        while(data.moveToNext()){
            listDataActive.add(data.getString(data.getColumnIndex("_id"))+"/"+data.getString(data.getColumnIndex("name")));
        }
    }

    private void createCollection(){
        ticketCollection = new HashMap<String, List<String>>();
        for(String group :groupList){
            if(group.equals("ACTIVE")){
                //loadChild(listDataActive);
                childList = listDataActive;
                //Log.d(TAG, listDataInactive.get(0).toString());
            }
            ticketCollection.put(group, childList);
        }
    }

    private void createGroupList(){
        groupList = new ArrayList<>();
        groupList.add("ACTIVE");
    }


    public LiveData<String> getText() {
        return mText;
    }

    public Map<String, List<String>> getTicketCollection() { return ticketCollection; }

    public List<String> getGroupList() { return groupList; }
}
