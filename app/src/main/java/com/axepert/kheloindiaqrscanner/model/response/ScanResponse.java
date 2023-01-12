package com.axepert.kheloindiaqrscanner.model.response;

import java.util.ArrayList;

public class ScanResponse {
    public int code;
    public String message;
    public Data data;

    public class Data{
        public String name;
        public String sportname;
        public String state;
        public String imageUrl;
        public String base_url;
        public ArrayList<Access> access;

        public String getName() {
            return name;
        }

        public String getSportname() {
            return sportname;
        }

        public String getState() {
            return state;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getBase_url() {
            return base_url;
        }

        public ArrayList<Access> getAccess() {
            return access;
        }

        public class Access{
            public String image;
            public String text;

            public String getImage() {
                return image;
            }

            public String getText() {
                return text;
            }
        }

    }

}
