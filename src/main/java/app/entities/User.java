package app.entities;

public class User {

    private int user_id;
    private String user_name;
    private String user_password;
    private String zipcode;
    private String user_role;

    public User(int user_id, String user_name, String user_password,String zipcode, String user_role) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_password = user_password;
        this.zipcode = zipcode;
        this.user_role = user_role;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", user_password='" + user_password + '\'' +
                ", user_role='" + user_role + '\'' +
                '}';
    }
}

