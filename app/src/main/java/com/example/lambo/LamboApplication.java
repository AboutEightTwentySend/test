package com.example.lambo;

import android.app.Application;
import android.graphics.Bitmap;

import com.example.lambo.net.BaseNet;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.StorageUtils;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class LamboApplication extends Application {
    final static String TAG = "lambo";
    ImageLoaderConfiguration imgConfig;

    @Override
    public void onCreate() {
        super.onCreate();
        BaseNet.setContext(this);
        initImageLoader();
    }

    private void initImageLoader() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
//                .showImageOnLoading(R.drawable.default_header)
//                .showImageForEmptyUri(R.drawable.default_header)
//                .showImageOnFail(R.drawable.default_h eader)
                .cacheInMemory(true).cacheOnDisk(true).build();
        imgConfig = new ImageLoaderConfiguration.Builder(this)
                .threadPriority(Thread.NORM_PRIORITY - 2) // default 设置当前线程的优先级
                .threadPoolSize(3) // default  线程池内加载的数量
                .memoryCache(new WeakMemoryCache()) //可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)  // 内存缓存的最大值
                .diskCache(new UnlimitedDiskCache(StorageUtils.getCacheDirectory(this)))//自定义缓存路径
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb sd卡(本地)缓存的最大值
                .diskCacheFileCount(100)  // 可以缓存的文件数量
//                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
//                .denyCacheImageMultipleSizesInMemory()
//                .imageDownloader(new BaseImageDownloader(this)) // default
//                .memoryCacheExtraOptions(480, 800) // default = device screen dimensions 内存缓存文件的最大长宽
//                .memoryCacheSizePercentage(13) // default
//                .diskCacheExtraOptions(480, 800, null)  // 本地缓存的详细信息(缓存的最大长宽)，最好不要设置这个
//                        // default为使用HASHCODE对UIL进行加密命名， 还可以用MD5(new Md5FileNameGenerator())加密
//                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
//                .imageDecoder(new BaseImageDecoder(true)) // default
//                .writeDebugLogs() // 打印debug log
                .defaultDisplayImageOptions(options).build();
        ImageLoader.getInstance().init(imgConfig);
    }
}