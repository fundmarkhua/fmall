package com.fmall.search.message;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by jack.zhang
 * email:57525101@qq.com
 * 2017/5/13 11:10
 */
public class MyMessageListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        //取消息内容
        TextMessage textMessage = (TextMessage) message;
        try {
            String text = textMessage.getText();
            System.out.println(text);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
