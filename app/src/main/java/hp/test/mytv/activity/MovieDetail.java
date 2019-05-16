package hp.test.mytv.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.Objects;

import hp.test.mytv.R;
import hp.test.mytv.adapter.DetailGenreAdapter;
import hp.test.mytv.model.show_detail.ShowDetailResult;
import hp.test.mytv.utils.APIClient;
import hp.test.mytv.utils.ExpandableHeightGridView;
import hp.test.mytv.utils.TMDBInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetail extends AppCompatActivity {

    private ImageView ivBackdropPath;
    private ProgressBar pgLoadingPoster;
    private TMDBInterface tmdbInterface;
    private TextView tvOverview;
    private TextView tvRating;
    private TextView tvRatingCount;
    private TextView tvPopularity;
    private Target mTarget;
    private ExpandableHeightGridView gvGenres;

    @SuppressLint({"RestrictedApi", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ivBackdropPath = findViewById(R.id.iv_collapsing_toolbar);
        pgLoadingPoster = findViewById(R.id.pg_loading_poster);
        tvOverview = findViewById(R.id.tv_overview);
        tvPopularity = findViewById(R.id.tv_popularity);
        tvRating = findViewById(R.id.tv_rating);
        tvRatingCount = findViewById(R.id.tv_rating_count);
        gvGenres = findViewById(R.id.gv_genres);


        gvGenres.setNumColumns(2);
        gvGenres.setExpanded(true);


        int showID = (int) getIntent().getIntExtra("SHOW_ID",0);
        String showName = getIntent().getStringExtra("SHOW_NAME");
        String showSubname = getIntent().getStringExtra("SHOW_SUBNAME");

        tmdbInterface = APIClient.getClient().create(TMDBInterface.class);

        Objects.requireNonNull(getSupportActionBar()).setTitle(showName);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        setData(showID);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    private void setData(int id){

        mTarget = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Log.d("imgURL", "Begin Loading Image");
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int width = displayMetrics.widthPixels;
                ivBackdropPath.getLayoutParams().height = (int) ((double)bitmap.getHeight()/bitmap.getWidth()*width);
                ivBackdropPath.requestLayout();
                ivBackdropPath.setImageBitmap(bitmap);
                Log.d("imgURL", "Loading Image");
                ivBackdropPath.setVisibility(View.VISIBLE);
                pgLoadingPoster.setVisibility(View.GONE);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };


        final Call<ShowDetailResult> movieDetail = tmdbInterface.getMovieDetail(id);
        movieDetail.enqueue(new Callback<ShowDetailResult>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<ShowDetailResult> call, @NonNull Response<ShowDetailResult> response) {
                assert response.body() != null;
                ShowDetailResult movieDetailResult = response.body();
                String imgUrl = "http://image.tmdb.org/t/p/w780" + movieDetailResult.getPosterPath();
                Log.d("imgURL", String.valueOf(movieDetailResult.getGenres().size()));


                tvOverview.setText(movieDetailResult.getOverview());
                tvPopularity.setText(Double.toString(movieDetailResult.getPopularity()));
                tvRating.setText(Double.toString(movieDetailResult.getVoteAverage()));
                tvRatingCount.setText(Integer.toString(movieDetailResult.getVoteCount()));

                gvGenres.setAdapter(new DetailGenreAdapter(movieDetailResult.getGenres(),tvOverview.getContext()));

                Picasso.get().load(imgUrl).into(mTarget);


            }

            @Override
            public void onFailure(Call<ShowDetailResult> call, Throwable t) {

            }
        });

    }


}

