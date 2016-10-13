package cn.wyl.welfarecenter.bean;

/**
 * 项目名称：WelfareCenter
 * 创建人：wyl
 * 时间：2016/10/13 18:59
 */
public class MessageBean {

    /**
     * success : true
     * msg : 收藏成功
     */

    private boolean success;
    private String msg;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
