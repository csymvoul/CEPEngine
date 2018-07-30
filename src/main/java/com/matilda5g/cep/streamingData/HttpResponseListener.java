package com.matilda5g.cep.streamingData;

import org.json.JSONObject;

public class HttpResponseListener {
    void onResponse(JSONObject httpResponse){
        System.out.println(httpResponse.toString());
    }
}
