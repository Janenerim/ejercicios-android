package tk.mirenamorrortu.todolistfragments;

import android.app.Fragment;
import android.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import tk.mirenamorrortu.todolistfragments.Fragments.InputFragment;
import tk.mirenamorrortu.todolistfragments.Model.ToDo;


public class MainActivity extends ActionBarActivity implements InputFragment.TODOItemListener{

    private final String TODO = "TODO";

    private InputFragment.TODOItemListener listFragment;

    @Override
    public void addTodo (ToDo todo){
        listFragment.addTodo(todo);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            listFragment = (InputFragment.TODOItemListener) getFragmentManager().findFragmentById(R.id.listFragment);
        } catch (ClassCastException ex){
            throw new ClassCastException(this.toString() + " must implement TODOItemListener interface");
        }
    }
}
