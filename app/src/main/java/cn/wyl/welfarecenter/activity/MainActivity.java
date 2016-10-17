package cn.wyl.welfarecenter.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import cn.wyl.welfarecenter.R;
import cn.wyl.welfarecenter.adapters.NewGoodsAdapter;
import cn.wyl.welfarecenter.bean.NewGoodsBean;

/**
 * 添加闪屏
 */
public class MainActivity extends AppCompatActivity {
    NewGoodsAdapter mNewGoodsAdapter;
    List<NewGoodsBean> mList;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;

    RadioGroup mGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGroup= (RadioGroup) findViewById(R.id.rg_menu);
        initView();

    }

    private void initView() {
        mLayoutManager=new LinearLayoutManager(this);
        mRecyclerView= (RecyclerView) findViewById(R.id.recyclerLayout);
        mList=new ArrayList<>();
        mNewGoodsAdapter=new NewGoodsAdapter(this,mList);
        mRecyclerView.setAdapter(mNewGoodsAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }


    RadioButton rbtn_cart = null;
    RadioButton groupButton = null;
    public void onMenuButtonChanged(View view){

        if (view.getId() != R.id.rb_cart) {
            if (rbtn_cart != null) {
                rbtn_cart.setChecked(false);
            }
            groupButton = (RadioButton) view;
            switch (view.getId()) {
                case R.id.rb_boutique:
                    Log.d("main", "boutique");
                    break;
                case R.id.rb_category:
                    Log.d("main", "category");
                    break;
                case R.id.rb_newgoods:

                    Log.d("main", "newgoods");

                    break;
                case R.id.rb_center:
                    Log.d("main", "center");
                    break;
            }
        } else {
            rbtn_cart = (RadioButton) view;
            if (groupButton != null) {
                groupButton.setChecked(false);
            }
        }

    }


}
