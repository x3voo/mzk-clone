package pl.rikwo.mzkclone;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import pl.rikwo.mzkclone.databinding.FragmentPinBinding;

public class PinFragment extends Fragment {

    private FragmentPinBinding binding;

    EditText pin;

    DatabaseHelper mDatabaseHelper;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentPinBinding.inflate(inflater, container, false);

        mDatabaseHelper = new DatabaseHelper(getActivity());

        pin = binding.editTextNumberPassword;

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //NavHostFragment.findNavController(PinFragment.this)
                //        .navigate(R.id.action_RegisterFragment_to_LoginFragment);
                if(mDatabaseHelper.login(pin.getText().toString())){
                    Intent intent = new Intent(view.getContext(), HomeActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                } else {
                    Toast.makeText(view.getContext(), "Invalid PIN", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}