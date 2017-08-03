package com.example.hien.servicebindmedia;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements RcAdapter.IAdapter {

    private RecyclerView rcView;
    private RcAdapter mAdapter;
    private ServiceConnection mConnect;
    private ServiceBindMedia mService;
    private Intent intentConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewByIds();
        initComponents();
        setEvents();
    }


    private void findViewByIds() {
        rcView = (RecyclerView) findViewById(R.id.rc_view);
    }

    private void initComponents() {

        mAdapter = new RcAdapter(this, this);

        LinearLayoutManager manager = new LinearLayoutManager(this);

        manager.setOrientation(LinearLayoutManager.VERTICAL);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate_view);

        DividerItemDecoration divider = new DividerItemDecoration(this, manager.getOrientation());
        Drawable drawable = getResources().getDrawable(R.drawable.divider_rc);

        divider.setDrawable(drawable);

        rcView.addItemDecoration(divider);
        rcView.setAdapter(mAdapter);
        rcView.setLayoutManager(manager);
        rcView.startAnimation(animation);

        connectServiceBind();

    }

    //request connect service
    private void connectServiceBind(){
        mConnect = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                ServiceBindMedia.MyService myService = (ServiceBindMedia.MyService) iBinder;

                mService = myService.getService();

                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        };

        intentConnect = new Intent(MainActivity.this, ServiceBindMedia.class);

        bindService(intentConnect, mConnect, Context.BIND_AUTO_CREATE);
    }

    private void setEvents() {


    }


    @Override
    public int getSize() {
        if(mService == null){
            return 0;
        }

        return mService.getSizeList();
    }

    @Override
    public ItemMusic getItem(int position) {
        if(mService == null){
            return null;
        }

        return mService.getItemList(position);

    }

    @Override
    public void clickItem(int position) {
        try {
            mService.playSong(position);
        } catch (IOException e) {

            showMess("Không thể play!!!");
            e.printStackTrace();
        }
    }

    private void showMess(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        unbindService(mConnect);
        if(mService != null){
            stopService(intentConnect);
        }
        super.onDestroy();
    }
}
