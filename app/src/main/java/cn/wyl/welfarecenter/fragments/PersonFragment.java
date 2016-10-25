package cn.wyl.welfarecenter.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.wyl.welfarecenter.R;
import cn.wyl.welfarecenter.WelfareCenterApplication;
import cn.wyl.welfarecenter.bean.UserAvatar;
import cn.wyl.welfarecenter.utils.ImageLoader;
import cn.wyl.welfarecenter.utils.MFGT;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonFragment extends Fragment {
    Context mContext;


    UserAvatar user;
    String username;
    @BindView(R.id.img_per_user_avatar)
    ImageView mImgPerUserAvatar;
    @BindView(R.id.tv_per_usernick)
    TextView mTvPerUsernick;
    @BindView(R.id.tv_setting)
    TextView mTvSetting;
    @BindView(R.id.img_message)
    ImageView mImgMessage;
    @BindView(R.id.user)
    LinearLayout mUser;


    public PersonFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =
                inflater.inflate(R.layout.fragment_person, container, false);
        ButterKnife.bind(this, view);
        mContext = getActivity();
        initView();
        return view;

    }

    private void initView() {
        //  String userName = SharedPreferencesUtils.getInstance(getActivity()).getUserName();
        user = WelfareCenterApplication.getUser();

        if (user == null) {

            LoginDialog();
        } else {
            mTvPerUsernick.setText(user.getMuserNick());

            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), mContext, mImgPerUserAvatar);
        }
    }

    private void LoginDialog() {
        new AlertDialog.Builder(getActivity()).setTitle("未登录").setMessage("请先登录...").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                MFGT.gotoLogin(getActivity());

            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setIcon(R.drawable.quest).create().show();
    }


    @OnClick({R.id.tv_per_usernick, R.id.tv_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_per_usernick:

                break;
            case R.id.tv_setting:
                Log.e("main", "点击用户昵称");
                if (user == null) {
                    LoginDialog();
                } else if (user != null) {

                    MFGT.goPersonInfoActivity(getActivity());
                }
                break;
            case R.id.btn_relogin:
                break;
        }

    }

}
