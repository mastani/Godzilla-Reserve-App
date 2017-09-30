package ir.mastani.godzilla.api;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ir.mastani.godzilla.R;
import ir.mastani.godzilla.api.callback.InitCallback;
import ir.mastani.godzilla.api.callback.LoginCallback;
import ir.mastani.godzilla.api.volley.AppController;
import ir.mastani.godzilla.utils.Installation;
import ir.mastani.godzilla.utils.Utilities;

public class Requests {
    private final static String URL_BASE = "http://192.168.1.100:8000/";
    private final static String URL_INIT = URL_BASE + "api/init";
    private final static String URL_LOGIN = URL_BASE + "api/login";

    public static void initSession(final Context context, final InitCallback callback) {
        StringRequest strReq = new StringRequest(Request.Method.POST, URL_INIT, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);

                    if (json.has("success")) {
                        boolean success = json.getBoolean("success");
                        if (success) {
                            String url = json.getString("captcha");
                            callback.onCaptcha(url);
                        } else {
                            callback.OnError();
                        }
                    } else {
                        callback.OnError();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    callback.OnError();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                callback.OnError();
                Toast.makeText(context, R.string.error_internet, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("installation_id", Installation.id(context));
                params.put("device", Utilities.getPhoneString());
                return params;
            }
        };

        strReq.setShouldCache(false);
        strReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(strReq);
    }

    public static void loginRequest(final Context context, final String username, final String password, final String captcha, final LoginCallback callback) {
        StringRequest strReq = new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);

                    Parser.parseUser(json);
                    // parse program

                    callback.onSuccess();
                } catch (JSONException e) {
                    e.printStackTrace();
                    callback.OnError();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                callback.OnError();
                Toast.makeText(context, R.string.error_internet, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("installation_id", Installation.id(context));
                params.put("username", username);
                params.put("password", password);
                params.put("captcha", captcha);
                return params;
            }
        };

        strReq.setShouldCache(false);
        strReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(strReq);
    }
}
