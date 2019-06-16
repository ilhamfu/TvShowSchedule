package hp.test.mytv.services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import hp.test.mytv.model.on_air.OnAirItem;
import hp.test.mytv.model.on_air.OnAirResult;
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
                AllPagerResultReceiver allPagerResultReceiver = new AllPagerResultReceiver(new Handler());
                Intent intent = new Intent(FetchJobService.this,FetchAllPageService.class);
                intent.putExtra("receiver",allPagerResultReceiver);
                intent.putExtra("TOTAL_PAGE",response.body().getTotalPages());
                startService(intent);
            }

            @Override
            public void onFailure(Call<OnAirResult> call, Throwable t) {

            }
        });
    }


    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Job Finished");
        return true;
    }

    private class AllPagerResultReceiver extends ResultReceiver{
        /**
         * Create a new ResultReceive to receive results.  Your
         * {@link #onReceiveResult} method will be called from the thread running
         * <var>handler</var> if given, or from an arbitrary thread if null.
         *
         * @param handler
         */
        AllPagerResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle bundle){
            List<OnAirItem> onAirItems = (List<OnAirItem>) bundle.getSerializable("DATA");
            databaseHelper.addOnAirs(onAirItems);
            databaseHelper.updateFavorite();
        }


    }
}

