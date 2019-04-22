package hp.test.mytv;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class TvAdapter extends RecyclerView.Adapter<TvAdapter.ViewHolder> {

    private List<String> rvData;

    public TvAdapter(List<String> inputData) {
        rvData = inputData;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView waktu;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.tv_title);
            waktu = view.findViewById(R.id.tv_waktu);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_main, parent, false);

        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final String name = rvData.get(position);
        holder.title.setText(rvData.get(position));
        holder.waktu.setText(rvData.get(position));
    }

    @Override
    public int getItemCount() {

        return rvData.size();
    }

}
