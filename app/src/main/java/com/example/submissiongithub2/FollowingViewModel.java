package com.example.submissiongithub2;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import cz.msebera.android.httpclient.Header;

public class FollowingViewModel extends ViewModel {

    public final MutableLiveData<ArrayList<DataUser>> listModel = new MutableLiveData<>();

    void setFollowing(String following){
        ArrayList<DataUser> listFollowing = new ArrayList<>();
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.github.com/users/"+ following +"/following";
        client.addHeader("Authorization", "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");
        client.addHeader("User-Agent", "request");
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                Log.d(FollowingFragment.TAG, result);
                try {
                    JSONArray jsonArray = new JSONArray(result);

                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);

                        String photoSederhana = object.getString("avatar_url");
                        String namesederhana = object.getString("login");

                        DataUser data = new DataUser();
                        data.setPhotoUser(photoSederhana);
                        data.setNameUser(namesederhana);

                        listFollowing.add(data);
                    }
                    listModel.postValue(listFollowing);
                } catch (JSONException e) {
                    Log.d("Exception", e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String errorMessage;
                switch (statusCode){
                    case 401:
                        errorMessage = statusCode + "Bad Request";
                        break;
                    case 403:
                        errorMessage = statusCode + "Forbiden";
                        break;
                    case 404:
                        errorMessage = statusCode + "Not Found";
                        break;
                    default:
                        errorMessage = statusCode + " : " + error.getMessage();
                }
            }
        });
    }

     LiveData<ArrayList<DataUser>> getData() {
        return listModel;
    }
}
