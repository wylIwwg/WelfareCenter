package cn.wyl.welfarecenter.fragments;

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
import cn.wyl.welfarecenter.activity.BoutiqueCategoryActivity;
import cn.wyl.welfarecenter.bean.BoutiqueBean;
import cn.wyl.welfarecenter.common.CommonAdapter;
import cn.wyl.welfarecenter.common.CommonViewHolder;
import cn.wyl.welfarecenter.net.NetDao;
import cn.wyl.welfarecenter.utils.ConvertUtils;
import cn.wyl.welfarecenter.utils.ImageLoader;
import cn.wyl.welfarecenter.utils.MFGT;
import cn.wyl.welfarecenter.utils.OkHttpUtils;

public class BoutiqueFragment extends Fragment {

    @BindView(R.id.recy_boutique)
    RecyclerView mRecyBoutique;

    LinearLayoutManager mLayoutManager;
    // BoutiqueAdapter mAdapter;
    // ArrayList<BoutiqueBean> mList;

    CommonAdapter<BoutiqueBean> testAdapter;
    ArrayList<BoutiqueBean> testList;

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
                if (result.length > 0 && result != null) {
                    ArrayList<BoutiqueBean> boutis = ConvertUtils.array2List(result);
                    // mAdapter.initData(boutis);
                    testAdapter.initData(boutis);
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void initView() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        //  mList=new ArrayList<>();

        testList = new ArrayList<>();
        testAdapter = new CommonAdapter<BoutiqueBean>(getActivity(), testList, R.layout.item_boutique) {
            @Override
            public void convert(CommonViewHolder holder, final BoutiqueBean boutiqueBean) {

                holder.setOnClickListener(R.id.relaLyaout_boutique, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(mContext, BoutiqueCategoryActivity.class);
                        intent.putExtra(I.Boutique.NAME,boutiqueBean.getName());
                        intent.putExtra(I.Boutique.CAT_ID,boutiqueBean.getId());
                        MFGT.startActivity(getActivity(),intent);
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
