package cn.wyl.welfarecenter.bean;

/**
 * 项目名称：WelfareCenter
 * 创建人：wyl
 * 时间：2016/10/13 19:13
 */
public class ColorBean {

    private int colorId;
    private String colorName;
    private String colorCode;
    private String colorImg;
    private String colorUrl;

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public String getColorImg() {
        return colorImg;
    }

    public void setColorImg(String colorImg) {
        this.colorImg = colorImg;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getColorUrl() {
        return colorUrl;
    }

    public void setColorUrl(String colorUrl) {
        this.colorUrl = colorUrl;
    }
}
