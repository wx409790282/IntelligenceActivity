package com.intelligence.activity.http;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.intelligence.activity.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Convert a JsonElement into a list of objects or an object with Google Gson.
 *
 * The JsonElement is the response object for a {@link com.android.volley.Request.Method} GET call.
 *
 * @author https://plus.google.com/+PabloCostaTirado/about
 */
public class GsonGetRequest<T> extends Request<T>
{
    private final Gson gson;
    private final Type type;
    private final Map<String, String> params;
    private final Response.Listener<T> listener;

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url URL of the request to make
     * @param type is the type of the object to be returned
     * @param listener is the listener for the right answer
     * @param errorListener  is the listener for the wrong answer
     */
    public GsonGetRequest
    (String url, Type type, Gson gson,
     Response.Listener<T> listener, Response.ErrorListener errorListener,Map<String, String> params)
    {
        super(Method.GET, url, errorListener);
        this.gson = gson;
        this.type = type;
        this.listener = listener;
        this.params=params;
    }
    public GsonGetRequest
            (String url, Type type, Gson gson,
             Response.Listener<T> listener, Response.ErrorListener errorListener)
    {
        super(Method.GET, url, errorListener);
        this.gson = gson;
        this.type = type;
        this.listener = listener;
        params=null;
    }

    @Override
    protected void deliverResponse(T response)
    {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response)
    {
        try
        {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            JSONObject resultJson=new JSONObject(json);
            Log.e("Gson",resultJson.toString());
            if(resultJson.getString("status").equals("1")){
                return (Response<T>) Response.success
                        (
                                gson.fromJson(resultJson.getString("data"), type),
                                HttpHeaderParser.parseCacheHeaders(response)
                        );
            }else{
               // Toast.makeText(MyApplication.getInstance().getApplicationContext(),json,Toast.LENGTH_SHORT).show();
                //Toast.makeText(MyApplication.getInstance(),resultJson.getString("data"),Toast.LENGTH_SHORT).show();
            }
        }
        catch (UnsupportedEncodingException e)
        {
            return Response.error(new ParseError(e));
        }
        catch (JsonSyntaxException e)
        {
            return Response.error(new ParseError(e));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}
