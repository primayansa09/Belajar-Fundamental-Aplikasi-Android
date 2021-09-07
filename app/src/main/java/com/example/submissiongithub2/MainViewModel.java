package com.example.submissiongithub2;

import android.content.Context;
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
        String url = "https://api.github.com/search/users?q=" + query;
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

    void getFollowers(String username){
        ArrayList<DataUser> listFollowers = new ArrayList<>();
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.github.com/users/"+username+"/followers";
        client.addHeader("Authorization", "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");
        client.addHeader("User-Agent", "request");
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                Log.d(FollowersFragment.TAG, url);
                try {
                        JSONArray jsonArray = new JSONArray(result);

                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String name = object.getString("login");
                            String avatar = object.getString("avatar_url");

                            DataUser data = new DataUser();
                            data.setNameUser(name);
                            data.setPhotoUser(avatar);

                            listFollowers.add(data);
                        }
                        listModel.postValue(listFollowers);

                } catch (JSONException e) {
                    Log.d(FollowersFragment.TAG, "onSuccess : Gagal...");
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

    void getFollowing(String following){
        ArrayList<DataUser> listFollowing = new ArrayList<>();
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.github.com/users/"+ following +"/following";
        client.addHeader("Authorization", "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");
        client.addHeader("User-Agent", "request");
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                Log.d(FollowingFragment.TAG, "onSuccess : Berhasil...");
                try {
                    JSONArray jsonArray = new JSONArray(result);

                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);

                        String photo = object.getString("avatar_url");
                        String name = object.getString("login");

                        DataUser data = new DataUser();
                        data.setPhotoUser(photo);
                        data.setNameUser(name);

                        listFollowing.add(data);
                    }
                    listModel.postValue(listFollowing);
                } catch (JSONException e) {
                    Log.d(FollowingFragment.TAG, "onSuccess : Gagal");
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

    void getDetail(String detail){
        ArrayList<DataUser> mDetail = new ArrayList<>();
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.github.com/users/" + detail;
        client.addHeader("Authorization", "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");
        client.addHeader("User-Agent", "request");
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                Log.d(ActivityDetail.TAG, "onSuccess : Berhasil...");
                try {
                    JSONObject jsonObject = new JSONObject(result);

                        String photo = jsonObject.getString("avatar_url");
                        String name = jsonObject.getString("name");
                        String comp = jsonObject.getString("company");
                        String loc = jsonObject.getString("location");
                        String repos = jsonObject.getString("public_repos");
                        String follower = jsonObject.getString("followers");
                        String following = jsonObject.getString("following");

                        DataUser data = new DataUser();
                        data.setNameUser(name);
                        data.setPhotoUser(photo);
                        data.setCompany(comp);
                        data.setLocation(loc);
                        data.setRepository(repos);
                        data.setFollower(follower);
                        data.setFollowing(following);

                        mDetail.add(data);

                    listModel.postValue(mDetail);
                } catch (JSONException e) {
                    Log.d(ActivityDetail.TAG, "onSuccess : Gagal...");
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

    LiveData<ArrayList<DataUser>> getData(){
        return listModel;
    }

}
