package net.doyouhike.app.bbs.biz.openapi.presenter;

/**
 * 作者：luochangdong on 16/9/19
 * 描述：
 */
public class EventRole {
    /**
     * 召集人
     */
    public static final int CONVENER = 1;
    /**
     * 协作
     */
    public static final int COOPERATE = 2;
    /**
     * 财务
     */
    public static final int BOOKKEEPER = 3;
    /**
     * 普通成员
     */
    public static final int MEMBER = 4;
    /**
     * 留守人员
     */
    public static final int QUEUE = 5;
    /**
     * 未确认成员
     */
    public static final int UNPASS = 9;

    /**
     * 游客 没有报名
     */
    public static final int VISITOR = 0;
    //操作类型      confirm(确认成员)， reject(拒绝成员)，delete(删除成员)，
    // set_ordinary(设置为普通成员)，set_collaborator(设置为协作),
    // set_accountant(设置为财务)， set_guard(调协为留守人员)
    public static final String HANDLE_confirm = "confirm";
    public static final String HANDLE_reject = "reject";
    public static final String HANDLE_delete = "delete";
    public static final String HANDLE_set_ordinary = "set_ordinary";
    public static final String HANDLE_set_collaborator = "set_collaborator";
    public static final String HANDLE_set_accountant = "set_accountant";
    public static final String HANDLE_set_guard = "set_guard";


    public static String getOperStr(int role){
        String operation = "";
        switch (role){
            case COOPERATE:
                operation = HANDLE_set_collaborator;
                break;
            case BOOKKEEPER:
                operation = HANDLE_set_accountant;
                break;
            case MEMBER:
                operation = HANDLE_set_ordinary;
                break;
            case QUEUE:
                operation = HANDLE_set_guard;
                break;
        }
        return operation;
    }


}
