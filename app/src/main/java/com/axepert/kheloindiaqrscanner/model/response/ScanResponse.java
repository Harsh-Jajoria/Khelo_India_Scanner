package com.axepert.kheloindiaqrscanner.model.response;

public class ScanResponse {
    public int code;
    public String status;
    public Data data;

    public class Data {
        public String imageUrl;
        public String name;
        public String sportName;
        public String state;
        public String category;

        public String getImageUrl() {
            return imageUrl;
        }

        public String getName() {
            return name;
        }

        public String getSportName() {
            return sportName;
        }

        public String getState() {
            return state;
        }

        public String getCategory() {
            return category;
        }
    }

}
