package com.intranet.project.service.classes;

public class UpdatePassword {
    private String currentPassword;
    private String newPassword;
    private Long id;

    public UpdatePassword(){

    }
    public UpdatePassword(String currentPassword, String newPassword, Long id) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.id = id;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
