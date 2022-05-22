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
    private final MutableLiveData<String> mLastName;
    private final MutableLiveData<String> mPersonalId;
    private final MutableLiveData<String> mTelephone;

    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView");

        //get the data and append to a list
        Cursor data = mDatabaseHelper.getUserData();
        while(data.moveToNext()){
            //get the calue from the database in column 0 and 1
            //then add it to the ArrayList
            listData.add(data.getString(data.getColumnIndex("name")));
            listData.add(data.getString(data.getColumnIndex("lastName")));
            listData.add(data.getString(data.getColumnIndex("personalId")));
            listData.add(data.getString(data.getColumnIndex("telephone")));
        }
    }

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        mDatabaseHelper = new DatabaseHelper(application);
        populateListView();

        mText = new MutableLiveData<>();
        mText.setValue(listData.get(0));

        mName = new MutableLiveData<>();
        mName.setValue(listData.get(0));

        mLastName = new MutableLiveData<>();
        mLastName.setValue(listData.get(1));

        mPersonalId = new MutableLiveData<>();
        mPersonalId.setValue(listData.get(2));

        mTelephone = new MutableLiveData<>();
        mTelephone.setValue(listData.get(3));
    }

    public LiveData<String> getText(String data) {
        LiveData<String> r = mText;
        if(data == "name"){
            r = mName;
        } else if(data == "text") {
            r = mText;
        } else if(data == "lastname") {
            r = mLastName;
        } else if(data == "personalid") {
            r = mPersonalId;
        } else if(data == "telephone") {
            r = mTelephone;
        }
        return r;
    }
}
