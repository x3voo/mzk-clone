package pl.rikwo.mzkclone.ui.changePin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import pl.rikwo.mzkclone.databinding.FragmentChangePinBinding;

public class ChangePinFragment extends Fragment {
    private FragmentChangePinBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ChangePinViewModel changePinViewModel =
                new ViewModelProvider(this).get(ChangePinViewModel.class);

        binding = FragmentChangePinBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textChangePin;
        changePinViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
