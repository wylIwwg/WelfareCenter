package cn.wyl.welfarecenter.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.wyl.welfarecenter.R;
import cn.wyl.welfarecenter.adapters.BoutiqueAdapter;
import cn.wyl.welfarecenter.bean.BoutiqueBean;
import cn.wyl.welfarecenter.net.NetDao;
import cn.wyl.welfarecenter.utils.ConvertUtils;
import cn.wyl.welfarecenter.utils.OkHttpUtils;

public class BoutiqueFragment extends Fragment {

    @BindView(R.id.recy_boutique)
    RecyclerView mRecyBoutique;

    LinearLayoutManager mLayoutManager;
    BoutiqueAdapter mAdapter;
    ArrayList<BoutiqueBean> mList;

    public BoutiqueFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_boutique, container, false);
        ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    private void initData() {
        NetDao.downBoutique(getActivity(), new OkHttpUtils.OnCompleteListener<BoutiqueBean[]>() {
            @Override
            public void onSuccess(BoutiqueBean[] result) {
                if (result.length>0&&result!=null){
                    ArrayList<BoutiqueBean> boutis = ConvertUtils.array2List(result);
                    mAdapter.initData(boutis);
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void initView() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mList=new ArrayList<>();
        mAdapter=new BoutiqueAdapter(getActivity(),mList);
        mRecyBoutique.setAdapter(mAdapter);
        mRecyBoutique.setLayoutManager(mLayoutManager);

    }


}
