package tk.mirenamorrortu.calkulat.Listeners;

import android.view.View;
import android.widget.Button;

/**
 * Created by Bafi on 20/03/15.
 */
public class OperationOnClickListener implements View.OnClickListener{

    public interface OperationListenerInterface{
        public void setOperation (String number);

    }

    private OperationListenerInterface target;

    public OperationOnClickListener(OperationListenerInterface target){
        this.target = target;
    }
    @Override
    public void onClick(View v) {
        Button btn = (Button) v;

        this.target.setOperation (btn.getText().toString());

    }
}
