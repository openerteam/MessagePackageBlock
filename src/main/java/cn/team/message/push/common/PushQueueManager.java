package cn.team.message.push.common;



import cn.team.message.common.MessagePlatformInterface;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by magicbeans on 2017/7/18.
 */
public class PushQueueManager {

    private Logger logger;

    private ExecutorService executor;

    private ExecutorService heartbeat;

    private Future future;

    private Queue<AbstractPushMessage> queue;

    private static PushQueueManager pushQueueManager;

    private List<MessagePlatformInterface> platforms;

    private int maxQueue = 20;
    private int maxTime = 5;

    private boolean isStop = false;


    private PushQueueManager() {
        executor = Executors.newCachedThreadPool();
        heartbeat = Executors.newFixedThreadPool(1);
        queue = new LinkedList();
        platforms = new ArrayList<MessagePlatformInterface>();
        logger = Logger.getLogger("推送日志");
    }


    public static PushQueueManager getInstances() {
        if (pushQueueManager == null) {
            pushQueueManager = new PushQueueManager();
        }
        return pushQueueManager;
    }

    public void add(AbstractPushMessage message) {
        if (message == null) {
            logger.log(Level.INFO, "消息为空因此忽略发送");
            return;
        }
        logger.log(Level.INFO, "向队列注入消息" + message.toString());
        queue.add(message);
    }

    public void startQueue() {

        future = heartbeat.submit(new Runnable() {
            long oldTime = 0;

            int count=0;

            public void run() {
                while (!isStop) {
                    if (count % 120 == 0){
                        logger.log(Level.INFO, "消息服务线程心跳" + System.currentTimeMillis());
                    }
                    try {
                        Thread.sleep(1000);
                        count ++;
                        if (queue.size() == 0) {
                            oldTime = System.currentTimeMillis();
                            continue;
                        }
                        if (queue.size() > maxQueue || System.currentTimeMillis() - oldTime > maxTime * 1000) {
                            oldTime = System.currentTimeMillis();
                            //启动发送
                            executor.execute(new MessgeThread(queue));
                            queue.clear();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private class MessgeThread implements Runnable {

        private Queue<AbstractPushMessage> sendQueue;

        public MessgeThread(Queue backup) {
            sendQueue = new LinkedList();
            sendQueue.addAll(backup);
        }

        public void run() {
            while (sendQueue.size() > 0) {
                AbstractPushMessage message = sendQueue.peek();
                if (message == null) {
                    sendQueue.poll();
                    continue;
                }
                logger.log(Level.INFO, "开发分发消息" + message.toString());
                dispatchPlatformMessage(message);
                logger.log(Level.INFO, message.toString() + "退出队列");
                sendQueue.poll();
            }
        }
    }

    public boolean isSurvival() {
        return future.isCancelled() && future.isDone();
    }

    public void stop() {
        isStop = true;
    }

    private void dispatchPlatformMessage(AbstractPushMessage message) {
        for (MessagePlatformInterface platform : platforms) {
            if (platform == null) {
                continue;
            }
            if (!platform.checkMessage(message)) {
                continue;
            }
            logger.log(Level.INFO, "开始发送" + message.toString() + "消息向" + platform.getName() + "平台");
            boolean result = platform.sendMessage(message);
            logger.log(Level.INFO, "发送" + message.toString() + "消息向" + platform.getName() + "平台状态：" + result);
        }
    }

    public void addPushPlatform(MessagePlatformInterface abstractPushPlatform) {
        platforms.add(abstractPushPlatform);
    }
}
