package pl.rikwo.mzkclone.ui.ticketControl;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import pl.rikwo.mzkclone.databinding.FragmentTicketControlBinding;

public class TicketControlFragment extends Fragment {
    private FragmentTicketControlBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TicketControlViewModel ticketControlViewModel =
                new ViewModelProvider(this).get(TicketControlViewModel.class);

        binding = FragmentTicketControlBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textTicketControl;
        ticketControlViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}