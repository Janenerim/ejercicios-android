package tk.mirenamorrortu.intents.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import tk.mirenamorrortu.intents.MainActivity;
import tk.mirenamorrortu.intents.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class photo_intent extends Fragment {

    public interface PHOTOImgListener{
        public void addphoto();
    }


    Button btn_photoint;
    Intent photo_intent;

    public photo_intent() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_photo_intent, container, false);
        btn_photoint = (Button) layout.findViewById(R.id.btn_photo);
        btn_photoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Hacer intent a la cámara y si recibe respuesta pasarla a photoresult

            }
        });
        return layout;
    }

    private	void startSubActivityImplicitly() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, MainActivity.ASK_CAMERA);
    }

    
}
