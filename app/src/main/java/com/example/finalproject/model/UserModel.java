package com.example.finalproject.model;

public class UserModel {
    public String userId;
    private FbUserModel fbUserModel = new FbUserModel();
    final public static UserModel instance = new UserModel();

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void getUserData(Model.Listener<User> callback) {
        fbUserModel.getUserData(userId, callback);
    }

    public void createUser(User user, Model.Listener<Void> callback) {
        fbUserModel.createUser(user, callback);
    }

    public void updateUser(User user, Model.Listener<Void>  callback) {
        fbUserModel.updateUser(user, callback);
    }
}
