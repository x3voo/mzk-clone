package pl.rikwo.mzkclone.ui.myTickets;

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

import pl.rikwo.mzkclone.CustomExandableListAdapter;
import pl.rikwo.mzkclone.databinding.FragmentMyTicketsBinding;

public class MyTicketsFragment extends Fragment {
    private FragmentMyTicketsBinding binding;

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MyTicketsViewModel myTicketsViewModel =
                new ViewModelProvider(this).get(MyTicketsViewModel.class);

        binding = FragmentMyTicketsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        expandableListView = binding.ticketsList;
        expandableListAdapter = new CustomExandableListAdapter(getActivity(), myTicketsViewModel.getGroupList(), myTicketsViewModel.getTicketCollection());
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int lastExpandedPosition = -1;
            @Override
            public void onGroupExpand(int groupPosition) {
                if(lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
                    expandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String selected = expandableListAdapter.getChild(groupPosition,childPosition).toString();
                Toast.makeText(v.getContext(), "Selected: " + selected, Toast.LENGTH_SHORT).show();
                return true;
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
