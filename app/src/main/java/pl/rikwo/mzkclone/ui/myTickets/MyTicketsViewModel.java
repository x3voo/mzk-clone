package pl.rikwo.mzkclone.ui.myTickets;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyTicketsViewModel  extends ViewModel {
    private final MutableLiveData<String> mText;

    private List<String> groupList;
    private List<String> childList;
    Map<String, List<String>> ticketCollection;

    public MyTicketsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is MyTickets fragment");

        createGroupList();
        createCollection();
    }

    private void createCollection(){
        String[] activeTickets = {"Bilet miejski ulgowy jednorazowy 30min"};
        String[] inactiveTickets = {"Bilet miejski ulgowy jednorazowy 30min","Bilet miejski ulgowy jednorazowy 30min"};
        String[] usedTickets = {"Bilet miejski ulgowy jednorazowy 30min","Bilet miejski ulgowy jednorazowy 30min","Bilet miejski ulgowy jednorazowy 30min"};
        ticketCollection = new HashMap<String, List<String>>();
        for(String group :groupList){
            if(group.equals("ACTIVE")){
                loadChild(activeTickets);
            }else if(group.equals("INACTIVE")){
                loadChild(inactiveTickets);
            }else if(group.equals("USED")){
                loadChild(usedTickets);
            }
            ticketCollection.put(group, childList);
        }
    }

    private void loadChild(String[] type){
        childList = new ArrayList<>();
        for(String model : type){
            childList.add(model);
        }
    }

    private void createGroupList(){
        groupList = new ArrayList<>();
        groupList.add("ACTIVE");
        groupList.add("INACTIVE");
        groupList.add("USED");
    }


    public LiveData<String> getText() {
        return mText;
    }

    public Map<String, List<String>> getTicketCollection() { return ticketCollection; }

    public List<String> getGroupList() { return groupList; }
}
