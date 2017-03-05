package com.example.marti.myapplication.Interface;

import android.content.DialogInterface;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.marti.myapplication.Coms.MyCallback;
import com.example.marti.myapplication.Coms.SocketController;
import com.example.marti.myapplication.Coms.SocketProtocol;
import com.example.marti.myapplication.Coms.SocketProtocolOperation;
import com.example.marti.myapplication.R;

import static android.content.ContentValues.TAG;

/**
 * Created by marti on 04/03/17.
 */

public class ButtonController  implements View.OnClickListener {

    private boolean status;
    private ImageButton button;

    public ButtonController(View view){
        this.status = false;
        button = (ImageButton) view.findViewById(R.id.button);
        button.setColorFilter(ContextCompat.getColor(button.getContext(), R.color.button_off), PorterDuff.Mode.MULTIPLY);
        button.setOnClickListener(this);

        /* init the status asnychorniously*/
        Log.v(TAG, "Sent init request.");
        SocketProtocolOperation initOp = new SocketProtocolOperation(new InitState(),SocketProtocolOperation.INIT, null);
        initOp.execute("");
    }

    @Override
    public void onClick(View view) {
        /* toogle */
        if(!status){
            Log.v(TAG, "Sent on request.");
            SocketProtocolOperation initOp = new SocketProtocolOperation(new UpdateAfterOn(),SocketProtocolOperation.SEND_ON, null);
            initOp.execute("");
        }else{
            Log.v(TAG, "Sent off request.");
            SocketProtocolOperation initOp = new SocketProtocolOperation(new UpdateAfterOff(),SocketProtocolOperation.SEND_OFF, null);
            initOp.execute("");
        }
    }

    public class UpdateAfterOn implements MyCallback {

        @Override
        public void callback(boolean result) {
            status = true;
            Log.v(TAG, "ON SET SUCCESSFULLY.");
            button.setColorFilter(ContextCompat.getColor(button.getContext(), R.color.button_on), PorterDuff.Mode.MULTIPLY);
        }
    }

    public class UpdateAfterOff implements MyCallback {

        @Override
        public void callback(boolean result) {
            status = false;
            Log.v(TAG, "OFF SET SUCCESSFULLY.");
            button.setColorFilter(ContextCompat.getColor(button.getContext(), R.color.button_off), PorterDuff.Mode.MULTIPLY);
        }
    }

    public class InitState implements MyCallback{
        @Override
        public void callback(boolean result) {
            Log.v(TAG, "Updated STATE.");
            if(result){
                button.setColorFilter(ContextCompat.getColor(button.getContext(), R.color.button_on), PorterDuff.Mode.MULTIPLY);
            }
            else{
                button.setColorFilter(ContextCompat.getColor(button.getContext(), R.color.button_off), PorterDuff.Mode.MULTIPLY);
            }
            status = result;
        }
    }
}
