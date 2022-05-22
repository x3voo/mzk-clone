package pl.rikwo.mzkclone.ui.buyTicket;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import pl.rikwo.mzkclone.DatabaseHelper;
import pl.rikwo.mzkclone.databinding.FragmentBuyTicketBinding;

public class BuyTicketFragment extends Fragment {
    private FragmentBuyTicketBinding binding;

    DatabaseHelper mDatabaseHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BuyTicketViewModel buyTicketViewModel =
                new ViewModelProvider(this).get(BuyTicketViewModel.class);

        binding = FragmentBuyTicketBinding.inflate(inflater, container, false);

        mDatabaseHelper = new DatabaseHelper(getActivity());

        View root = binding.getRoot();

        //final TextView textView = binding.textBuyTicket;

        //buyTicketViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        binding.buyRegular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDatabaseHelper.addTicket("regular")){
                    Toast.makeText(v.getContext(), "Added 1 regular ticket", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(v.getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            }
        });

        binding.buyDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDatabaseHelper.addTicket("discount")){
                    Toast.makeText(v.getContext(), "Added 1 discount ticket", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(v.getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }

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