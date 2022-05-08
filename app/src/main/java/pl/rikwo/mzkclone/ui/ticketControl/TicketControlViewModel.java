package pl.rikwo.mzkclone.ui.ticketControl;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TicketControlViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public TicketControlViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is TicketControl fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
