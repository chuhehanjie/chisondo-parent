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
}
