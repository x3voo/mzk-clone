package pl.rikwo.mzkclone.registerUi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import pl.rikwo.mzkclone.R;
import pl.rikwo.mzkclone.databinding.FragmentRegisterInfoBinding;

public class RegisterInfoFragment extends Fragment {

    private FragmentRegisterInfoBinding binding;

    private boolean accept = false;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentRegisterInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.checkAcceptRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accept = !accept;
            }
        });

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(accept) {
                    NavHostFragment.findNavController(RegisterInfoFragment.this)
                            .navigate(R.id.action_FirstFragment_to_SecondFragment);
                } else {
                    Toast.makeText(view.getContext(), "To continue, you must accept the rules.", Toast.LENGTH_SHORT).show();
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