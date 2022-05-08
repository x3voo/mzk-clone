package pl.rikwo.mzkclone.ui.buyTicket;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BuyTicketViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public BuyTicketViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is BuyTicket fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
