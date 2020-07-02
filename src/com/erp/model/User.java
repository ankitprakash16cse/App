package com.erp.model;
import java.io.Serializable;

@SuppressWarnings("unused")
public class User implements Serializable
{
    private String Username;
    public String getUsername() {
        return Username;
    }


    public void setUsername(String username) {
        Username = username;
    }
    private String Password;
    private String BranchCode;

    public String getPassword() {
        return Password;
    }


    public void setPassword(String password) {
        Password = password;
    }


    public String getBranchCode() {
        return BranchCode;
    }


    public void setBranchCode(String branchCode) {
        BranchCode = branchCode;
    }

    public void setUser(String Branch_Code,String Username,String Password)
    {
        this.BranchCode=Branch_Code;
        this.Username=Username;
        this.Password=Password;
    }

}
