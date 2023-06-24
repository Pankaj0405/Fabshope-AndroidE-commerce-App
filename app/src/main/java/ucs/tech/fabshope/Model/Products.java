package ucs.tech.fabshope.Model;

public class Products {
    private String pname, description, price, image, category, pid, date, time,rating,imageUrl,details,size;

    public Products()
    {

    }

    public Products(String pname,String rating, String description, String price, String imageUrl,String details,String size,String image, String category, String pid, String date, String time) {
        this.pname = pname;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.size = size;
        this.image = image;

        this.category = category;
        this.pid = pid;
        this.date = date;
        this.time = time;
        this.rating=rating;
        this.details=details;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
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
}
