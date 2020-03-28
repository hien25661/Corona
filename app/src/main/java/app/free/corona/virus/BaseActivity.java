package app.free.corona.virus;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.bumptech.glide.Glide;

import app.free.corona.virus.utils.EventBusUtils;


/**
 * Created by nguyenvanhien on 6/27/17.
 */

public class BaseActivity extends AppCompatActivity {
    protected EventBusUtils.Holder eventBus = EventBusUtils.getDefault();
    @Override
    protected void onResume() {
        super.onResume();
        EventBusUtils.register(eventBus, this);
    }

    @Override
    protected void onStop() {
        recycleActivityView();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        recycleActivityView();
        super.onDestroy();
        EventBusUtils.unregister(eventBus, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBusUtils.unregister(eventBus, this);

    }

    public void recycleActivityView(){
        try{
            Glide.get(this).getBitmapPool().clearMemory();
            Glide.get(this).clearDiskCache();
            Glide.get(this).clearMemory();
        }catch (Exception ex){}
    }

    public void setUpToolBar(AppCompatActivity activity, Toolbar toolbar) {
        if (activity != null) {
            if (toolbar != null) {
                activity.setSupportActionBar(toolbar);
                ActionBar mActionBar = activity.getSupportActionBar();
                if (mActionBar != null) {
                    mActionBar.setDisplayHomeAsUpEnabled(true);
                    mActionBar.setTitle("");
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
