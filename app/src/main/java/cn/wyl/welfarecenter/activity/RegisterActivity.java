package cn.wyl.welfarecenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.regex.Pattern;

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
 * 时间：2016/10/21 9:45
 */
public class RegisterActivity extends BaseActivity {
    @BindView(R.id.img_back)
    ImageView mImgBack;
    @BindView(R.id.et_regi_userName)
    EditText mEtRegiUserName;
    @BindView(R.id.et_regi_userNick)
    EditText mEtRegiUserNick;
    @BindView(R.id.et_regi_password)
    EditText mEtRegiPassword;
    @BindView(R.id.et_regi_password_comfirm)
    EditText mEtRegiPasswordComfirm;
    @BindView(R.id.btn_register)
    Button mBtnRegister;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        setListener();
    }

    private void setListener() {


    }


    @OnClick({R.id.img_back, R.id.et_regi_userName, R.id.et_regi_password, R.id.et_regi_password_comfirm, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                break;
            case R.id.et_regi_userName:

                break;
            case R.id.et_regi_password:
                break;
            case R.id.et_regi_password_comfirm:
                break;
            case R.id.btn_register:
                String regexName = "^[a-zA-Z][a-zA-Z0-9_]{5,16}$";
                String regexPasswprd = "[a-zA-Z0-9_]{5,16}$";
                String regexNick = "^\\w{3,15}$";

                String name = mEtRegiUserName.getText().toString().trim();
                String nick = mEtRegiUserNick.getText().toString().trim();
                String password = mEtRegiPassword.getText().toString().trim();
                String passwordComfirm = mEtRegiPasswordComfirm.getText().toString();
                if (!Pattern.matches(regexName, name)) {
                    mEtRegiUserName.setError("用户名非法~");

                    break;
                }
                if (!Pattern.matches(regexNick, nick) || nick.equals("")) {
                    mEtRegiUserNick.setError("昵称为空或非法~");
                    break;
                }
                if (!Pattern.matches(regexPasswprd, password)) {
                    mEtRegiPassword.setError("密码非法~");

                    break;
                }
                if (!passwordComfirm.equals(password)) {
                    Toast.makeText(RegisterActivity.this, "密码不一致", Toast.LENGTH_SHORT).show();
                    break;
                }

                NetDao.doRegisterUser(this, name, nick, password, new OkHttpUtils.OnCompleteListener<Result>() {
                    @Override
                    public void onSuccess(Result result) {

                        if (result.getRetCode()==0&&result.isRetMsg()){
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            intent.putExtra("userName", mEtRegiUserName.getText().toString());
                            MFGT.startActivity(RegisterActivity.this, intent);
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });

                break;
        }
    }
}
