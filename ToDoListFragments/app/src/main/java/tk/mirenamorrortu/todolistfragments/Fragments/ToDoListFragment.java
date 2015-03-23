package tk.mirenamorrortu.todolistfragments.Fragments;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import tk.mirenamorrortu.todolistfragments.Adapters.ToDoAdapter;
import tk.mirenamorrortu.todolistfragments.DetailActivity;
import tk.mirenamorrortu.todolistfragments.Model.ToDo;
import tk.mirenamorrortu.todolistfragments.R;

/**
 * A fragment representing a list of Items.
 */
public class ToDoListFragment extends ListFragment implements InputFragment.TODOItemListener{

    private final String TODOS_KEY = "TODOS";
    private ArrayList<ToDo> todos;
    public static final String TODO_ITEM = "TODO_ITEM";

    private ToDoAdapter aa;

    private void restore_state(Bundle savedInstanceState){
        ArrayList<ToDo> tmp = savedInstanceState.getParcelableArrayList(TODOS_KEY);
        this.todos.addAll(tmp);
    }

    private void save_state(Bundle savedInstanceState){
        savedInstanceState.putParcelableArrayList(TODOS_KEY, this.todos);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        this.save_state(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        ToDo todo = todos.get(position);

        /*
        //Si quisieramos eliminar el item al pulsar sobre el...
        todos.remove(position);
        aa.notifyDataSetChanged();*/

        Intent detailIntent = new Intent(getActivity(), DetailActivity.class);
        detailIntent.putExtra(TODO_ITEM, todo);

        startActivity(detailIntent);

    }

/*    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        this.restore_state(savedInstanceState);
        super.onViewStateRestored(savedInstanceState);
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = super.onCreateView(inflater, container, savedInstanceState);
        todos = new ArrayList<ToDo>();
        if (savedInstanceState != null){
            restore_state(savedInstanceState);
        }

        aa = new ToDoAdapter(getActivity(), R.layout.todo_list_item, todos);

        setListAdapter(aa);

        return layout;
    }

    @Override
    public void addTodo(ToDo todo) {
        todos.add(0, todo);
        aa.notifyDataSetChanged();
    }
}
