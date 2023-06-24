package ucs.tech.fabshope.Model;

public class AdminOrders {

    private String name,phone,address,city,state,date,time,totalAmount,orderid,userid;

    public AdminOrders() {
    }


    public AdminOrders(String name,String address, String phone, String city, String state, String date, String time, String totalAmount,String orderid,String userid) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.city = city;
        this.state = state;
        this.date = date;
        this.time = time;
        this.orderid=orderid;
        this.userid=userid;
        this.totalAmount = totalAmount;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
