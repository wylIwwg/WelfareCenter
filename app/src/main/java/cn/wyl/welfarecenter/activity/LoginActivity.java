package cn.wyl.welfarecenter.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

/**
 * 项目名称：WelfareCenter
 * 创建人：wyl
 * 时间：2016/10/14 14:18
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView mImgBack;
    @BindView(R.id.et_userName)
    EditText mEtUserName;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.btn_freeRegister)
    Button mBtnFreeRegister;

    String name;
    LoginActivity mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        name = getIntent().getStringExtra("userName");
        mContext = this;
        if (name != null) {
            mEtUserName.setText(name);
        }
    }

    @OnClick({R.id.img_back, R.id.btn_login, R.id.btn_freeRegister})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                MFGT.finish(this);
                break;
            case R.id.btn_login:
                final String name = mEtUserName.getText().toString();
                String password = mEtPassword.getText().toString();
                if (name.equals("")) {
                    Toast.makeText(LoginActivity.this, R.string.user_name_connot_be_empty, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.equals("")) {
                    Toast.makeText(LoginActivity.this, R.string.password_connot_be_empty, Toast.LENGTH_SHORT).show();
                    return;
                }

                // isExistName(name);
                userLogin(name, password);

                break;
            case R.id.btn_freeRegister:
                MFGT.startActivity(LoginActivity.this, RegisterActivity.class);
                break;
        }
    }

    private void userLogin(String name, String password) {
        NetDao.doLogin(this, name, password, new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String result) {
                Result userresult = ResultUtils.getResultFromJson(result, UserAvatar.class);
                if (userresult.getRetCode() == 0 && userresult.isRetMsg()) {
                    Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                    UserAvatar user = (UserAvatar) userresult.getRetData();
                    UserDao dao = new UserDao(mContext);
                    boolean isSuccess = dao.saveUser(user);
                    if (isSuccess) {
                        WelfareCenterApplication.setUser(user);
                        MFGT.finish(mContext);
                    }
                } else {
                    Toast.makeText(LoginActivity.this, R.string.login_failed, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(LoginActivity.this, R.string.login_failed, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void isExistName(final String name) {
        //判断是否已经存在该用户
        NetDao.doFindUserByName(this, name, new OkHttpUtils.OnCompleteListener<Result>() {
            @Override
            public void onSuccess(Result result) {
                if (result.getRetCode() == 0 && result.isRetMsg()) {
                    Toast.makeText(LoginActivity.this, "用户名：" + name + " 不存在！", Toast.LENGTH_SHORT).show();
                    mEtUserName.requestFocus();
                    return;
                }
            }

            @Override
            public void onError(String error) {
            }
        });
    }
}
