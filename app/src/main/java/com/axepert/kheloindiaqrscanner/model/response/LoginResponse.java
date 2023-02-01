package com.axepert.kheloindiaqrscanner.model.response;

import androidx.annotation.Keep;

@Keep
public class LoginResponse {
    public int code;
    public String message;
    public Data data;

    public class Data {
        public String id;
        public String name;
        public String image;
        public String email;
        public String phone;
        public String access_code;
        public String department;
        public String base_url;

        public String getName() {
            return name;
        }

        public String getImage() {
            return image;
        }

        public String getEmail() {
            return email;
        }

        public String getAccess_code() {
            return access_code;
        }

        public String getPhone() {
            return phone;
        }

        public String getBase_url() {
            return base_url;
        }

        public String getId() {
            return id;
        }

        public String getDepartment() {
            return department;
        }
    }
}
