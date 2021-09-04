package com.example.submissiongithub2;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;
import android.widget.Toast;

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

public class MainViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<DataUser>> listModel =new MutableLiveData<>();

    void ListSearch(String query, Context context){
        ArrayList<DataUser> dataSearch = new ArrayList<>();
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.github.com/search/users?q=sidiqpermana" + query;
        client.addHeader("Authorization", "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");
        client.addHeader("User-Agent", "request");
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                Log.d(ActivityHome.TAG, "onSuccess : Berhasil...");
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("items");

                    for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject items = jsonArray.getJSONObject(i);
                    String name = items.getString("login");
                    String photo = items.getString("avatar_url");

                    DataUser data = new DataUser();
                    data.setNameUser(name);
                    data.setPhotoUser(photo);

                    dataSearch.add(data);
                    }
                    listModel.postValue(dataSearch);

                } catch (JSONException e) {
                    Log.d(ActivityHome.TAG, "onSuccess : Gagal...");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            String errorMessage;
            switch (statusCode){
                case 401:
                    errorMessage = statusCode + " : Bad Request";
                    break;
                case 403:
                    errorMessage = statusCode + " : Forbiden";
                    break;
                case 404:
                    errorMessage = statusCode + " : Not Found";
                    break;
                default:
                    errorMessage = statusCode + " : " + error.getMessage();
            }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

    }

    void getDataUser(Context context){
        ArrayList<DataUser> listUser = new ArrayList<>();
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.github.com/search/users?q=username";
        client.addHeader("Authorization", "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");
        client.addHeader("User-Agent", "request");
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                Log.d(ActivityHome.TAG, "onSuccess : Berhasil...");
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("items");

                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject items = jsonArray.getJSONObject(i);
                        String nameUser = items.getString("login");
                        String photoUser = items.getString("avatar_url");

                        DataUser user = new DataUser();
                        user.setPhotoUser(photoUser);
                        user.setNameUser(nameUser);

                        listUser.add(user);
                    }

                    listModel.postValue(listUser);

                } catch (JSONException e) {
                    Log.d(ActivityHome.TAG, "onSuccess : Gagal...");
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
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    LiveData<ArrayList<DataUser>> getData(){
        return listModel;
    }
}
