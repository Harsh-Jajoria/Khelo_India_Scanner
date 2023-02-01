package com.axepert.kheloindiaqrscanner.model.request;

import androidx.annotation.Keep;

@Keep
public class ScanRequest {
    private String user_id;
    private String access_code;
    private String name;
    private String image;

    public ScanRequest(String user_id, String access_code, String name, String image) {
        this.user_id = user_id;
        this.access_code = access_code;
        this.name = name;
        this.image = image;
    }
}
