package crawl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.*;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: crawl_data
 * @ClassName: Content
 * @Description: 主类
 * @Author: ZhangJun
 * @Date: 2019/12/5 16:20
 */
public class Content {
    static File file = new File("d://temp/crawl/meituan");
    static File fileChild = new File("d://temp/crawl/meituan/child");
    public static void main(String[] args) {
        file.mkdirs();
        fileChild.mkdirs();
        doCrawl();
    }

    private static void crawlPage() {
        String url = "https://cd.meituan.com/meishi/";
        for (int i = 1; i < 68; i++) {
            Request build = new Request.Builder()
                    .header("Accept", "application/json")
                    .header("Cookie", "uuid=6bbdfb32a1254cb8a2a1.1575533562.1.0.0; _lx_utm=utm_source%3DBaidu%26utm_medium%3Dorganic; _lxsdk_cuid=16ed51e43b3c8-045e818ddeffdb-2393f61-1fa400-16ed51e43b3c8; _lxsdk=16ed51e43b3c8-045e818ddeffdb-2393f61-1fa400-16ed51e43b3c8; _hc.v=9cfc0e82-e5e0-b029-6821-a0a0182292d3.1575533616; ci=59; rvct=59%2C10%2C774; client-id=7d2509f0-1cd4-4484-aa54-7ef788f271c6; lat=30.540624; lng=104.059104; wm_order_channel=default; utm_source=; au_trace_key_net=default; openh5_uuid=16ed51e43b3c8-045e818ddeffdb-2393f61-1fa400-16ed51e43b3c8; cssVersion=4e969a3d; __mta=245782843.1575533782699.1575533800144.1575536331261.4; _lxsdk_s=16ed51e43b5-59b-2fa-76e%7C%7C108")
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36")
                    .url("https://cd.meituan.com/meishi/api/poi/getPoiList?cityName=%E6%88%90%E9%83%BD&cateId=0&areaId=0&sort=&dinnerCountAttrId=&page=" + i + "&userId=&uuid=6bbdfb32a1254cb8a2a1.1575533562.1.0.0&platform=1&partner=126&originUrl=https%3A%2F%2Fcd.meituan.com%2Fmeishi%2F&riskLevel=1&optimusCode=10&_token=eJx1j0tvozAUhf%2BLt6DYEB52dpmUCid0GgIEQtWFeQTI8Co4kGY0%2F31cqV10UelK5%2FO5R0fXf8FAM7BSECIIyWDKB7ACygItDCADPoqNbur60sAqNg1FBul3DyMig2Q4PoDVi6LqukwM8vrhHITxohAVyQrC6FX%2BYk2wqon5SFERAiXn%2FbiCMM0WTV7xK2sXaddAwWNZQXHFDwEgGhpfNAj986nsU%2FnX%2B0n8RlSMVdEKyrdzffHV5%2FmydsucbLRst7X0Wzbk7ibwrPK50ObKTU9JeLO1u%2BX9dlH9KFHDs68Oj9hApLaH7H1tRUnOw24zR7i4HlDR2SNsL3BZQxpQSNXaKxKLaXF9YGWwp2oeec3x1Pb%2B3qajeaXOvhmYx6a1ToPK7ncda1KHeN2v1FACGtb%2BZfAfwrfYDYyta4YxntJ8uJfRvWsf32LnZho4wI12Oiwxk2b9KSZnX1o3R%2B5YZ2VixPZ7nu2mRPGkjW2dC%2FDvPwSLksI%3D")
                    .build();
            try {
                Response execute = new OkHttpClient().newCall(build).execute();
                writeAppend(new StringBuilder(execute.body().string()), new File(file, i + ".json"));
                //响应写到本地
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static Response getResponse(String url,String accept) {
        Request build = new Request.Builder()
                .header("Accept", accept)
                .header("Host","www.meituan.com")
                .header("Cookie", "uuid=6bbdfb32a1254cb8a2a1.1575533562.1.0.0; _lx_utm=utm_source%3DBaidu%26utm_medium%3Dorganic; _lxsdk_cuid=16ed51e43b3c8-045e818ddeffdb-2393f61-1fa400-16ed51e43b3c8; _lxsdk=16ed51e43b3c8-045e818ddeffdb-2393f61-1fa400-16ed51e43b3c8; _hc.v=9cfc0e82-e5e0-b029-6821-a0a0182292d3.1575533616; ci=59; rvct=59%2C10%2C774; client-id=7d2509f0-1cd4-4484-aa54-7ef788f271c6; lat=30.540624; lng=104.059104; wm_order_channel=default; utm_source=; au_trace_key_net=default; openh5_uuid=16ed51e43b3c8-045e818ddeffdb-2393f61-1fa400-16ed51e43b3c8; cssVersion=4e969a3d; __mta=245782843.1575533782699.1575533800144.1575536331261.4; _lxsdk_s=16ed51e43b5-59b-2fa-76e%7C%7C108")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36")
                .url(url)
                .build();
        try {
            Response execute = new OkHttpClient().newCall(build).execute();
            return execute;
            //响应写到本地
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void doCrawl() {
        //这里直接遍历
        for (int i = 49; i < 68; i++) {
            File file = new File(Content.file, i + ".json");
            //遍历里面的数据
            StringBuilder read = read(file);
            //读出来,接下来就是遍历数据，然后获得并存盘
            parseAndSave(i,read);
            System.out.println("第"+i+"页的数据");
        }
    }

    private static void parseAndSave(int page,StringBuilder read) {
        JSONObject jsonObject = JSONObject.parseObject(read.toString());
        JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("poiInfos");
        //然后遍历
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject item = jsonArray.getJSONObject(i);
            //这里直接请求，获得数据后存盘
            rquestAndParseAndSave(page,item);
        }
    }

    private static void rquestAndParseAndSave(int page,JSONObject item) {
        String poiId1 = item.getString("poiId");
        String selector = "#app > section > div > div.details.clear > div.d-left";
        Element poiId = getElementByOkhttp("https://www.meituan.com/meishi/" + poiId1);
        Elements script = poiId.getElementsByTag("script");
        Element ele=script.get(15>script.size()-1?script.size()-1:15);
            String text = ele.toString().replaceAll("<script>","").replaceAll("</script>","");
                String s = text.replaceAll("window._appState = ", "");
                //直接写到文件里面
                writeAppend(new StringBuilder(s),new File(fileChild,page+"-"+poiId1+".json"));

    }

    private static StringBuilder read(File file) {
        StringBuilder result = new StringBuilder();
        //读取文件出来
        String line = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static Element getElementByJsoup(String url) {
        try {
            return Jsoup.connect(url).get().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static Element getElementByOkhttp(String url){
        Response response = getResponse(url, "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
        try {
            String string = response.body().string();
            return Jsoup.parse(string);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


    public static void writeAppend(StringBuilder sb, File file) {
        try {
            file.createNewFile();
            FileWriter bw = new FileWriter(file, true);
            bw.append(sb);
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
