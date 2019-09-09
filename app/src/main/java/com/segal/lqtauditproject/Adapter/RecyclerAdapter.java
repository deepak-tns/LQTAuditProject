package com.segal.lqtauditproject.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.segal.lqtauditproject.R;
import com.segal.lqtauditproject.ViewModels.SiteListItem;

import java.util.List;

/**
 * Created by Hossein on 10/30/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private List<SiteListItem> mData;
    private final LayoutInflater inflater;

    public RecyclerAdapter(Context context, List<SiteListItem> data) {
        this.mData = data;
        this.inflater = LayoutInflater.from(context);

    }

    public void setDataSet(List<SiteListItem> dataSet){
        this.mData = dataSet;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.site_list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        SiteListItem currentSiteItem = mData.get(position);
        holder.setData(currentSiteItem, position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvSiteId, tvSiteName, tvCreatedAt;
        int position;
        SiteListItem currentSiteItem;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvSiteId = itemView.findViewById(R.id.siteId);
            tvSiteName = itemView.findViewById(R.id.siteName);
            tvCreatedAt = itemView.findViewById(R.id.tvCreatedAt);
        }

        public void setData(SiteListItem currentSiteItem, int position) {
            String id = currentSiteItem.getSiteId() == null ? "" : currentSiteItem.getSiteId();
            String name = currentSiteItem.getSiteName() == null ? "" : currentSiteItem.getSiteName();
            String createdAt = currentSiteItem.getCreatedAt() == null ? "" : currentSiteItem.getCreatedAt();
            String status = currentSiteItem.getStatus() == null ? "" : currentSiteItem.getStatus();

            this.tvSiteId.setText(id.length() < 1 ? "-" : id);
            this.tvSiteName.setText(name.length() < 1 ? "-" : name);
            this.tvCreatedAt.setText(status);
            this.position = position;
            this.currentSiteItem = currentSiteItem;
        }
    }

}
