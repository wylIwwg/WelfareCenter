package cn.wyl.welfarecenter.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.wyl.welfarecenter.R;
import cn.wyl.welfarecenter.WelfareCenterApplication;
import cn.wyl.welfarecenter.bean.Result;
import cn.wyl.welfarecenter.bean.UserAvatar;
import cn.wyl.welfarecenter.dao.UserDao;
import cn.wyl.welfarecenter.net.NetDao;
import cn.wyl.welfarecenter.utils.MFGT;
import cn.wyl.welfarecenter.utils.OkHttpUtils;
import cn.wyl.welfarecenter.utils.ResultUtils;

public class UpdateNickActivity extends BaseActivity {
    UserAvatar user;
    @BindView(R.id.et_update_nick)
    EditText mEtUpdateNick;
    @BindView(R.id.btn_upda_ok)
    Button mBtnUpdaOk;
    UpdateNickActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_nick);
        ButterKnife.bind(this);

        mContext = this;
        user = WelfareCenterApplication.getUser();
        if (user != null) {
            mEtUpdateNick.setText(user.getMuserNick());
        }
    }

    @OnClick(R.id.btn_upda_ok)
    public void onClick() {
        String nick = mEtUpdateNick.getText().toString().trim();
        if (nick.isEmpty()) {
            Toast.makeText(UpdateNickActivity.this, "昵称不能为空", Toast.LENGTH_SHORT).show();
        } else if (nick.equals(user.getMuserNick())) {
            Toast.makeText(UpdateNickActivity.this, "昵称未做修改", Toast.LENGTH_SHORT).show();
        } else
            updateNick(nick);
    }

    private void updateNick(String nick) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setTitle("更新中...");
        pd.show();
        NetDao.updateUserNick(this, user.getMuserName(), nick, new OkHttpUtils.OnCompleteListener<String>() {
                    @Override
                    public void onSuccess(String result) {

                        Result json = ResultUtils.getResultFromJson(result, UserAvatar.class);
                        if (json != null && json.isRetMsg() && json.getRetCode() == 0) {
                            UserAvatar user = (UserAvatar) json.getRetData();
                            Log.e("main", "更新" + user);


                            UserDao dao = new UserDao(mContext);
                            boolean isSuccess = dao.updateUser(user);
                            if (isSuccess) {
                                WelfareCenterApplication.setUser(user);

                                MFGT.finish(mContext);

                                setResult(RESULT_OK, null);
                                pd.dismiss();
                            }

                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                }

        );
    }
}
