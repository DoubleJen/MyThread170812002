package tw.adouble.app.helloworld.mythread170812002;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private TextView mesg;
    private UIHandler handler;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new UIHandler();
        timer = new Timer();
        mesg = (TextView)findViewById(R.id.mesg);

    }
    public void test1(View view){
        MyThread01 mt1 = new MyThread01();
        mt1.start();
        MyThread01 mt2 = new MyThread01();
        mt2.start();
    }
    public void test2(View view){
        timer.schedule(new MyTask(), 1000, 5000);
    }
    public void test3(View view){

    }

    private class MyThread01 extends Thread{
        @Override
        public void run() {
            for(int i=0; i<20; i++){
                Log.i("brad", "i = " + i);
                //mesg.setText(" i = " + i);//會閃退
                handler.sendEmptyMessage(i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    //加上finish 程式一結束console一併結束
    @Override
    public void finish() {
        if(timer != null){
            timer.cancel();
            timer.purge();
            timer = null;

        }
        super.finish();
    }

    private class MyTask extends TimerTask{
        private int i;
        @Override
        public void run() {
            i++;
            Log.i("brad", " i = " + i);
            handler.sendEmptyMessage(i);
        }
    }

    private class UIHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mesg.setText("i = " + msg.what);//what值即為執行序中的i
        }
    }
}
