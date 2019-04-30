package hp.test.mytv.Adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.List;

import hp.test.mytv.Activity.MainActivity;
import hp.test.mytv.R;


public class TvAdapter extends RecyclerView.Adapter<TvAdapter.ViewHolder> {

    private List<String> rvData;

    public TvAdapter(List<String> inputData) {
        rvData = inputData;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvTime;
        ToggleButton btnFavorite;

        ViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tv_title);
            tvTime = view.findViewById(R.id.tv_time);
            btnFavorite = view.findViewById(R.id.btn_favorite);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_main, parent, false);

        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.tvTitle.setText(rvData.get(position));
        holder.tvTime.setText(rvData.get(position));
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

        return rvData.size();
    }

}
