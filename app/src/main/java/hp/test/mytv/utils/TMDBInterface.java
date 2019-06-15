package hp.test.mytv.utils;

import hp.test.mytv.model.genre.GenreResult;
import hp.test.mytv.model.show_detail.ShowDetailResult;
import hp.test.mytv.model.on_air.OnAirResult;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TMDBInterface {
    @GET("genre/tv/list?api_key=0bb80c27e7acca74020ce73ec0577699&language=en-US")
    Call<GenreResult> getGenreData();

    @GET("tv/airing_today?api_key=0bb80c27e7acca74020ce73ec0577699&language=en-US")
    Call<OnAirResult> getOnAir(@Query("page") int page);

    @GET("tv/{id}?api_key=0bb80c27e7acca74020ce73ec0577699&language=en-US")
    Call<ShowDetailResult> getMovieDetail(@Path("id") int id);
}
