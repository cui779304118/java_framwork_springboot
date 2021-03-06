package d1.framework.utilsample;

import d1.framework.util.AlgorithmHelper;
import d1.framework.util.HttpHelper;
import d1.framework.util.MiscHelper;
import d1.framework.util.RSAHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UtilsampleApplicationTests {
    /***
     * 电子卡应用私钥
     */
    public static final String codePrivateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCCpSCH5eRgjutjsxP5vG9pZij/SMQKYHAJ/SEYiF2bOR8tj+RSfyZ6gosUhNDDSR7zewCFcgGn9P3hEVo1vP+ZB2Bv+5po0o8tBpNtr5x0RVtxQ0AGHGurdTIXv2eNqSyVH6iU2GyzPHykI9v6rjku/7bII8Tnmx+4rcovW8+qWiEzfoiG17JZJlc+gBt8JMI9EBscbFwOwtPBvHwyYuJGyTNwzk1J5j/FLhGhE872Fn4IRGdQ/EzS1M1GkX/dFezcc89SQZ9XmsPJKKKUEDrsCx3898PYOG6KiL3bMS3zTT/CejiD+Glrhzw4pM6/UlETyojork1Hv0L/r3XMb/KHAgMBAAECggEABUPEQNW5005PHBBuYKUAVF4Cju8Ov2BNg4mI2OU96HBv2jUvPUfUdCFom2YSx5vdvHAbgEUs3ejYhkZdvUalgjQy5bBc85ZwoJ7gOA5YnrLg0XqlTNm+WQV7BEv5T1Me8c95yq3CC6MHXtH3Hm4A7ezuH7iZScANkjKmsCvh/XpfMEFiPxchgQHoZKY6ko9LrTJ2khBh03+IbmL+jvW9ehZ4U5104X+/QFsNJVZPfVPEUnSw8eHMyLzzxFExnJ7hHa1M7gSwb9uB4ygncyjj9Ck3XFG1bb9iaL37uehwK2yNA+laCiOOvSyPQ0CtStJRT93ZDLYiVKiVnNZr2nxIAQKBgQDnTCd0IZ2q6TVGKiXrx5QvAVUnaeq3s5yhfApNhWvPAMGTAGR8Ft/bhuQtoPcyrAVwmhPxFPU8d4fsJ8N9XO5WMuoROXAtczxQWp1tt/nbGL9x7OvHGi3LhoApGV53+2gwJUuw9o/D11haJ9FsQreNJ9c46kAjvvC1V0HJtkrMAQKBgQCQmQ9M9VRWU8qLpU3B5phOjpgs7v9HHj2QZucc8Puvi9OhZnNEv2uEUXO3kUXrtmmu5jDoXbAlugeF+ir5spvvAt6xqJtP6ZvVj385lDohr7Rn9r2XyneTAnHODhfp10FlQgd4Yh5ocK09yFNhX0WMXHlg//ajyXgo51WprBZehwKBgQCaStzOxVXuGx+krJ0bScl6CcOKRUeEP9auipY83FHshq6ap1pgxMmUIcm4/nWoYcRHNyEyYy6spXgQev4XdJSw/8nQr6Y7S3HikHAQLvgHxdx/3PHzoW0HJbP8tqWkGBuKcnapl/CTTeXsTj+PnnnfOcKCfxFGRys/bPO/4VDIAQKBgCIfnNbChpagy4nNBt6ddpADYrIn7KJx5oFvZ1cTH7I7w0Oxk75DljHxrudBJ0kLEUGvHReZFHqyvhfGA+JNXjM3cCpo6c4gCSCK7rE948ITXvWx6ugpZbKRvx2rgcUzgUFOJckpDpgDDz8nSVYPT7BiGlrjzOa2It80SKjz7Yt9AoGBAKTcry2A2Ob43zHk5AoVSrnKfiYhJ/epaqonEgoRHucHdyqjNJLt2tjkMBWV0yrjXqWj7vi3i4p+Ia00Tfi68vNQJEdtV+o5DQ4wpm3Xx2D8PxQI9cQ2vlV5Wg7jKnwTdT+qdlpwmvD8mqCFY2eLIVIws2R5L8dEjfTu+4WCqlRd";

    /***
     * 校园码离线解码公钥<br/>
     * 默认30天变化一次<br/>
     * 注意此公钥定期变化，请使用接口拉取最新公钥，这里为了演示配置在这里
     */
    public static final String codePublicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIwHbJ4ERDzJNoG0B+dE/hE0NpoevBqu1wy/buzLF5MiALyFMx/YpIm2P+4rGpbRQIiNrs2RbDHQoKLk77A8xI0CAwEAAQ==";
    private String testbase64 = "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";
    private String privatestring = "MIIBVwIBADANBgkqhkiG9w0BAQEFAASCAUEwggE9AgEAAkEAwvO8TQn3FDPE8kJSIqhL1bXp80EL\n" +
            "Qcfvvo2dJbfOXY+dajf5TKHHbRiQoRASkICz5Mt+FbsmrR8vs9yGlB42NwIDAQABAkEAsBey5a9z\n" +
            "o6bjZaQsRqvX3BE2YghFn8R2NJv7737og67K/t1jHCM6ase8Wm/E6EzJBczom51zGYWSs2faVx/p\n" +
            "iQIhAN/kw1dvNgUOb0Oi98QiQiVVXvFcHwoUMFtPEUlf14gFAiEA3uiEKW2ukjCjjYgDRBch0IdU\n" +
            "KonQkLl3x0F/uH4iRgsCIQDTcPOZ6wkVFWC2e32J2OCm6UzUm68kkZRpZu5oYP7nEQIhAIclReuw\n" +
            "OiUcJC0NB53iwHejdN4NjEwShpJiHcOHhdQdAiEA3LsfS3Vm8HMKNoCSTrvu5u1mGZBwrxe9JHqe\n" +
            "fEdmKCU=";
    private String publicstring = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAMLzvE0J9xQzxPJCUiKoS9W16fNBC0HH776NnSW3zl2P\n" +
            "nWo3+Uyhx20YkKEQEpCAs+TLfhW7Jq0fL7PchpQeNjcCAwEAAQ==";

    @Test
    public void contextLoads() throws Exception {
        System.out.println(MiscHelper.getUUID32());
        testrsa();
    }

    private void testjsrsa2() throws Exception {
        String value = "dHt075moS3cYKZX3xS1juSgnuTA4I1nRWihXDCv/AuVlJd3Ahx9rwsIvCwfT/q9HOWV8L4P7Lxm2dCXYjnntOQ==";
        byte[] afterEncrypt = RSAHelper.base642Byte(value);
        PublicKey publicKey = RSAHelper.string2PublicKey(publicstring);
        byte[] afterDecrypt = RSAHelper.publicDecrypt(afterEncrypt, publicKey);
        System.out.println(new String(afterDecrypt, "utf-8"));
    }

    private void testjsrsa1() throws Exception {
        String value = "urVH6c3METFDum9i35hcISj2LD55YtXyN7L03XL/mcK7vuLhV8OGLxCDnriP907P+gW7Xj8O2WLTmZU88POM1w==";
        PrivateKey privateKey = RSAHelper.string2PrivateKey(privatestring);

        byte[] afterEncrypt = RSAHelper.base642Byte(value);
        byte[] afterDecrypt = RSAHelper.privateDecrypt(afterEncrypt, privateKey);
        System.out.println(new String(afterDecrypt, "utf-8"));
    }

    private void testrsa() throws Exception {
//        KeyPair pair = RSAHelper.getKeyPair(512);

//        PublicKey publicKey = RSAHelper.string2PublicKey( publicstring);
//        PrivateKey privateKey = RSAHelper.string2PrivateKey( privatestring);
        PublicKey publicKey = RSAHelper.string2PublicKey(codePublicKey);
        PrivateKey privateKey = RSAHelper.string2PrivateKey(codePrivateKey);
        byte[] data = "我iamwho".getBytes("utf-8");
        byte[] afterEncrypt = RSAHelper.publicEncrypt(data, publicKey);
        String v = RSAHelper.byte2Base64(afterEncrypt);
        System.out.println(v);
        byte[] afterDecrypt = RSAHelper.privateDecrypt(afterEncrypt, privateKey);
        System.out.println(new String(afterDecrypt, "utf-8"));

        afterEncrypt = RSAHelper.privateEncrypt(data, privateKey);
        afterDecrypt = RSAHelper.publicDecrypt(afterEncrypt, publicKey);
        System.out.println(new String(afterDecrypt, "utf-8"));
    }

    private void testmd5() throws UnsupportedEncodingException {
        String test = "我iamwho";//E8B9CDF2F6742A5F613DB2298873D8AD
        System.out.println(AlgorithmHelper.md5_32(test, true));
    }

    private void testBase64() throws Exception {
        String temp = MiscHelper.file2Base64("C:\\Users\\liuyi\\Desktop\\1.jpg");
        System.out.print(temp.equals(testbase64));
    }

    private void testheader() throws Exception {
        Test0 test0 = new Test0();
        test0.image = testbase64;
        String url = "http://ocrcp.market.alicloudapi.com/rest/160601/ocr/ocr_vehicle_plate.json";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE 09360871641a44a380be995b40f00930");
        String ss = HttpHelper.<String>postObjectAsJSON(url, test0, String.class, headers);

    }

    private void testdownload() throws Exception {
        String url = "http://olpxrx06o.bkt.clouddn.com/car1.jpg";
        byte[] data = HttpHelper.downloadFileAsByte(url);
        String filepath = "C:\\Users\\liuyi\\Desktop\\1.jpg";
        File file = new File(filepath);
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(data, 0, data.length);
        fos.flush();
        fos.close();

    }

    private void testhttp1() throws Exception {
        String s = "<xml><nonce_str><![CDATA[35f4094e40f44406b487b16cbbdcdf34]]></nonce_str><out_trade_no><![CDATA[1537423610890]]></out_trade_no><appid><![CDATA[wxfd4760906693c291]]></appid><sign><![CDATA[013CB6C3BFA7095188789525152E1E9B]]></sign><trade_type><![CDATA[APP]]></trade_type><mch_id><![CDATA[1449496302]]></mch_id><notify_url><![CDATA[http://www.baidu.com]]></notify_url></xml>";
        String ss = HttpHelper.<String>postObjectAsJSON("https://open.ucpaas.com/ol/sms/sendsms", s, String.class);
    }

    private void testsign() throws Exception {
        Test3 test3 = new Test3();
        Map<String, Object> map = MiscHelper.objectToMapWithoutNull(test3);
        String sign = AlgorithmHelper.hmacSign(map, "key", "192006250b4c09247ec02edce69f6a2d");
        System.out.println(sign);//9A0A8659F005D6984697E2CA0A9CF3B7
    }

    private void testobjecttomap() throws Exception {
        Test2 test2 = new Test2();
        test2.mobile = "18513197785";
        test2.code = "ss";
        test2.count = "sd";
        test2.msg = "sdfa";
        Map<String, Object> map = MiscHelper.objectToMapWithoutNull(test2);
    }

    private void testhttp() throws Exception {
        Test1 test1 = new Test1();
        test1.appid = "113f067fca4644978bb91a1e3a7ba3b1";
        test1.mobile = "18513197785";
        test1.sid = "053308d91b5e169e2e856644f5bd468f";
        test1.token = "3b7ead851e3ffb43608cde1335dff397";
        test1.param = "1232123";
        test1.templateId = "368230";
        Test2 test2 = HttpHelper.<Test2>postObjectAsJSON("https://open.ucpaas.com/ol/sms/sendsms", test1, Test2.class);
        System.out.println(test2.code);
    }

    class Test0 {
        String image;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }

    class Test1 {
        String sid;
        String token;
        String appid;
        String templateId;
        String param;
        String mobile;

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getTemplateId() {
            return templateId;
        }

        public void setTemplateId(String templateId) {
            this.templateId = templateId;
        }

        public String getParam() {
            return param;
        }

        public void setParam(String code) {
            this.param = code;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }

}
