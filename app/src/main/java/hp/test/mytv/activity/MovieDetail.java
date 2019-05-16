package hp.test.mytv.activity;

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
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.w3c.dom.Text;

import java.util.Objects;

import hp.test.mytv.R;
import hp.test.mytv.model.movie_detail.MovieDetailResult;
import hp.test.mytv.utils.APIClient;
import hp.test.mytv.utils.TMDBInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetail extends AppCompatActivity {

    private ImageView ivBackdropPath;
    private ProgressBar pgLoadingPoster;
    private TMDBInterface tmdbInterface;
    private TextView tvOverview;
    private Target mTarget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ivBackdropPath = findViewById(R.id.iv_collapsing_toolbar);
        pgLoadingPoster = findViewById(R.id.pg_loading_poster);
        tvOverview = findViewById(R.id.tv_overview);

        int showID = (int) getIntent().getIntExtra("SHOW_ID",0);

        tmdbInterface = APIClient.getClient().create(TMDBInterface.class);

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


        final Call<MovieDetailResult> movieDetail = tmdbInterface.getMovieDetail(id);
        movieDetail.enqueue(new Callback<MovieDetailResult>() {
            @Override
            public void onResponse(@NonNull Call<MovieDetailResult> call, @NonNull Response<MovieDetailResult> response) {
                assert response.body() != null;
                MovieDetailResult movieDetailResult = response.body();
                String imgUrl = "http://image.tmdb.org/t/p/w780" + movieDetailResult.getPosterPath();
                Log.d("imgURL", imgUrl);


                getSupportActionBar().setTitle(movieDetailResult.getName());
                tvOverview.setText(movieDetailResult.getOverview());

                Picasso.get().load(imgUrl).into(mTarget);


            }

            @Override
            public void onFailure(Call<MovieDetailResult> call, Throwable t) {

            }
        });
    }

}

