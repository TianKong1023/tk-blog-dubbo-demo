package com.kk.blog.enums;

public enum ArticleStatus {
    BLOG("1", "博文"),
    BEAUTY("2", "美文"),
    UNKNOWN("0", "未知");

    private String code;
    private String articleType;

    public String getCode() {
        return code;
    }

    public String getArticleType() {
        return articleType;
    }

    ArticleStatus(String code, String articleType) {
        this.code = code;
        this.articleType = articleType;
    }

    public static ArticleStatus getByCode(String code) {
        for (ArticleStatus value : ArticleStatus.values()) {
            if(value.getCode().equals(code)) {
                return value;
            }
        }
        return UNKNOWN;
    }
}
