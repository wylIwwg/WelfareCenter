package cn.wyl.welfarecenter.views;

import android.app.Activity;


/**
 * Created by clawpo on 16/8/3.
 */
public class DisplayUtils {
    public static void initBack(final Activity activity){
       /* activity.findViewById(R.id.backClickArea).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });*/
    }

    public static void initBackWithTitle(final Activity activity, final String title){
        initBack(activity);
       // ((TextView)activity.findViewById(R.id.tv_common_title)).setText(title);
    }
}
