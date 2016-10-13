package cn.wyl.welfarecenter.bean;

/**
 * 项目名称：WelfareCenter
 * 创建人：wyl
 * 时间：2016/10/13 18:52
 */
public class CategoryGroupBean {


    /**
     * id : 344
     * name : 最IN
     * imageUrl : muying/2.jpg
     */

    private int id;
    private String name;
    private String imageUrl;

    @Override
    public String toString() {
        return "CategoryGroupBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
