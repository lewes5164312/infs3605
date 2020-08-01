package com.example.infs3605;

public class Article {

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUtlToImage() {
        return utlToImage;
    }

    public void setUtlToImage(String utlToImage) {
        this.utlToImage = utlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private Integer id;
    private String author;
    private String title;
    private String description;
    private String url;
    private String utlToImage;
    private String publishedAt;
    private String content;

    public Article(Integer id, String author, String title, String description, String url, String utlToImage, String publishedAt, String content) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.utlToImage = utlToImage;
        this.publishedAt = publishedAt;
        this.content = content;
    }


}
