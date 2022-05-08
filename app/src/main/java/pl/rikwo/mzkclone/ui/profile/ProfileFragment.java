package pl.rikwo.mzkclone.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import pl.rikwo.mzkclone.DatabaseHelper;
import pl.rikwo.mzkclone.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private ProfileViewModel profileViewModel;
    //private ProfileViewModel.Factory profileViewModelFactory;

    //DatabaseHelper mDatabaseHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //mDatabaseHelper = new DatabaseHelper(getActivity());

        //profileViewModelFactory = new ViewModelProvider.Factory(requireActivity().getApplication());
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);


        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textProfile = binding.textProfile;
        final TextView textValueName = binding.textValueName;
        final TextView textValueLastName = binding.textValueLastName;
        final TextView textValuePersonalIDNumber = binding.textValuePersonalIDNumber;
        final TextView textValueTelephoneNumber = binding.textValueTelephoneNumber;
        profileViewModel.getText("text").observe(getViewLifecycleOwner(), textProfile::setText);
        profileViewModel.getText("name").observe(getViewLifecycleOwner(), textValueName::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
