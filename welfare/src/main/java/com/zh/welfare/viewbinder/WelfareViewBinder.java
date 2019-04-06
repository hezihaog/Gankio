package com.zh.welfare.viewbinder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zh.service.api.bean.GankBean;
import com.zh.welfare.R;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Package: me.wally.gankio.viewbinder
 * FileName: WelfareViewBinder
 * Date: on 2018/10/31  下午7:04
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class WelfareViewBinder extends ItemViewBinder<GankBean.ResultsBean, WelfareViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_gank_welfare_view, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GankBean.ResultsBean bean) {
        Glide
                .with(holder.itemView.getContext())
                .load(bean.getUrl())
                .into(holder.mGirlIv);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mGirlIv;

        ViewHolder(View itemView) {
            super(itemView);
            mGirlIv = itemView.findViewById(R.id.girl_iv);
        }
    }
}
