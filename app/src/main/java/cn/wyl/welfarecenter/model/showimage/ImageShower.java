package cn.wyl.welfarecenter.model.showimage;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.wyl.welfarecenter.R;
import cn.wyl.welfarecenter.WelfareCenterApplication;
import cn.wyl.welfarecenter.bean.UserAvatar;
import cn.wyl.welfarecenter.model.utils.ImageLoader;


public class ImageShower extends AppCompatActivity {

    @BindView(R.id.avatar_show)
    ImageView mAvatarShow;
    UserAvatar user;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageshower);
        ButterKnife.bind(this);
        user = WelfareCenterApplication.getUser();
        if (user!=null) {
            mContext=this;
            final ImageLoadingDialog dialog = new ImageLoadingDialog(this);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user),mContext,mAvatarShow);
                    dialog.dismiss();
                }
            }, 1000 * 2);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        finish();
        return true;
    }

}
