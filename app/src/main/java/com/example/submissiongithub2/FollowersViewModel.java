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

public class FollowersViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<DataUser>> listModel = new MutableLiveData<>();

    void setFollowers(String username){
        ArrayList<DataUser> listFollowers = new ArrayList<>();
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.github.com/users/"+username+"/followers";
        client.addHeader("Authorization", "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");
        client.addHeader("User-Agent", "request");
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                Log.d(FollowersFragment.TAG, result);
                try {
                    JSONArray jsonArray = new JSONArray(result);

                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        String mName = object.getString("login");
                        String mPhoto = object.getString("avatar_url");

                        DataUser data = new DataUser();
                        data.setNameUser(mName);
                        data.setPhotoUser(mPhoto);

                        listFollowers.add(data);
                    }
                    listModel.postValue(listFollowers);

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
                        errorMessage = statusCode + "bad Request";
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
