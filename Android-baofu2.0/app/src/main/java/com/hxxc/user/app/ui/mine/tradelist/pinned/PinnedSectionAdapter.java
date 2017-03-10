package com.hxxc.user.app.ui.mine.tradelist.pinned;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/3/1.
 */

public abstract class PinnedSectionAdapter<T extends MultiItemEntity> extends BaseHeaderAdapter<T> {

    public static final int TYPE_Footer = 3;

    public PinnedSectionAdapter(List<T> data) {
        super(data);
    }
    public abstract void setHasMore(boolean hasMore);

}
