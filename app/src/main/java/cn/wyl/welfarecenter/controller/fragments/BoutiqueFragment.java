package cn.wyl.welfarecenter.controller.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.wyl.welfarecenter.I;
import cn.wyl.welfarecenter.R;
import cn.wyl.welfarecenter.bean.BoutiqueBean;
import cn.wyl.welfarecenter.controller.activity.BoutiqueCategoryActivity;
import cn.wyl.welfarecenter.controller.commonadapter.CommonAdapter;
import cn.wyl.welfarecenter.controller.commonadapter.CommonViewHolder;
import cn.wyl.welfarecenter.model.net.ModelNeworBoutiqueGoods;
import cn.wyl.welfarecenter.model.net.onCompleteListener;
import cn.wyl.welfarecenter.model.utils.ConvertUtils;
import cn.wyl.welfarecenter.model.utils.ImageLoader;
import cn.wyl.welfarecenter.model.utils.MFGT;

public class BoutiqueFragment extends Fragment {

    @BindView(R.id.recy_boutique)
    RecyclerView mRecyBoutique;

    LinearLayoutManager mLayoutManager;
    ModelNeworBoutiqueGoods mBoutique;

    CommonAdapter<BoutiqueBean> testAdapter;
    ArrayList<BoutiqueBean> testList;

    public BoutiqueFragment() {

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
        mBoutique.downBoutique(getActivity(), new onCompleteListener<BoutiqueBean[]>() {
            @Override
            public void onSuccess(BoutiqueBean[] result) {
                if (result.length > 0 && result != null) {
                    ArrayList<BoutiqueBean> boutis = ConvertUtils.array2List(result);

                    testAdapter.initData(boutis);
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void initView() {

        mBoutique = new ModelNeworBoutiqueGoods();
        mLayoutManager = new LinearLayoutManager(getActivity());
        //  mList=new ArrayList<>();

        testList = new ArrayList<>();
        testAdapter = new CommonAdapter<BoutiqueBean>(getActivity(), testList, R.layout.item_boutique) {
            @Override
            public void convert(CommonViewHolder holder, final BoutiqueBean boutiqueBean) {

                holder.setOnClickListener(R.id.relaLyaout_boutique, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, BoutiqueCategoryActivity.class);
                        intent.putExtra(I.Boutique.NAME, boutiqueBean.getName());
                        intent.putExtra(I.Boutique.CAT_ID, boutiqueBean.getId());
                        MFGT.startActivity(getActivity(), intent);
                    }
                });
                ImageView mImgBoutiGoods = holder.getView(R.id.img_bouti_goods);
                TextView mTvBoutiTitle = holder.getView(R.id.tv_bouti_title);
                TextView mTvBoutiName = holder.getView(R.id.tv_bouti_name);
                TextView mTvBoutiDec = holder.getView(R.id.tv_bouti_dec);
                mTvBoutiTitle.setText(boutiqueBean.getTitle());
                mTvBoutiName.setText(boutiqueBean.getName());
                mTvBoutiDec.setText(boutiqueBean.getDescription());
                ImageLoader.downloadImg(mContext, mImgBoutiGoods, boutiqueBean.getImageurl());

            }
        };

        //   mAdapter=new BoutiqueAdapter(getActivity(),mList);

        mRecyBoutique.setAdapter(testAdapter);
        mRecyBoutique.setLayoutManager(mLayoutManager);

    }


}
