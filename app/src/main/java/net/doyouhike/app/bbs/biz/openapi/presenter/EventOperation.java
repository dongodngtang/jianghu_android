package net.doyouhike.app.bbs.biz.openapi.presenter;

/**
 * 作者：luochangdong on 16/9/19
 * 描述：
 */
public enum EventOperation {
    //操作类型      confirm(确认成员)， reject(拒绝成员)，delete(删除成员)，
    // set_ordinary(设置为普通成员)，set_collaborator(设置为协作),
    // set_accountant(设置为财务)， set_guard(调协为留守人员)
    CONFIRM("confirm"),
    REJECT("reject"),
    DELETE("delete"),
    SET_ORDINARY("set_ordinary"),
    SET_COLLABORATOR("set_collaborator"),
    SET_ACCOUNTANT("set_accountant"),
    SET_GUARD("set_guard");


    private String operation;

    EventOperation(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }
}
