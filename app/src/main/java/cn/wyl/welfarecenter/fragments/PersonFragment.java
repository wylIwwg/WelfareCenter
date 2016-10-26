package cn.wyl.welfarecenter.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import cn.wyl.welfarecenter.activity.CollectsActivity;
import cn.wyl.welfarecenter.bean.MessageBean;
import cn.wyl.welfarecenter.bean.UserAvatar;
import cn.wyl.welfarecenter.net.NetDao;
import cn.wyl.welfarecenter.showimage.ImageShower;
import cn.wyl.welfarecenter.utils.ImageLoader;
import cn.wyl.welfarecenter.utils.MFGT;
import cn.wyl.welfarecenter.utils.OkHttpUtils;

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
    @BindView(R.id.tv_collect_goods)
    TextView mTvCollectGoods;
    @BindView(R.id.collect_goods)
    LinearLayout mCollectGoods;
    @BindView(R.id.tv_collect_shops)
    TextView mTvCollectShops;
    @BindView(R.id.collect_shops)
    LinearLayout mCollectShops;


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
        initData();

        return view;

    }

    private void initData() {
        user = WelfareCenterApplication.getUser();

        if (user == null) {

            LoginDialog();
        } else {
            //设置收藏宝贝的数目
            setCollectGoodsCount();

            mTvPerUsernick.setText(user.getMuserNick());
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), mContext, mImgPerUserAvatar);
        }
    }

    private void setCollectGoodsCount() {
        NetDao.findCollectCount(mContext, user.getMuserName(), new OkHttpUtils.OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if (result.isSuccess()) {
                    String count = result.getMsg();
                    mTvCollectGoods.setText(count);
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void initView() {
        //  String userName = SharedPreferencesUtils.getInstance(getActivity()).getUserName();

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


    @OnClick({R.id.tv_per_usernick, R.id.tv_setting, R.id.collect_goods, R.id.collect_shops,R.id.img_per_user_avatar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_per_usernick:
                Log.e("main", "点击用户昵称");
                gotoPersonInfo();
                break;
            case R.id.tv_setting:
                Log.e("main", "点击设置");
                gotoPersonInfo();
                break;
            case R.id.btn_relogin:
                break;
            case R.id.collect_goods:
                MFGT.startActivity(getActivity(), CollectsActivity.class);
                break;
            case R.id.collect_shops:
                break;
            case R.id.img_per_user_avatar:

               startActivity(new Intent(getActivity(), ImageShower.class));
                break;
        }

    }


    /**
     * 跳转到个人资料界面
     */
    private void gotoPersonInfo() {
        if (user == null) {
            LoginDialog();
        } else if (user != null) {

            MFGT.goPersonInfoActivity(getActivity());
        }
    }

}
