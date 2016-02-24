package com.goqual.mercury.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goqual.mercury.R;
import com.goqual.mercury.data.local.ReportDTO;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ladmusician on 2/24/16.
 */
public class ReportsAdapter extends RecyclerView.Adapter<ReportsAdapter.ReportsViewHolder> {
    private List<ReportDTO> mReportList = null;
    private Context mContext = null;

    public void setReportList(List<ReportDTO> reports) {
        this.mReportList = reports;
    }

    public ReportsAdapter(Context ctx) {
        this.mContext = ctx;
        this.mReportList = new ArrayList<>();
    }

    @Override
    public ReportsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_report, parent, false);
        return new ReportsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ReportsViewHolder holder, int position) {
        holder.bindView(mReportList.get(position));
    }

    @Override
    public int getItemCount() {
        return mReportList.size();
    }

    class ReportsViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.report_content_title)
        TextView mTitle;
        @Bind(R.id.report_content_description)
        TextView mContent;
        @Bind(R.id.report_content_position)
        TextView mPosition;
        @Bind(R.id.report_content_date)
        TextView mDate;
        @Bind(R.id.report_content_img)
        ImageView mImage;

        public ReportsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(ReportDTO report) {
            mTitle.setText(report.getTitle());
            mContent.setText(report.getContent());
            mPosition.setText(report.getLocation());
            mDate.setText(report.getDate());
            Glide.with(mContext)
                    .load("https://scontent.cdninstagram.com/t51.2885-15/e35/12545342_1527061687624177_770307423_n.jpg?ig_cache_key=MTE2MjMzNzUzOTY3Nzc0MTQ3Nw%3D%3D.2")
                    .into(mImage);
        }
    }
}
