package com.wenkai.my.gift.http;

import com.wenkai.my.gift.bean.HomeHeartExpandInfo;

/**
 * Created by my on 2016/3/16.
 */
public interface IOkCallBack<E> {
    public void onSuccess(E resultInfo);
}
