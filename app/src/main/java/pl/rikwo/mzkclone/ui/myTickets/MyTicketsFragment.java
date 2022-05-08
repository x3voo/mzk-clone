package pl.rikwo.mzkclone.ui.myTickets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import pl.rikwo.mzkclone.databinding.FragmentMyTicketsBinding;

public class MyTicketsFragment extends Fragment {
    private FragmentMyTicketsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MyTicketsViewModel myTicketsViewModel =
                new ViewModelProvider(this).get(MyTicketsViewModel.class);

        binding = FragmentMyTicketsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textMyTickets;
        myTicketsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
