package com.axepert.kheloindiaqrscanner.model.response;

import androidx.annotation.Keep;

import java.util.ArrayList;

@Keep
public class ScanResponse {
    public int code;
    public String message;
    public Data data;

    public class Data {
        public String name;
        public String sportname;
        public String state;
        public String imageUrl;
        public String base_url;
        public ArrayList<Access> access;
        public int dine_in;
        public String transport;
        public String sport_zone;
        public String venue;
        public String category_color;
        public String designation;
        public String category;

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

        public int getDine_in() {
            return dine_in;
        }

        public String getTransport() {
            return transport;
        }

        public String getSport_zone() {
            return sport_zone;
        }

        public String getVenue() {
            return venue;
        }

        public String getCategory_color() {
            return category_color;
        }

        public String getDesignation() {
            return designation;
        }

        public String getCategory() {
            return category;
        }

        public class Access {
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
