package cn.wyl.welfarecenter.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.wyl.welfarecenter.R;
import cn.wyl.welfarecenter.WelfareCenterApplication;
import cn.wyl.welfarecenter.bean.UserAvatar;
import cn.wyl.welfarecenter.utils.MFGT;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonFragment extends Fragment {
    Context mContext;

    @BindView(R.id.tv_per_username)
    TextView mTvPerUsername;

    UserAvatar user;
    String username;

    public PersonFragment() {

    }

    /* public static PersonFragment newInstance(String name) {
         PersonFragment fragment = new PersonFragment();
         Bundle args = new Bundle();
         // args.putSerializable("user", user);
         args.putString("username", name);
         fragment.setArguments(args);
         return fragment;
     }


     @Override
     public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         if (getArguments() != null) {
             //  user = (UserAvatar) getArguments().getSerializable("user");
             username = getArguments().getString("username");
         }
     }
 */
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
        UserAvatar user = WelfareCenterApplication.getUser();

        if (user == null) {

            new AlertDialog.Builder(getActivity()).setTitle("未登录").setMessage("请先登录...").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Intent intent = new Intent(getActivity(), LoginActivity.class);
                    //MFGT.startActivity(getActivity(),intent);
                    MFGT.gotoLogin(getActivity());

                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).setIcon(R.drawable.quest).create().show();
        } else
            mTvPerUsername.setText(user.getMuserName());
    }


}
