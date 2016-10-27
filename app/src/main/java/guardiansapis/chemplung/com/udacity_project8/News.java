package guardiansapis.chemplung.com.udacity_project8;

/**
 * Created by NUSNAFIF on 10/25/2016.
 */

public class News {
    String title;
    String publishDate;
    String section;
    String author;
    String url;

    public News(String title, String publishDate, String section, String author, String url) {
        this.title = title;
        this.publishDate = publishDate;
        this.section = section;
        this.author = author;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getAuthor() { return author; }

    public void setAuthor(String author) { this.author = author; }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
