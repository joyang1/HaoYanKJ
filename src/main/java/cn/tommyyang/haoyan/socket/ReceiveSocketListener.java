package cn.tommyyang.haoyan.socket;

import cn.tommyyang.haoyan.common.SpringBeanFactory;
import cn.tommyyang.haoyan.websocket.hello.HelloHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;

import javax.imageio.ImageIO;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by TommyYang on 2017/12/23.
 */
public class ReceiveSocketListener implements ServletContextListener{

    private final static Logger logger = LoggerFactory.getLogger(ReceiveSocketListener.class);
    private HelloHandler helloHandler = SpringBeanFactory.getBean(HelloHandler.class);
    private ServerSocket serverSocket;
    private SocketThread socketThread;

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        if(socketThread == null){
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

        public void run() {
            while (!this.isInterrupted()){
                try {
                    serverSocket = new ServerSocket(6000);
                    Socket socket = serverSocket.accept();
                    InputStream inputStream = socket.getInputStream();
                    Image image = ImageIO.read(inputStream);
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] bytes = new byte[1024];
                    int length = 0;
                    while ((length = inputStream.read(bytes, 0, 1024))> 0){
                        outputStream.write(bytes, 0, length);
                    }
                    TextMessage textMessage = new TextMessage(outputStream.toByteArray());
                    helloHandler.broadcast(textMessage);
                }catch (Exception e){
                    logger.error("socket error:\n",e);
                }
            }
        }

    }
}
