package com.TAS.takeasup.Model;

public class Users {

    String user_name,phone,email,uid;//,profilePic;

    public Users() {
    }

    public Users(String user_name, String phone, String email, String uid){//,String profilePic) {
        this.user_name = user_name;
        this.phone = phone;
        this.email = email;
        this.uid = uid;
//        this.profilePic = profilePic;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

//    public String getProfilePic() {
//        return profilePic;
//    }
//
//    public void setProfilePic(String profilePic) {
//        this.profilePic = profilePic;
//    }

}
