
import com.google.firebase.Timestamp;

public class Journal {
    private String title;
    private String thought;
    private String userId;
    private String imageUrl;
    private String username;
    private com.google.firebase.Timestamp timeAdded;

    public Journal() { }

    public Journal(String title, String thought, String userId, String imageUrl, String username, Timestamp timeAdded) {
        this.title = title;
        this.thought = thought;
        this.userId = userId;
        this.imageUrl = imageUrl;
        this.username = username;
        this.timeAdded = timeAdded;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThought() {
        return thought;
    }

    public void setThought(String thought) {
        this.thought = thought;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public com.google.firebase.Timestamp getTimeAdded() {
        return timeAdded;
    }

    public void setTimeAdded(com.google.firebase.Timestamp timeAdded) {
        this.timeAdded = timeAdded;
    }
}
