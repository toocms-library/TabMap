package com.toocms.tab.map.choosing.poi;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.toocms.tab.map.R;
import com.toocms.tab.map.choosing.config.ChoosingConfig;
import com.toocms.tab.toolkit.ListUtils;

import java.util.List;

/**
 * POI列表适配器
 * <p>
 * Author：Zero
 * Date：2020/6/23 17:27
 *
 * @version v1.0
 */

public class PoiListAdapter extends RecyclerView.Adapter<PoiListAdapter.ViewHolder> {

    private Activity activity;
    private RecyclerView recyclerView;
    private LayoutInflater inflater;
    private List<PoiItem> list;

    public PoiListAdapter(Activity activity, RecyclerView recyclerView) {
        this.activity = activity;
        this.recyclerView = recyclerView;
        inflater = LayoutInflater.from(activity);
    }

    public void setData(List<PoiItem> list) {
        this.list = list;
        notifyDataSetChanged();
        if (ListUtils.isEmpty(list)) recyclerView.setVisibility(View.GONE);
        else recyclerView.setVisibility(View.VISIBLE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.listitem_near_poi, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final PoiItem poiItem = list.get(position);
        holder.tvName.setText(poiItem.getTitle());
        String address = poiItem.getSnippet();
        if (TextUtils.isEmpty(address)) {
            address = poiItem.getCityName() + poiItem.getAdName();
        }
        holder.tvAddress.setText(address);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ChoosingConfig.poiResultListener != null) {
                    LatLonPoint point = poiItem.getLatLonPoint();
                    PoiResult result = new PoiResult(poiItem.getTitle(), poiItem.getSnippet(), String.valueOf(point.getLatitude()), String.valueOf(point.getLongitude()));
                    ChoosingConfig.poiResultListener.onPoiResult(result);
                }
                activity.setResult(Activity.RESULT_OK);
                activity.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return ListUtils.getSize(list);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout item;
        public TextView tvName;
        public TextView tvAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.listitem_near_poi_item);
            tvName = itemView.findViewById(R.id.listitem_near_poi_name);
            tvAddress = itemView.findViewById(R.id.listitem_near_poi_address);
        }
    }
}
