package com.chylee.fxiaoke.xjl.event;

import com.chylee.fxiaoke.common.event.RequestEvent;
import com.chylee.fxiaoke.xjl.event.data.object.AccountAddrObj;
import com.chylee.fxiaoke.xjl.event.data.object.AccountObj;
import com.chylee.fxiaoke.xjl.event.data.object.ContactObj;

import java.util.List;

public class AccountReqEvent extends RequestEvent {
    private AccountObj accountObj;
    private ContactObj contactObj;
    private List<AccountAddrObj> accountAddrObjs;

    public AccountObj getAccountObj() {
        return accountObj;
    }

    public void setAccountObj(AccountObj accountObj) {
        this.accountObj = accountObj;
    }

    public ContactObj getContactObj() {
        return contactObj;
    }

    public void setContactObj(ContactObj contactObj) {
        this.contactObj = contactObj;
    }

    public List<AccountAddrObj> getAccountAddrObjs() {
        return accountAddrObjs;
    }

    public void setAccountAddrObjs(List<AccountAddrObj> accountAddrObjs) {
        this.accountAddrObjs = accountAddrObjs;
    }

    public void assign(AccountReqEvent reqEvent) {
        this.accountObj = reqEvent.getAccountObj();
        this.contactObj = reqEvent.getContactObj();
        this.accountAddrObjs = reqEvent.getAccountAddrObjs();
    }
}
