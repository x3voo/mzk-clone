package pl.rikwo.mzkclone.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import pl.rikwo.mzkclone.DatabaseHelper;
import pl.rikwo.mzkclone.EditProfileActivity;
import pl.rikwo.mzkclone.TicketActivity;
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

        final TextView textValueName = binding.textValueName;
        final TextView textValueLastName = binding.textValueLastName;
        final TextView textValuePersonalIDNumber = binding.textValuePersonalIDNumber;
        final TextView textValueTelephoneNumber = binding.textValueTelephoneNumber;
        profileViewModel.getText("name").observe(getViewLifecycleOwner(), textValueName::setText);
        profileViewModel.getText("lastname").observe(getViewLifecycleOwner(), textValueLastName::setText);
        profileViewModel.getText("personalid").observe(getViewLifecycleOwner(), textValuePersonalIDNumber::setText);
        profileViewModel.getText("telephone").observe(getViewLifecycleOwner(), textValueTelephoneNumber::setText);

        binding.buttonEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditProfileActivity.class);
                startActivity(intent);

                /* temporary fix to not refreshing */
                getActivity().finish();
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
