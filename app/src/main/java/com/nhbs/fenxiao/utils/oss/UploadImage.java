package com.nhbs.fenxiao.utils.oss;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.nhbs.fenxiao.utils.oss.bean.OssConfig;
import java.text.SimpleDateFormat;

public class UploadImage {

  private static final String DOUBLE_SLASH = "//";
  private static final String SLASH = "/";
  private static final String DOT = ".";

  private static final int RESPONSE_SUCCESS = 200;

  /**
   * 同步上传图片到阿里云
   */

  public static PersistenceResponse uploadFile(Context context, String objectName, final String fileAbsPath, OssConfig config) {
    if (TextUtils.isEmpty(fileAbsPath)) {
        return new PersistenceResponse();
    }
    Log.e("======>文件上传", "objectName:" + objectName + ",fileAbsPath:" + fileAbsPath);
    //*********************** 构造 OSSClient ***********************
    ClientConfiguration conf = new ClientConfiguration();
    // 连接超时，默认15秒
    conf.setConnectionTimeout(30 * 1000);
    // socket超时，默认15秒
    conf.setSocketTimeout(30 * 1000);
    // 最大并发请求书，默认5个
    conf.setMaxConcurrentRequest(5);
    // 失败后最大重试次数，默认2次
    conf.setMaxErrorRetry(5);

    OSSLog.disableLog();

    /**
     * bucketname:maskball
     * 文件夹命名规则：以用户id创建文件夹名称，
     * 头像图片以head_uuid.jpg命名，
     * 图库图片以gallery_uuid.jpg命名，
     * 例：用户id为1的用户的存储路径为：maskba/1/head_uuid.jpg
     */
    final String endpoint = config.endpoint;
    final String accessKeyId = config.accessKeyId;
    final String secretKeyId = config.accessKeySecret;
    final String securityToken = config.stsToken;
    final String bucketName = config.bucketName;
    OSSCredentialProvider credentialProvider =
        new OSSStsTokenCredentialProvider(accessKeyId, secretKeyId, securityToken);

    OSSClient oss = new OSSClient(context, endpoint, credentialProvider, conf);

    //*********************** 拼接 request url ***********************

    final String[] splitUrlParts = endpoint.split(DOUBLE_SLASH);

    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
    objectName = format.format(System.currentTimeMillis()) + SLASH + System.currentTimeMillis() + "-" + objectName;
    String requestUrlSB =
        splitUrlParts[0] + DOUBLE_SLASH + bucketName + DOT + splitUrlParts[1] + SLASH + objectName;
    String cloudUrl =
        putObjectFromLocalFile(oss, bucketName, objectName, fileAbsPath) ? requestUrlSB : null;
    PersistenceResponse response = new PersistenceResponse();
    response.cloudUrl = cloudUrl;
    response.success = !TextUtils.isEmpty(cloudUrl);
    response.fileAbsPath = fileAbsPath;
    return response;
  }

  private static boolean putObjectFromLocalFile(OSS client, String bucketName, String ObjectName,
      String uploadFilePath) {
    PutObjectRequest put = new PutObjectRequest(bucketName, ObjectName, uploadFilePath);
    try {
      PutObjectResult putResult = client.putObject(put);
      return putResult != null && putResult.getStatusCode() == RESPONSE_SUCCESS;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }
}
