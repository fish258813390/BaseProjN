package com.neil.fish.db;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.neil.fish.R;
import com.neil.fish.utils.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 数据库 工具类 打开资源文件下的db文件
 * 思路:将raw文件夹下的db文件拷贝至内存卡中，在进行读取；
 * 如果内存卡中不存在 db文件就重新进行拷贝，否则就是内存卡中进行读取
 * Created by neil on 2017/10/24 0024.
 */
public class SQLdm {

    //数据库存储路径
    String filePath = "data/data/com.neil.fish/card_bin.db";
    //数据库存放的文件夹 data/data/com.main.jh 下面
    String pathStr = "data/data/com.neil.fish";

    SQLiteDatabase database;

    public SQLiteDatabase openDatabase(Context context) {
        LogUtils.d("filePath:" + filePath);
        File jhPath = new File(filePath);
        //查看数据库文件是否存在
        if (jhPath.exists()) {
            LogUtils.d("存在数据库------打开");
            //存在则直接返回打开的数据库
            return SQLiteDatabase.openOrCreateDatabase(jhPath, null);
        } else {
            //不存在先创建文件夹
            LogUtils.e("不存在数据库------创建");
            File path = new File(pathStr);
            LogUtils.d("pathStr=" + path);
            if (path.mkdir()) {
                LogUtils.d("创建成功");
            } else {
                LogUtils.d("创建失败");
            }
            try {
                //得到资源
                AssetManager am = context.getAssets();
                //得到数据库的输入流
                InputStream is = am.open("card_bin.db");
                LogUtils.d("输入流---->" + is + "");
                //用输出流写到SDcard上面
                FileOutputStream fos = new FileOutputStream(jhPath);
                LogUtils.d("输出流---->" + "fos=" + fos);
                LogUtils.d("jhPath=" + jhPath);
                //创建byte数组  用于1KB写一次
                byte[] buffer = new byte[1024];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                //最后关闭就可以了
                fos.flush();
                fos.close();
                is.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }
            //如果没有这个数据库  我们已经把他写到SD卡上了，然后在执行一次这个方法 就可以返回数据库了
            return openDatabase(context);
        }
    }


    // 获取raw 下的db文件
    static String databaseFilename = Environment.getExternalStorageDirectory().getPath() + "/jjtdb/" + "jjtdb";

    public SQLiteDatabase openDatabaseNew(Context context) {
        try {
//          String databaseFilename = DATABASE_PATH + "/" + DATABASE_FILENAME;
            File dir = new File(Environment.getExternalStorageDirectory().getPath() + "/jjtdb/");
            if (!dir.exists()) {
                dir.mkdir();//新建文件
            }
            if (!(new File(databaseFilename)).exists()) {
                InputStream is = context.getResources().openRawResource(R.raw.card_bin);
                FileOutputStream fos = new FileOutputStream(databaseFilename);
                byte[] buffer = new byte[1024];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
            database = SQLiteDatabase.openOrCreateDatabase(databaseFilename, null);
            return database;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
