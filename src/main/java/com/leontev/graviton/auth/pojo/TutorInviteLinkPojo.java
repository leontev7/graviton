package com.leontev.graviton.pojo;

import com.leontev.graviton.model.TutorInviteLink;
import lombok.Data;

@Data
public class TutorInviteLinkPojo {
    private String link;

    public TutorInviteLinkPojo(TutorInviteLink link) {
        this.link = link.getLink();
    }
}
