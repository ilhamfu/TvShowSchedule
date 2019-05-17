package hp.test.mytv.adapter;

import hp.test.mytv.R;
import hp.test.mytv.model.show_detail.Network;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailNetworkAdapter extends RecyclerView.Adapter<DetailNetworkAdapter.DetailNetworkAdapterViewHolder>{

    private List<Network> networks;

    public DetailNetworkAdapter(List<Network> networks) {
        this.networks = networks;
    }

    @NonNull
    @Override
    public DetailNetworkAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_network_item, viewGroup, false);
        return new DetailNetworkAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailNetworkAdapterViewHolder detailNetworkAdapterViewHolder, int i) {

        String imgUrl = "http://image.tmdb.org/t/p/w92" + networks.get(i).getLogoPath();

        detailNetworkAdapterViewHolder.tvName.setText(networks.get(i).getName());
        Picasso.get().load(imgUrl).into(detailNetworkAdapterViewHolder.ivLogo);

    }

    @Override
    public int getItemCount() {
        return networks.size();
    }

    class DetailNetworkAdapterViewHolder extends RecyclerView.ViewHolder {

        ImageView ivLogo;
        TextView tvName;

        public DetailNetworkAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ivLogo = itemView.findViewById(R.id.iv_network_logo);
            tvName = itemView.findViewById(R.id.tv_network_name);

        }
    }
}
