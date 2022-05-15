package pl.rikwo.mzkclone.ui.buyTicket;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import pl.rikwo.mzkclone.databinding.FragmentBuyTicketBinding;

public class BuyTicketFragment extends Fragment {
    private FragmentBuyTicketBinding binding;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BuyTicketViewModel buyTicketViewModel =
                new ViewModelProvider(this).get(BuyTicketViewModel.class);

        binding = FragmentBuyTicketBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textBuyTicket;
        buyTicketViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}