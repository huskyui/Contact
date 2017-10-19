package com.example.android.contact;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by husky on 17-10-18.
 */
//所有的activity都BaseActivity，而baseActivity继承AppCompatActivity,建立一个List<Activity>，
// 当activity是onCreate时，add，在onDestroy时，remove
//而这样也就实现了可以finishAll，将所有没有activity的给finish()掉
public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    public static void finishAll(){
        for(Activity activity:activities){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
    }

}
