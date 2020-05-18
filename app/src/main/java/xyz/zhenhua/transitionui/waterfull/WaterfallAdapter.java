package xyz.zhenhua.transitionui.waterfull;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import xyz.zhenhua.transitionui.DetailActivity;
import xyz.zhenhua.transitionui.R;

/**
 * Created by zachary on 2018/4/24.
 */

public class WaterfallAdapter extends RecyclerView.Adapter<WaterfallAdapter.WaterfallAdapterViewHolder> {

    Context mContext;
    List<WaterfallBean> datas;

    public WaterfallAdapter(Context mContext, List<WaterfallBean> datas) {
        this.mContext = mContext;
        this.datas = datas;
    }


    @NonNull
    @Override
    public WaterfallAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_waterfall, parent, false);

        return new WaterfallAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final WaterfallAdapterViewHolder holder, final int position) {
        holder.cardView.setCardBackgroundColor(Color.parseColor(datas.get(position).getBgColor()));
        ViewGroup.LayoutParams layoutParams = holder.cardView.getLayoutParams();
        layoutParams.height = datas.get(position).getHeight();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, holder.cardView, "card").toBundle();
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("color", datas.get(position).getBgColor());
                mContext.startActivity(intent, bundle);
            }
        });
        holder.cardView.setLayoutParams(layoutParams);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class WaterfallAdapterViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;

        public WaterfallAdapterViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cv_item_waterfall);
        }
    }
}
