package com.TAS.takeasup.Model;

public class GalleryPics {

    String coverPic,coverName;

    public GalleryPics(String coverPic, String coverName) {
        this.coverPic = coverPic;
        this.coverName = coverName;
    }

    public GalleryPics() {

    }

    public String getCoverPic() {
        return coverPic;
    }

    public void setCoverPic(String coverPic) {
        this.coverPic = coverPic;
    }

    public String getCoverName() {
        return coverName;
    }

    public void setCoverName(String coverName) {
        this.coverName = coverName;
    }
}
