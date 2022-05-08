package pl.rikwo.mzkclone.ui.changePin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ChangePinViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public ChangePinViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is ChangePin fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
