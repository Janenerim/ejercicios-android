package tk.mirenamorrortu.calkulat.Listeners;

import android.view.View;
import android.widget.Button;

import tk.mirenamorrortu.calkulat.MainActivity;

/**
 * Created by Bafi on 20/03/15.
 */
public class NumberOnClickListener implements View.OnClickListener{

    public interface NumberListenerInterface{
        public void setNumber (String number);

    }

    private NumberListenerInterface target;

    public NumberOnClickListener(NumberListenerInterface target){
        this.target = target;
    }
    @Override
    public void onClick(View v) {
        Button btn = (Button) v;

        this.target.setNumber (btn.getText().toString());

    }
}
