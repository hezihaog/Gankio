package me.wally.gankio.enums;

/**
 * Package: me.wally.gankio.enums
 * FileName: GankCategoryType
 * Date: on 2018/10/31  下午12:15
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public enum GankCategoryType {
    ANDROID("Android"),
    WELFARE("福利"),
    IOS("iOS"),
    WEB("前端");

    private String mType;

    GankCategoryType(String type) {
        mType = type;
    }

    public String getType() {
        return mType;
    }

    @Override
    public String toString() {
        return this.mType;
    }
}
