package com.chisondo.server.modules.tea.constant;

public class TeaSpectrumConstant {

    public static final int QUERY_ALL = 0;

    public interface StandardFlag {
        int STANDARD = 1;
        int NORMAL = 2;
    }

    public interface AuthFlag {
        int YES = 1;
        int NO = 2;
    }

    public interface OrderByFlag {
        int NONE = 0;
        int MAKE_TEA_TIMES = 1;
        int PUBLISH_TIME = 2;
    }

    public interface MyChapuType {
        int CREATED = 0;
        int SAVED = 1;
        int USED = 2;
    }

    public interface MyChapuFlag {
        // 0-创建的；1-收藏的；2-我编辑的；4-我点赞的；5-我评论的；6-我使用过的；7-保存的
        int CREATED = 0;
        int FAVORITE = 1;
        int EDITED = 2;
        int LIKED = 4;
        int COMMENTED = 5;
        int USED = 6;
        int SAVED = 7;
    }

    public interface MyChapuOperFlag {
        int DELETE = 0;
        int FINISH = 1;
        int CREATE = 0;
        int MODIFY = 1;
        int SAVE = 2;
    }
}
