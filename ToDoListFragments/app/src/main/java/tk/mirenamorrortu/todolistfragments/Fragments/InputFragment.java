package tk.mirenamorrortu.todolistfragments.Fragments;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import tk.mirenamorrortu.todolistfragments.Model.ToDo;
import tk.mirenamorrortu.todolistfragments.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InputFragment extends Fragment {

    public interface TODOItemListener{
        public void addTodo(ToDo todo);
    }

    private Button btnAdd;
    private EditText todoText;

    private TODOItemListener target;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            this.target = (TODOItemListener) activity;
        }catch (ClassCastException ex){
            throw new ClassCastException(activity.toString() + " must implement TODOItemListener interface");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout =  inflater.inflate(R.layout.fragment_input, container, false);

        btnAdd = (Button) layout.findViewById(R.id.btnAdd);
        todoText = (EditText) layout.findViewById(R.id.inputText);

        addEventListeners();

        return layout;
    }

    private void addEventListeners(){
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToDo todo = new ToDo(todoText.getText().toString());
                target.addTodo(todo);
                todoText.setText("");
            }
        });
    }
}
