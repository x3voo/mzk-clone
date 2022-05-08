package pl.rikwo.mzkclone.ui.profile;

import android.app.Application;
import android.database.Cursor;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import pl.rikwo.mzkclone.DatabaseHelper;

/*
https://stackoverflow.com/questions/68950777/list-is-showing-incorrect-entry-using-mvvm-and-sqlite

 */

public class ProfileViewModel extends AndroidViewModel {
    private static final String TAG = "ListDataActivity";
    DatabaseHelper mDatabaseHelper;

    ArrayList<String> listData = new ArrayList<>();

    private final MutableLiveData<String> mText;
    private final MutableLiveData<String> mName;

    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView");

        //get the data and append to a list
        Cursor data = mDatabaseHelper.getData();
        while(data.moveToNext()){
            //get the calue from the database in column 0 and 1
            //then add it to the ArrayList
            listData.add(data.getString(0));
            listData.add(data.getString(1));
        }
    }

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        mDatabaseHelper = new DatabaseHelper(application);
        populateListView();

        mText = new MutableLiveData<>();
        mText.setValue(listData.get(0));

        mName = new MutableLiveData<>();
        mName.setValue(listData.get(1));
    }

    public LiveData<String> getText(String data) {
        LiveData<String> r = mText;
        if(data == "name"){
            r = mName;
        } else if(data == "text") {
            r = mText;
        }
        return r;
    }
}
