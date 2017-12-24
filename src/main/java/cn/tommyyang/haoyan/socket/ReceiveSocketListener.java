package cn.tommyyang.haoyan.socket;

import cn.tommyyang.haoyan.common.SpringBeanFactory;
import cn.tommyyang.haoyan.websocket.hello.HelloHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.socket.TextMessage;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by TommyYang on 2017/12/23.
 */
public class ReceiveSocketListener implements ServletContextListener{

    private final static Logger logger = LoggerFactory.getLogger(ReceiveSocketListener.class);
    private SocketThread socketThread;

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        if(socketThread == null){
            logger.info("init socketThread");
            socketThread = new SocketThread();
            socketThread.start();
        }
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        if(socketThread != null){
            socketThread.interrupt();
        }
    }

    class SocketThread extends Thread{
        private HelloHandler helloHandler = SpringBeanFactory.getBean("helloHandler");
        private ServerSocket serverSocket;
        SocketThread(){
            try {
                logger.info("new ServerSocket");
                serverSocket = new ServerSocket(6000);
            } catch (IOException e) {
                logger.error("new ServerSocket(6000) error:\n", e);
            }
        }
        public void run() {
            while (!this.isInterrupted()){
                try {
                    Socket socket = serverSocket.accept();
                    InputStream inputStream = socket.getInputStream();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    RenderedImage image = ImageIO.read(inputStream);
                    inputStream.close();
                    if(image != null){
                        ImageIO.write(image,"png", outputStream);
                        byte[] bytes = outputStream.toByteArray();
                        BASE64Encoder encoder = new BASE64Encoder();
                        String imgBase64Code = "data:image/png;base64," + encoder.encode(bytes);
                        TextMessage textMessage = new TextMessage(imgBase64Code);
                        helloHandler.broadcast(textMessage);
                    }
                    socket.close();
                }catch (Exception e){
                    logger.error("socket error:\n",e);
                }
            }
        }

    }
}
