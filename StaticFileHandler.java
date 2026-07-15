/**
 * [백엔드/네트워크] 웹 브라우저 접속 요청 처리 및 index.html 화면 전송
  
 * - 역할: 사용자가 http://localhost:8080으로 접속했을 때 index.html(가상 키패드) 화면을 전송
 * - 금융 IT 포인트: 사용자 IP 확인 및 접속 로그 기록, 파일 존재 여부 확인 후 404 에러 처리
 */

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class StaticFileHandler implements HttpHandler {
    private static final Logger logger = Logger.getLogger(StaticFileHandler.class.getName());

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String filePath = "./index.html"; // 가져올 화면 파일 경로
        byte[] responseBytes;

        // 접속한 유저의 IP 확인
        String clientIp = exchange.getRemoteAddress().getAddress().getHostAddress();

        if (Files.exists(Paths.get(filePath))) {
            // 파일을 읽어서 전송
            responseBytes = Files.readAllBytes(Paths.get(filePath));
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, responseBytes.length);
            
            logger.info(String.format("[Server Log] IP: %s -> 로그인 화면(index.html) 서빙 성공", clientIp));
        } else {
            // 파일이 없으면 404 에러 전송
            String errorMsg = "<h1>404 Not Found</h1>";
            responseBytes = errorMsg.getBytes("UTF-8");
            exchange.sendResponseHeaders(404, responseBytes.length);
            
            logger.warning(String.format("[Server Warning] IP: %s -> index.html 파일 분실!", clientIp));
        }

        // 스트림 전송 마감
        OutputStream os = exchange.getResponseBody();
        os.write(responseBytes);
        os.close();
    }
}