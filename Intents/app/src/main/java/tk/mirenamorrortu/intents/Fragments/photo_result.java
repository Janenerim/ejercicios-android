package tk.mirenamorrortu.intents.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tk.mirenamorrortu.intents.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class photo_result extends Fragment {


    public photo_result() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_photo_result, container, false);
        if (savedInstanceState != null){

        }
        return layout;
    }


}
