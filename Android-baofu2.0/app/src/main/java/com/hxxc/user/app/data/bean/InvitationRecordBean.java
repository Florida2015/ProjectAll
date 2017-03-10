package com.hxxc.user.app.data.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by houwen.lai on 2016/11/4.
 * 邀请好友 明细
 */

public class InvitationRecordBean implements Serializable {

    private BigDecimal invitationAward;
    private int invitationTotalNumber;

    public InvitationRecordBean() {
        super();
    }

    public BigDecimal getInvitation() {
        return invitationAward;
    }

    public void setInvitation(BigDecimal invitation) {
        this.invitationAward = invitation;
    }

    public int getInvitationTotalNumber() {
        return invitationTotalNumber;
    }

    public void setInvitationTotalNumber(int invitationTotalNumber) {
        this.invitationTotalNumber = invitationTotalNumber;
    }

    @Override
    public String toString() {
        return "InvitationRecordBean{" +
                "invitationAward=" + invitationAward +
                ", invitationTotalNumber=" + invitationTotalNumber +
                '}';
    }
}
