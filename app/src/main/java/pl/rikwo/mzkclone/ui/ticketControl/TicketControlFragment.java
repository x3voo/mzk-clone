package pl.rikwo.mzkclone.ui.ticketControl;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import pl.rikwo.mzkclone.CustomExpandableListAdapter;
import pl.rikwo.mzkclone.TicketActivity;
import pl.rikwo.mzkclone.databinding.FragmentTicketControlBinding;

public class TicketControlFragment extends Fragment {
    private FragmentTicketControlBinding binding;

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TicketControlViewModel ticketControlViewModel =
                new ViewModelProvider(this).get(TicketControlViewModel.class);

        binding = FragmentTicketControlBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        expandableListView = binding.ticketsList;
        expandableListAdapter = new CustomExpandableListAdapter(getActivity(), ticketControlViewModel.getGroupList(), ticketControlViewModel.getTicketCollection());
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

                String[] separated = selected.split("/");
                if(groupPosition == 0){
                    //Toast.makeText(v.getContext(), "Active - id" + separated[0], Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(v.getContext(), TicketActivity.class);
                    intent.putExtra("id", separated[0]);
                    startActivity(intent);
                    /* temporary fix to not refreshing */
                    getActivity().finish();

                }

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