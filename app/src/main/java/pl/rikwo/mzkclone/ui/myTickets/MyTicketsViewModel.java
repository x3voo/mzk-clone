package pl.rikwo.mzkclone.ui.myTickets;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyTicketsViewModel  extends ViewModel {
    private final MutableLiveData<String> mText;

    public MyTicketsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is MyTickets fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
