package pl.rikwo.mzkclone.ui.changePin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import pl.rikwo.mzkclone.DatabaseHelper;
import pl.rikwo.mzkclone.databinding.FragmentChangePinBinding;

public class ChangePinFragment extends Fragment {
    private FragmentChangePinBinding binding;

    DatabaseHelper mDatabaseHelper;
    EditText pin;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ChangePinViewModel changePinViewModel =
                new ViewModelProvider(this).get(ChangePinViewModel.class);

        mDatabaseHelper = new DatabaseHelper(getActivity());

        binding = FragmentChangePinBinding.inflate(inflater, container, false);

        pin = binding.editTextPin;

        binding.buttonEditPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!pin.getText().toString().equals("")){
                    if(mDatabaseHelper.changePin(pin.getText().toString())){
                        Toast.makeText(v.getContext(), "Your PIN has been changed successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(v.getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(v.getContext(), "You have not completed all the required data.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
