package com.axepert.kheloindiaqrscanner.model.request;

public class ScanRequest {
    private String user_id;
    private String access_code;
    private String uid;

    public ScanRequest(String user_id, String access_code, String uid) {
        this.user_id = user_id;
        this.access_code = access_code;
        this.uid = uid;
    }
}
