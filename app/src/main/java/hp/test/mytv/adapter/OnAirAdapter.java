package hp.test.mytv.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import java.util.List;

import hp.test.mytv.R;
import hp.test.mytv.activity.MovieDetail;
import hp.test.mytv.model.on_air.OnAirItem;


public class OnAirAdapter extends RecyclerView.Adapter<OnAirAdapter.OnAirViewHolder> {

    private List<OnAirItem> data;

    public OnAirAdapter(List<OnAirItem> inputData) {
        data = inputData;
    }

    class OnAirViewHolder extends RecyclerView.ViewHolder {
        TextView tvMainTitle;
        TextView tvTitle;
        TextView tvDesc;
        ImageView ivPoster;
        ToggleButton btnFavorite;

        OnAirViewHolder(View view) {
            super(view);
            tvMainTitle = view.findViewById(R.id.tv_mainTitle);
            tvTitle = view.findViewById(R.id.tv_title);
            btnFavorite = view.findViewById(R.id.btn_favorite);
            ivPoster = view.findViewById(R.id.iv_poster);
            tvDesc = view.findViewById(R.id.tv_description);
        }
    }

    @NonNull
    @Override
    public OnAirViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_main, parent, false);
        return new OnAirViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull final OnAirViewHolder holder, final int position) {

        final Context mContext = holder.itemView.getContext();

        holder.tvMainTitle.setText(data.get(position).getOriginalName());
        holder.tvTitle.setText(data.get(position).getName());
        holder.tvDesc.setText(data.get(position).getOverview());
        String imgUrl = "http://image.tmdb.org/t/p/w300/" + data.get(position).getPosterPath();

        Picasso.get().load(imgUrl).into(holder.ivPoster);

        holder.btnFavorite.setChecked(false);
        holder.btnFavorite.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_favorite_black_24dp));
        holder.btnFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    holder.btnFavorite.setBackgroundDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_favorite_24dp));
                } else
                    holder.btnFavorite.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_favorite_black_24dp));
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MovieDetail.class);
                intent.putExtra("SHOW_ID",data.get(position).getId());
                intent.putExtra("SHOW_NAME",data.get(position).getOriginalName());
                intent.putExtra("SHOW_SUBNAME",data.get(position).getName());
                mContext.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {

        return data.size();
    }

}
