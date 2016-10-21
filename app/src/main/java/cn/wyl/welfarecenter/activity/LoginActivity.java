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
import cn.wyl.welfarecenter.bean.Result;
import cn.wyl.welfarecenter.net.NetDao;
import cn.wyl.welfarecenter.utils.MFGT;
import cn.wyl.welfarecenter.utils.OkHttpUtils;

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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        name=getIntent().getStringExtra("userName");
        if (name!=null){
            mEtUserName.setText(name);
        }
    }

    @OnClick({R.id.img_back, R.id.btn_login, R.id.btn_freeRegister})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                break;
            case R.id.btn_login:
                final String name=mEtUserName.getText().toString();
                NetDao.doFindUserByName(this, name, new OkHttpUtils.OnCompleteListener<Result>() {
                    @Override
                    public void onSuccess(Result result) {

                            Toast.makeText(LoginActivity.this, result.toString()+"登录成功！", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(String error) {

                    }
                });
                break;
            case R.id.btn_freeRegister:
                MFGT.startActivity(LoginActivity.this,RegisterActivity.class);
                break;
        }
    }
}
