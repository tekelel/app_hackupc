package com.example.marti.myapplication.Interface;

import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.marti.myapplication.Coms.MyCallback;
import com.example.marti.myapplication.Coms.SocketController;
import com.example.marti.myapplication.Coms.SocketProtocol;
import com.example.marti.myapplication.R;

import static android.content.ContentValues.TAG;

/**
 * Created by marti on 04/03/17.
 */

public class ButtonController  implements View.OnClickListener {

    private boolean status;
    private Button button;
    private TextView state;

    public ButtonController(View view){
        this.status = false;
        button = (Button) view.findViewById(R.id.button);
        state =  (TextView) view.findViewById(R.id.stateText);
        state.setText("OFF");
        button.setOnClickListener(this);

        /* init the status asnychorniously*/
        Log.v(TAG, "Sent init request.");
        SocketProtocol.send_init(new InitState());
    }

    @Override
    public void onClick(View view) {
        /* toogle */
        if(!status){
            Log.v(TAG, "Sent on request.");
            SocketProtocol.send_on(new UpdateAfterOn());
        }else{
            Log.v(TAG, "Sent off request.");
            SocketProtocol.send_off(new UpdateAfterOff());
        }
    }

    public class UpdateAfterOn implements MyCallback {

        @Override
        public void callback(boolean result) {
            state.setText("ON");
        }
    }

    public class UpdateAfterOff implements MyCallback {

        @Override
        public void callback(boolean result) {
            state.setText("OFF");
        }
    }

    public class InitState implements MyCallback{
        @Override
        public void callback(boolean result) {
            Log.v(TAG, "Updated STATE.");
            state.setText(result ? "ON": "OFF");
        }
    }
}
