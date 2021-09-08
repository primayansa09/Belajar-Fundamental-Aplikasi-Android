package com.example.submissiongithub2;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import cz.msebera.android.httpclient.Header;

public class DetailViewModel extends ViewModel {

    private MutableLiveData<ArrayList<DataUser>> detailModel = new MutableLiveData<>();

    void setDetail(String dataDetail){
        ArrayList<DataUser> detail = new ArrayList<>();
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.github.com/users/" + dataDetail;
        client.addHeader("Authorization", "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");
        client.addHeader("User-Agent", "request");
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                Log.d(ActivityDetail.TAG, result);
                try {
                    JSONObject user = new JSONObject(result);

                    String mName = user.getString("name");
                    String mPhoto = user.getString("avatar_url");
                    String mLoc = user.getString("location");
                    String mComp = user.getString("company");
                    String mRepos = user.getString("public_repos");
                    String mFollowers = user.getString("followers");
                    String mFollowing = user.getString("following");

                    DataUser dataUser = new DataUser();
                    dataUser.setNameUser(mName);
                    dataUser.setPhotoUser(mPhoto);
                    dataUser.setLocation(mLoc);
                    dataUser.setCompany(mComp);
                    dataUser.setRepository(mRepos);
                    dataUser.setFollower(mFollowers);
                    dataUser.setFollowing(mFollowing);

                    detail.add(dataUser);

                    detailModel.postValue(detail);

                } catch (JSONException e) {
                    Log.d("Exception", e.getMessage().toString());
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
                        errorMessage = statusCode + " ; " + error.getMessage();
                }
            }
        });
    }

    LiveData<ArrayList<DataUser>> getData(){
        return detailModel;
    }
}
