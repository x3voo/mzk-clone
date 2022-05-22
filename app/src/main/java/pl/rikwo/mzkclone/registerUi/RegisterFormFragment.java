package pl.rikwo.mzkclone.registerUi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import pl.rikwo.mzkclone.DatabaseHelper;
import pl.rikwo.mzkclone.R;
import pl.rikwo.mzkclone.databinding.FragmentRegisterFormBinding;

public class RegisterFormFragment extends Fragment {

    DatabaseHelper mDatabaseHelper;

    EditText inputName, inputSurname, inputPhone, inputEmail, inputEmailConfirm, inputPassword, inputPasswordConfirm, inputPersonalId;
    boolean declare = false;

    private FragmentRegisterFormBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentRegisterFormBinding.inflate(inflater, container, false);

        mDatabaseHelper = new DatabaseHelper(getActivity());



        inputName = binding.name;
        inputSurname = binding.surname;
        inputPhone = binding.phone;
        inputEmail = binding.email;
        inputEmailConfirm = binding.emailConfirm;
        inputPassword = binding.passwordNumber;
        inputPasswordConfirm = binding.passwordNumberConfirm;
        inputPersonalId = binding.personalId;

        inputName.getText();
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //inputName = (EditText) view.findViewById(R.id.name);

        inputName.getText();

        binding.checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                declare = !declare;
            }
        });

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {




            @Override
            public void onClick(View view) {
                //NavHostFragment.findNavController(RegisterFormFragment.this)
                //        .navigate(R.id.action_SecondFragment_to_FirstFragment);

                //inputName.getText();
                if(!inputName.getText().toString().equals("") && !inputSurname.getText().toString().equals("")
                    && !inputPhone.getText().toString().equals("") && !inputEmail.getText().toString().equals("")
                    && !inputEmailConfirm.getText().toString().equals("") && !inputPassword.getText().toString().equals("")
                    && !inputPasswordConfirm.getText().toString().equals("") && !inputPersonalId.getText().toString().equals("")
                    && declare != false){

                    if(mDatabaseHelper.newUser(inputName.getText().toString(),inputSurname.getText().toString(),
                            inputPhone.getText().toString(), inputEmail.getText().toString(),
                            inputPassword.getText().toString(), inputPersonalId.getText().toString())){
                        Toast.makeText(view.getContext(), "Success", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    } else {
                        Toast.makeText(view.getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(view.getContext(), "You have not completed all the required data.", Toast.LENGTH_SHORT).show();
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