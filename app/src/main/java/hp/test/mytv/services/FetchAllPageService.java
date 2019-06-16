package hp.test.mytv.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import hp.test.mytv.model.on_air.OnAirItem;
import hp.test.mytv.model.on_air.OnAirResult;
import hp.test.mytv.utils.APIClient;
import hp.test.mytv.utils.DatabaseHelper;
import hp.test.mytv.utils.TMDBInterface;
import retrofit2.Call;
import retrofit2.Response;

public class FetchAllPageService extends IntentService {

    private TMDBInterface tmdbInterface;
    private DatabaseHelper databaseHelper;
    public FetchAllPageService() {
        super("FetchAllPage");
        tmdbInterface = APIClient.getClient().create(TMDBInterface.class);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int totalPage = intent.getIntExtra("TOTAL_PAGE",1);
        List<OnAirItem> onAirItems = new ArrayList<OnAirItem>();
        ResultReceiver resultReceiver = intent.getParcelableExtra("receiver");
        Log.d("Test", "onHandleIntent:  " + totalPage);
        for (int i=0;i<totalPage;i++){
            try {
                final Call<OnAirResult> onAirResultCall1 = tmdbInterface.getOnAir(i+1);
                Response<OnAirResult> data = onAirResultCall1.execute();
                assert data.body() != null;
                onAirItems.addAll(data.body().getResults());
                Log.d("Test", "onResponse: " + data.body().getTotalPages());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable("DATA", (Serializable) onAirItems);
        resultReceiver.send(1,bundle);

    }
}
