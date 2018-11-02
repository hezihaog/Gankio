package me.wally.gankio.enums;

/**
 * Package: me.wally.gankio.enums
 * FileName: DeleteFlag
 * Date: on 2018/11/1  下午2:12
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public enum DeleteFlag {
    NO_DELETE(0, "未删除"),
    DELETED(1, "已删除");

    private int mCode;
    private String mDesc;

    DeleteFlag(int code, String desc) {
        mCode = code;
        mDesc = desc;
    }

    public int getCode() {
        return mCode;
    }

    public String getDesc() {
        return mDesc;
    }

    @Override
    public String toString() {
        return "DeleteFlag{" +
                "mCode=" + mCode +
                ", mDesc='" + mDesc + '\'' +
                '}';
    }
}