package cn.tommyyang.haoyan;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * Created by tommy on 2017/11/9.
 */
@Controller
@RequestMapping("fileapi")
public class FileController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(BaseController.class);

    @RequestMapping(value = "/uploadfile1.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> uploadFile(HttpServletRequest request, HttpServletResponse response,
                                          @RequestParam(value = "photo", required = false) String photo) {
        try {
            byte[] img = new BASE64Decoder().decodeBuffer(photo);
            for (int i = 0; i < img.length; i++) {
                if (img[i] < 0) {
                    img[i] += 256;
                }
            }
            logger.info(String.format("上传图片的大小是:%d", img.length));
            String tmpPath = request.getRealPath("/") + "temp";
            File dir = new File(tmpPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            logger.info(String.format("上传图片的路径是:%s", tmpPath));
            String filePath = tmpPath + "/" + UUID.randomUUID().toString().replaceAll("-", "") + ".png";
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream outStream = new FileOutputStream(file);
            outStream.write(img);
            outStream.flush();
            outStream.close();
            return renderData(response, "sucess");
        } catch (Exception e) {
            logger.error("file upload error:\n", e);
            return renderErrorData(response, 500, e.getMessage());
        }
    }

    @RequestMapping(value = "/uploadfile.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> uploadFile(HttpServletRequest request, HttpServletResponse response,
                                          @RequestParam(value = "fileimg", required = false) MultipartFile fileimg) {
        try {
            String tmpPath = request.getRealPath("/") + "temp";
            File dir = new File(tmpPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            logger.info(String.format("上传图片的路径是:%s", tmpPath));
            String filePath = tmpPath + "/" + UUID.randomUUID().toString().replaceAll("-", "") + ".png";
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            fileimg.transferTo(file);
            return renderData(response, "sucess");
        } catch (Exception e) {
            logger.error("file upload error:\n", e);
            return renderErrorData(response, 500, e.getMessage());
        }
    }

    @RequestMapping(value = "/display.do", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getFiles(HttpServletRequest request, HttpServletResponse response) {
        try {
            String tmpPath = request.getRealPath("/") + "temp";
            File dir = new File(tmpPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File[] files = dir.listFiles();
            List<String> fileContents = new ArrayList<String>();
            for (File file : files) {
                fileContents.add(this.generateImgToBASE64String(file));
            }
            return renderData(response, fileContents);
        } catch (Exception e) {
            logger.error("file upload error:\n", e);
            return renderErrorData(response, 500, e.getMessage());
        }
    }

    private String generateImgToBASE64String(File file) {
        //图片转化成base64字符串
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(file);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return "data:image/png;base64," + encoder.encode(data);//返回Base64编码过的字节数组字符串
    }

}
