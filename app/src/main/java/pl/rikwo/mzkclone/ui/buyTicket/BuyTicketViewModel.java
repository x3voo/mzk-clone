package pl.rikwo.mzkclone.ui.buyTicket;

import android.widget.ExpandableListView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuyTicketViewModel extends ViewModel {
    private final MutableLiveData<String> mText;



    public BuyTicketViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is BuyTicket fragment");

    }



    public LiveData<String> getText() {
        return mText;
    }

    //public
}
