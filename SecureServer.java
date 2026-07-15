/**
 * [백엔드/실행] 자바 기반 금융 보안 데모 웹 서버 구동 메인 클래스
  
 * - 역할: 프로그램의 시작점(main)으로서 포트(8080)를 열고 통신 대기
 * - 금융 IT 포인트: ExecutorService(FixedThreadPool)를 도입하여 대규모 고객 동시 접속 시 서버 안정성 확보
 */

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class SecureServer {
    private static final Logger logger = Logger.getLogger(SecureServer.class.getName());

    public static void main(String[] args) {
        try {
            int port = 8080;
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            
            logger.info("==================================================");
            logger.info("[Java Backend] 금융 보안 데모 웹 서버 구동 중...");
            logger.info(" -> 서버 주소: http://localhost:" + port);
            logger.info("==================================================");

            // 사용자가 들어오면 우리가 만든 StaticFileHandler가 일하게 연결해줍니다.
            server.createContext("/", new StaticFileHandler());
            
            // 일꾼 대기조(스레드 풀 10개) 설정
            ExecutorService threadPool = Executors.newFixedThreadPool(10);
            server.setExecutor(threadPool); 
            
            server.start();
            logger.info("서버가 성공적으로 시작되었습니다. 브라우저로 접속해 보세요!");

        } catch (IOException e) {
            logger.severe("서버 구동 실패: " + e.getMessage());
        }
    }
}