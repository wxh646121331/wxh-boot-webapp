package com.wxh.boot.t2;

import com.hundsun.t2sdk.impl.client.ClientSocket;
import com.hundsun.t2sdk.interfaces.ICallBackMethod;
import com.hundsun.t2sdk.interfaces.share.event.IEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wuxinhong
 * @date 2022/8/8 2:32 下午
 */
@Slf4j
public class Callback implements ICallBackMethod {
    @Override
    public void execute(IEvent iEvent, ClientSocket clientSocket) {
        System.out.println("event attribute count:" + iEvent.getAttributeCount());
    }
}
