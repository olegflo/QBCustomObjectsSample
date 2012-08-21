package com.example.QBCustomObjectsSample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class MyActivity extends Activity {

    // !!! Before run this sample, go to QB admin panel woth following credentials !!!
    // https://admin.quickblox.com/
    // login: testcoobj
    // passwod: testcoobj

    static final String TAG = "QuickBlox Rest Api Test";

    // QuickBlox credentials
    // from here https://admin.quickblox.com/apps/{app_id}/edit
    // e.g. http://image.quickblox.com/2b515ec677476c1e871b211994b9.injoit.png
    static final int appId = 792;
    static final String authKey = "TQVpzyk588L2MLJ";
    static final String authSecret = "pGwqCAjcJwOJqeA";

    static final String userName = "test";
    static final String userPassword = "testtest";

    String className = "game_unit";

    // Use http://loopj.com/android-async-http/ for async requests
    AsyncHttpClient client = new AsyncHttpClient();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        /* 1. Authorize app */
        // http://quickblox.com/developers/Authentication_and_Authorization#Authentication_and_Authorization_API

        QBSignature qbSignature = new QBSignature(appId, authKey, authSecret);

        String signatureString = qbSignature.getSignature();

        String authParamsString = qbSignature.getMessage();
        authParamsString += "&signature=" + signatureString;

        client.post("https://api.quickblox.com/session.json?" + authParamsString, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                processAuth(response);
            }
        });
    }

    private void processAuth(String response) {
        Log.d(TAG, "Auth");
        Log.d(TAG, response);
        // Retrieve session here, e.g.
        // {"session":{"application_id":792,"created_at":"2012-08-21T15:45:48Z",
        // "device_id":null,"id":206766,"nonce":4051,
        // "token":"c6e0858706ae9c121a3adcb3786774cbcdeffd79",
        // "ts":1345563945,"updated_at":"2012-08-21T15:45:48Z","user_id":null}}

        try {

            System.out.println(2);

            JSONObject objRoot = new JSONObject(response);
            JSONObject session = objRoot.getJSONObject("session");
            String token = session.getString("token");
            // Add token in request header
            client.addHeader("QB-Token", token);
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println(3);
        }

        // User have been created once, so that code is commented. Just to demonstrate.

        /*
        // 2. Add user
        // http://quickblox.com/developers/Users#API_User_Sign_Up

        RequestParams addUserParams = new RequestParams();
        addUserParams.put("user[login]", userName);
        addUserParams.put("user[password]", userPassword);

        System.out.println(1);

        client.post("http://api.quickblox.com/users.json", addUserParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                Log.d(TAG, response);
                System.out.println(2);
            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                System.out.println(s);
                System.out.println(3);
            }
        });
        */

        /* 3. User sign in */
        RequestParams createUserParams = new RequestParams();
        createUserParams.put("login", userName);
        createUserParams.put("password", userPassword);

        client.post("http://api.quickblox.com/login.json", createUserParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                processUserSignIn(response);
            }
        });
    }

    private void processUserSignIn(String response) {
        Log.d(TAG, "Sign in");
        Log.d(TAG, response);

        // Retrieve user here, e.g.
        // {"user":{"id":10840,"owner_id":784,"full_name":null,
        // "email":null,"login":"test","phone":null,"website":null,
        // "created_at":"2012-08-21T16:41:57Z","updated_at":"2012-08-21T16:41:57Z",
        // "last_request_at":null,"external_user_id":null,"facebook_id":null,
        // "twitter_id":null,"blob_id":null,"user_tags":null}}

        // Now we are authorized and we have permission to manipulate with different objects inside QuickBlox
        // e.g. we can create custom objects, locations, etc.

        processCustomObjectCreate();
    }

    private void processCustomObjectCreate() {
        /* 4. Create custom object */
        // Before using REST API you should create object's model in admin panel using [+ Add class] button
        // e.g. http://image.quickblox.com/59740efe54bff3598adc4cf1afcc.injoit.png

        // Create instance of object
        // http://quickblox.com/developers/Custom_Objects#Create_new_record
        // Class name "game_unit", fields:
        // -- health (int)
        // -- power (int)
        // -- type (string)

        className = "game_unit";

        // Generate random values for new instance
        Random r = new Random();
        Integer randHealth = r.nextInt(100) + 1;
        Integer randPower = r.nextInt(1000) + 1;
        String randType = "type" + r.nextInt(10);

        RequestParams gameUnitParams = new RequestParams();
        gameUnitParams.put("health", randHealth.toString());
        gameUnitParams.put("power", randPower.toString());
        gameUnitParams.put("type", randType);

        client.post("https://api.quickblox.com/data/" + className + ".json", gameUnitParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                Log.d(TAG, "Create custom object");
                Log.d(TAG, response);

                // Retrieve created object, e.g.
                // {"_id":"5033d38936c9ae8e6a0002e1",
                // "health":95,
                // "power":515,
                // "type":"type0",
                // "user_id":10840,
                // "created_at":1345573769,
                // "updated_at":1345573769}

                try {
                    // Now we should get value of "_id" to update exact user
                    JSONObject jsonObject = new JSONObject(response);
                    String objectId = jsonObject.getString("_id");

                    processCustomObjectUpdate(objectId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void processCustomObjectUpdate(String objectId) {
        /* 4. Update custom object */
        // Almost the same as object creating, but
        // difference in request type -- PUT instead of POST.


        Random r = new Random();
        String randType = "some_new_monster_type";

        RequestParams gameUnitParams = new RequestParams();
        gameUnitParams.put("type", randType);

        // Send PUT request
        client.put("https://api.quickblox.com/data/" + className + "/" + objectId + ".json", gameUnitParams,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d(TAG, "Update custom object");
                        Log.d(TAG, response);

                        // Retrieve updated object

                        processCustomObjectsRetrieve();
                    }
                });

    }

    private void processCustomObjectsRetrieve() {
        /* 5. Retrieve all created instances */
        //

        client.get("https://api.quickblox.com/data/" + className + ".json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                Log.d(TAG, "Retrieve custom object instances");
                Log.d(TAG, response);

                // Retrieve JSON array here, e.g.
                // [{"_id":"5033d38936c9ae8e6a0002e1" ...},
                // {"_id":"5033d4bd36c9ae8a6a0002f5" ...},
                // {"_id":"5033d59236c9ae856a0002ee"...},
                // ...]

                processCustomObjectsRetrieveWithFilter();
            }
        });
    }

    private void processCustomObjectsRetrieveWithFilter() {
        /* 6. Retrieve exact instances using filter rules */
        // http://quickblox.com/developers/Custom_Objects#Retrieve_records

        // All filters are available on REST API docs
        // http://quickblox.com/developers/Custom_Objects#Search_operators
        RequestParams filterParams = new RequestParams();
        filterParams.put("health[gt]", "50"); // greater than 50
        filterParams.put("health[lt]", "100"); // less than 100
        filterParams.put("health[ne]", "80"); // not equals to 80

        client.get("https://api.quickblox.com/data/" + className + ".json", filterParams,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d(TAG, "Retrieve filtered custom object instances");
                        Log.d(TAG, response);
                    }
                });
    }
}