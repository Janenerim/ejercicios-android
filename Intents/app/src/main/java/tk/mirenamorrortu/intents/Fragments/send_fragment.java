package tk.mirenamorrortu.intents.Fragments;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import tk.mirenamorrortu.intents.MainActivity;
import tk.mirenamorrortu.intents.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class send_fragment extends Fragment {

    public interface SendDataListener{
        public void setData(String data);
    }

    Button btn_send;
    TextView txtV_received;
    TextView txtV_send;

    View layout;

    private SendDataListener target;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            this.target = (SendDataListener) activity;
        }
        catch (ClassCastException ex){
            //
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.fragment_send_fragment, container, false);
        get_objects();
        AddEventListeners();
        if (savedInstanceState!= null){
            txtV_received.setText(savedInstanceState.getString(MainActivity.RECEIVED_KEY));
        }
        return layout;
    }

    private void get_objects() {
        btn_send = (Button) layout.findViewById(R.id.btn_send);
        txtV_received = (TextView) layout.findViewById(R.id.txtV_receivedtxt);
        txtV_send = (TextView) layout.findViewById(R.id.txtV_sendtxt);
    }

    private void AddEventListeners(){
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


}
