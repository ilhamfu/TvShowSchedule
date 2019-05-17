package hp.test.mytv.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import hp.test.mytv.model.show_detail.Genre;

public class DetailGenreAdapter extends BaseAdapter {

    private List<Genre> genres;
    private Context mContext;

    public DetailGenreAdapter(List<Genre> genres, Context mContext){
        this.genres = genres;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return genres.size();
    }

    @Override
    public Genre getItem(int position) {
        return genres.get(position);
    }

    @Override
    public long getItemId(int position) {
        return genres.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView dummyTextView = new TextView(mContext);
        dummyTextView.setText(genres.get(position).getName());
        return dummyTextView;
    }
}
