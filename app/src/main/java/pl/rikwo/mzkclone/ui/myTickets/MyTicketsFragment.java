package pl.rikwo.mzkclone.ui.myTickets;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import pl.rikwo.mzkclone.CustomExandableListAdapter;
import pl.rikwo.mzkclone.DatabaseHelper;
import pl.rikwo.mzkclone.HomeActivity;
import pl.rikwo.mzkclone.LoginActivity;
import pl.rikwo.mzkclone.MainActivity;
import pl.rikwo.mzkclone.TicketActivity;
import pl.rikwo.mzkclone.databinding.FragmentMyTicketsBinding;

public class MyTicketsFragment extends Fragment {
    private FragmentMyTicketsBinding binding;

    DatabaseHelper mDatabaseHelper;

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;

    private String activationData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MyTicketsViewModel myTicketsViewModel =
                new ViewModelProvider(this).get(MyTicketsViewModel.class);

        binding = FragmentMyTicketsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mDatabaseHelper = new DatabaseHelper(getActivity());

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
                String[] separated = selected.split("/");
                if(groupPosition == 0){
                    //Toast.makeText(v.getContext(), "Active - id" + separated[0], Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(v.getContext(), TicketActivity.class);
                    intent.putExtra("id", separated[0]);
                    startActivity(intent);
                    /* temporary fix to not refreshing */
                    getActivity().finish();

                }else if(groupPosition == 1){
                    //Toast.makeText(v.getContext(), "Inactive - id" + separated[0], Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Activate ticket");

                    // Set up the input
                    final EditText input = new EditText(v.getContext());
                    // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    input.setInputType(InputType.TYPE_CLASS_NUMBER);

                    //Resources resources = getResources();
                    //int res = getResources().getIdentifier("@color/colorPrimary", "color", getActivity().getPackageName());
                    //input.setBackground(resources.getDrawable(res));
                    //input.setBackgroundTintBlendMode(resources.getColorStateList(res));
                    builder.setView(input);

                    // Set up the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            activationData = input.getText().toString();
                            if(mDatabaseHelper.activateTicket(separated[0],activationData)){
                                //expandableListAdapter.;
                                ((CustomExandableListAdapter)expandableListAdapter).notifyDataSetChanged();
                                //expandableListView.invalidateViews();
                                Toast.makeText(v.getContext(), "Ticket " + separated[0] + " set to " + activationData, Toast.LENGTH_SHORT).show();

                                /* temporary fix to not refreshing */
                                reload();

                            }else{
                                Toast.makeText(v.getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();



                }else if(groupPosition == 2){
                    //Toast.makeText(v.getContext(), "Used - id" + separated[0], Toast.LENGTH_SHORT).show();
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

    public void reload() {
        Intent intent = getActivity().getIntent();
        getActivity().overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        getActivity().finish();
        getActivity().overridePendingTransition(0, 0);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
