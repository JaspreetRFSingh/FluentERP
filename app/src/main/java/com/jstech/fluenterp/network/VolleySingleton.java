package com.jstech.fluenterp.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * App-wide singleton for Volley network requests.
 *
 * Usage:
 *   VolleySingleton.getInstance(context).addToRequestQueue(myRequest);
 *
 * Creating a new RequestQueue per Activity (the old pattern) is wasteful — each queue
 * spawns its own thread pool and cache. One shared queue handles all in-flight requests
 * and respects the Android Activity lifecycle through the application context.
 */
public class VolleySingleton {

    private static VolleySingleton instance;
    private final RequestQueue requestQueue;

    private VolleySingleton(Context context) {
        // Use application context to avoid leaking an Activity reference.
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized VolleySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new VolleySingleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }
}
