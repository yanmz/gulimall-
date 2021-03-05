package com.atguigu.gulimall.thirdparty;

import com.aliyun.oss.OSSClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@SpringBootTest
class GulimallThirdPartyApplicationTests {


    @Resource
    OSSClient ossClient;

    @Test
    public  void testUploadClient() throws FileNotFoundException {
        String bucketName = "gulimall-yanmz";
        // <yourObjectName>上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
        String objectName = "haha.jpg";
        // 上传文件到指定的存储空间（bucketName）并将其保存为指定的文件名称（objectName）。
        InputStream inputStream = new FileInputStream("C:\\Users\\Tom\\Pictures\\Saved Pictures\\1.jpg");
        ossClient.putObject(bucketName, objectName, inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
