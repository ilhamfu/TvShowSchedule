package hp.test.mytv.services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import hp.test.mytv.model.on_air.OnAirResult;
import hp.test.mytv.model.sql_lite.OnAir;
import hp.test.mytv.utils.APIClient;
import hp.test.mytv.utils.DatabaseHelper;
import hp.test.mytv.utils.TMDBInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchJobService extends JobService {

    private static final String TAG = "SyncService";
    private TMDBInterface tmdbInterface;
    private DatabaseHelper databaseHelper;

    @Override
    public boolean onStartJob(JobParameters params) {
        tmdbInterface = APIClient.getClient().create(TMDBInterface.class);
        databaseHelper = new DatabaseHelper(this);
        clearData();
        requestData();
        return true;
    }

    private void clearData() {
        databaseHelper.clearOnAir();
        Log.d(TAG,"DATA CLEARED");
    }

    private void requestData(){

        final Call<OnAirResult> onAirResultCall = tmdbInterface.getOnAir(1);

        onAirResultCall.enqueue(new Callback<OnAirResult>() {
            @Override
            public void onResponse(@NonNull Call<OnAirResult> call, @NonNull Response<OnAirResult> response) {
                assert response.body() != null;
                addTv(response.body());
            }

            @Override
            public void onFailure(Call<OnAirResult> call, Throwable t) {

            }
        });
    }

    private void addTv(OnAirResult result) {
        databaseHelper.addOnAirs(result.getResults());
        databaseHelper.updateFavorite();
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Job Finished");
        return true;
    }
}

