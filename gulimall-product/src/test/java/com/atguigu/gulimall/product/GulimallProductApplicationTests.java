package com.atguigu.gulimall.product;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.gulimall.product.entity.BrandEntity;
import com.atguigu.gulimall.product.service.BrandService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

@SpringBootTest
class GulimallProductApplicationTests {

    @Autowired
    BrandService brandService;

    @Resource
    OSSClient ossClient;


    @Test
    void contextLoads() {
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setBrandId(3L);
//        brandEntity.setDescript("国产华为");
        brandEntity.setName("华为1");
//        boolean save = brandService.save(brandEntity);
//        boolean save = brandService.updateById(brandEntity);
//        System.out.println(save);
        List<BrandEntity> brand_id = brandService.list(new QueryWrapper<BrandEntity>().eq("brand_id", 3L));

        brand_id.stream().forEach((item)-> System.out.println(item));
    }

    @Test
    public  void testUpload() throws FileNotFoundException {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "oss-cn-shanghai.aliyuncs.com";
         // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        String accessKeyId = "LTAI4GE9C7YuB79KKU6yMhA5";
        String accessKeySecret = "mjs9XZcNBDjgNUe5VhgYeZOkkP1fpM";
        String bucketName = "gulimall-yanmz";
         // <yourObjectName>上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
        String objectName = "1.jpg";

          // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传文件到指定的存储空间（bucketName）并将其保存为指定的文件名称（objectName）。
        InputStream inputStream = new FileInputStream("C:\\Users\\Tom\\Pictures\\Saved Pictures\\1.jpg");
        ossClient.putObject(bucketName, objectName, inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    @Test
    public  void testUploadClient() throws FileNotFoundException {
        String bucketName = "gulimall-yanmz";
        // <yourObjectName>上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
        String objectName = "gulimall";
        // 上传文件到指定的存储空间（bucketName）并将其保存为指定的文件名称（objectName）。
        InputStream inputStream = new FileInputStream("C:\\Users\\Tom\\Pictures\\Saved Pictures\\1.jpg");
        ossClient.putObject(bucketName, objectName, inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
