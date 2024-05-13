package app.entities;

public class User {

    private int userId;
    private String userName;
    private String userPassword;
    private String userEmail;
    private String zipcode;
    private String userRole;
    private String userAddress;

    public User(int userId, String userName, String userPassword, String userEmail, String zipcode, String userRole, String userAddress) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        this.zipcode = zipcode;
        this.userRole = userRole;
        this.userAddress = userAddress;
    }

    public String getZipcode() {
        return zipcode;
    }
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserPassword() {
        return userPassword;
    }
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    public String getUserRole() {
        return userRole;
    }
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public String getUserAddress() {
        return userAddress;
    }
    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + userId +
                ", user_name='" + userName + '\'' +
                ", user_email='" + userEmail + '\'' +
                ", user_password='" + userPassword + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", user_role='" + userRole + '\'' +
                ", user_address='" + userAddress + '\'' +
                '}';
    }
}

