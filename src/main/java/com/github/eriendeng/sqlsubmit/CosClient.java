package com.github.eriendeng.sqlsubmit;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.COSObjectInputStream;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.region.Region;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CosClient {
    private COSClient cosClient;
    private String bucket;

    public CosClient(String region, String bucket, String secretId, String secretKey) {
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region rg = new Region(region);
        ClientConfig clientConfig = new ClientConfig(rg);
        this.cosClient = new COSClient(cred, clientConfig);
        this.bucket = bucket;
    }

    public List<String> GetSql(String path) {
        GetObjectRequest getObjectRequest = new GetObjectRequest(this.bucket, path);
        COSObject cosObject = cosClient.getObject(getObjectRequest);
        COSObjectInputStream cosObjectInput = cosObject.getObjectContent();
        try {
            List<String> texts = new BufferedReader(
                    new InputStreamReader(cosObjectInput, StandardCharsets.UTF_8)
            ).lines().collect(Collectors.toList());
            cosObjectInput.close();
            return texts;
        }catch (Exception e) {
            System.err.println("cos read error: " + e);
        }
        return Arrays.asList("", "");
    }

}
