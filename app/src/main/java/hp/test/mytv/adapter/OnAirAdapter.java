package hp.test.mytv.adapter;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import hp.test.mytv.model.OnAirItem;


public class OnAirAdapter extends RecyclerView.Adapter<OnAirAdapter.ViewHolder> {

    private List<OnAirItem> data;

    public OnAirAdapter(List<OnAirItem> inputData) {
        data = inputData;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMainTitle;
        TextView tvTitle;
        TextView tvDesc;
        ImageView ivPoster;
        ToggleButton btnFavorite;

        ViewHolder(View view) {
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_main, parent, false);

        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        Log.d("Adapter", "Setting data for : "+data.get(position).getOriginalName());

        holder.tvMainTitle.setText(data.get(position).getOriginalName());
        holder.tvTitle.setText(data.get(position).getName());
        holder.tvDesc.setText(data.get(position).getOverview());
        String imgUrl = "http://image.tmdb.org/t/p/w92/" + data.get(position).getPosterPath();

        Picasso.get().load(imgUrl).into(holder.ivPoster);

        holder.btnFavorite.setChecked(false);
        holder.btnFavorite.setBackgroundDrawable(ContextCompat.getDrawable(holder.btnFavorite.getContext(), R.drawable.ic_favorite_black_24dp));
        holder.btnFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    holder.btnFavorite.setBackgroundDrawable(ContextCompat.getDrawable(holder.btnFavorite.getContext(),R.drawable.ic_favorite_24dp));
                } else
                    holder.btnFavorite.setBackgroundDrawable(ContextCompat.getDrawable(holder.btnFavorite.getContext(), R.drawable.ic_favorite_black_24dp));
            }
        });
    }

    @Override
    public int getItemCount() {

        return data.size();
    }

}
