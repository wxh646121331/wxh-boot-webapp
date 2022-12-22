package com.wxh.boot.t2;

import com.hundsun.t2sdk.common.core.context.ContextUtil;
import com.hundsun.t2sdk.common.share.dataset.DatasetService;
import com.hundsun.t2sdk.impl.client.T2Services;
import com.hundsun.t2sdk.interfaces.IClient;
import com.hundsun.t2sdk.interfaces.T2SDKException;
import com.hundsun.t2sdk.interfaces.share.dataset.IDataset;
import com.hundsun.t2sdk.interfaces.share.dataset.IDatasets;
import com.hundsun.t2sdk.interfaces.share.event.EventReturnCode;
import com.hundsun.t2sdk.interfaces.share.event.EventTagdef;
import com.hundsun.t2sdk.interfaces.share.event.EventType;
import com.hundsun.t2sdk.interfaces.share.event.IEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wuxinhong
 * @date 2022/8/8 2:38 下午
 */
@Slf4j
public class T2Client {
    private static T2Services server = T2Services.getInstance();;
    private static boolean isInit = false;

    private static IClient client;

    private static void init(){
        try {
//            server.setT2sdkConfigString("classpath:t2sdk-config.xml");
//            String t2sdkConfigString = server.getT2sdkConfigString();
//            System.out.println("t2sdkConfigString:" + t2sdkConfigString);
//            IConfiguration t2sdkConfig = server.getT2sdkConfig();
//            String rootElementAsXml = t2sdkConfig.getRootElementAsXml();
//            System.out.println("rootElementAsXml:" + rootElementAsXml);
//            log.info("T2Services init");
//            server.setT2sdkConfigString("classpath:t2sdk-config.xml");
//            String t2sdkConfigString = server.getT2sdkConfigString();
//            System.out.println("t2sdkConfigString:" + t2sdkConfigString);
//            IConfiguration t2sdkConfig = server.getT2sdkConfig();
            server.init();
            isInit = true;
        } catch (T2SDKException e) {
            e.printStackTrace();
//            log.error("T2Services init error", e);
        }
        if(isInit){
//            log.info("T2Services start");
            server.start();

        }
    }

    private static IClient getClient() throws T2SDKException {
        if(!isInit){
            init();
        }
        if(isInit){
            client = server.getClient("my-server");
        }
        return client;
    }


    public static void test(){
        try{
            IClient client = getClient();
            IEvent event = ContextUtil.getServiceContext().getEventFactory()
                    .getEventByAlias("88", EventType.ET_REQUEST);
//            event.setServiceId("");
            // 往event中添加dataset
            IDataset dataset = DatasetService.getDefaultInstance().getDataset();
            dataset.addColumn("branch_no");
            dataset.addColumn("operator_no");
            dataset.addColumn("fund_account");
            dataset.appendRow();
            dataset.updateString("branch_no", "1");
            dataset.updateString("operator_no", "1");
            dataset.updateString("fund_account", "2");
            event.putEventData(dataset);
            // 同步发送如下
            IEvent rsp = client.sendReceive(event, 10000);
            //先判断返回值
            if(rsp.getReturnCode() !=  EventReturnCode.I_OK){ //返回错误
                System.out.println(rsp.getErrorNo() +" : " + rsp.getErrorInfo());
            }else{
                StringBuilder sb = new StringBuilder();
                //获得结果集
                IDatasets result = rsp.getEventDatas();
                //获得结果集总数
                int datasetCount = result.getDatasetCount();
                //遍历所有的结果集
                for (int i = 0; i < datasetCount; i++) {
                    sb.append("\n" + "dataset - " + result.getDatasetName(i)
                            + "\n");
                    // 开始读取单个结果集的信息
                    IDataset ds = result.getDataset(i);
                    int columnCount = ds.getColumnCount();
                    // 遍历单个结果集列信息
                    for (int j = 1; j <= columnCount; j++) {
                        sb.append(ds.getColumnName(j));
                        sb.append("|");
                        sb.append(ds.getColumnType(j));
                        sb.append("\n");
                    }
                    // 遍历单个结果集记录，遍历前首先将指针置到开始
                    ds.beforeFirst();
                    while (ds.hasNext()) {
                        sb.append("\n");
                        ds.next();
                        for (int j = 1; j <= columnCount; j++) {
                            sb.append(ds.getString(j));
                        }
                    }
                }
                System.out.println(sb);
            }
            // 异步发送如下
            // 注意，在配置中配置的回调函数<method id="3" className="com.jres.test.CallBack"
            // />的id号必须填入event,否则回调不回应答
            event.setIntegerAttributeValue(EventTagdef.TAG_SENDERID, 3);
            client.send(event);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
