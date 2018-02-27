package com.aiju.zyb.manage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by AIJU on 2017-05-21.
 */

public class ThreadManage {
    private static ThreadManage instance;
    private ExecutorService executorService=null;
    public ThreadManage() {
        executorService = Executors.newCachedThreadPool(); // // 固定五个线程来执行任务
    }
    public static synchronized ThreadManage getIns() {
        if (null == instance) {
            instance = new ThreadManage();
        }
        return instance;
    }
    public  ExecutorService  getExecutorService()
    {
        return  executorService;
    }

    /***
     *
     * 关闭线程池对象
     */
    public void  CloseExecutorService()
    {
        if(executorService!=null)
        {
            executorService.shutdown();
            executorService=null;
            instance=null;
        }

    }
}
