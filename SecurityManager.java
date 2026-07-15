/**
 * [백엔드/보안] 개인정보 및 계좌 비밀번호 실시간 검증 & 즉시 메모리 소멸 
  
 * - 역할: 사용자 비밀번호 일치 여부를 대조하고 검증 종료 즉시 메모리를 안전하게 소멸
 * - 금융 보안 포인트: String 대신 가변 객체인 char[]를 사용하고, 검증 즉시 Arrays.fill()로 메모리 덮어쓰기 수행
 */

import java.util.Arrays;
import java.util.logging.Logger;

public class SecurityManager {
    private static final Logger logger = Logger.getLogger(SecurityManager.class.getName());
    
    // 실제 정답 비밀번호 (테스트용 1234)
    private static final char[] REAL_PASSWORD = {'1', '2', '3', '4'};

    /**
     * 사용자가 입력한 비밀번호를 안전하게 검증하고 메모리에서 흔적을 파괴합니다.
     */
    public static boolean verifyAndDestroy(char[] inputPassword) {
        try {
            logger.info("[Security] 비밀번호 일치 검증을 시작합니다.");
            
            // 1. 안전하게 배열 대 배열로 비밀번호 비교
            boolean isSuccess = Arrays.equals(inputPassword, REAL_PASSWORD);

            // 2. [가장 중요] 검증이 끝난 즉시 원본 데이터 파괴!
            Arrays.fill(inputPassword, '0');
            logger.info("[Security] 메모리 무효화(Zeroization) 완료 -> 입력값 '0000'으로 물리적 덮어쓰기 완료");

            return isSuccess;
        } catch (Exception e) {
            logger.warning("[Security Error] 비밀번호 처리 중 에러 발생: " + e.getMessage());
            return false;
        }
    }
}import java.util.Arrays;
import java.util.logging.Logger;

public class SecurityManager {
    private static final Logger logger = Logger.getLogger(SecurityManager.class.getName());
    
    // 실제 정답 비밀번호 (테스트용 1234)
    private static final char[] REAL_PASSWORD = {'1', '2', '3', '4'};

    /**
     * 사용자가 입력한 비밀번호를 안전하게 검증하고 메모리에서 흔적을 파괴합니다.
     */
    public static boolean verifyAndDestroy(char[] inputPassword) {
        try {
            logger.info("[Security] 비밀번호 일치 검증을 시작합니다.");
            
            // 1. 안전하게 배열 대 배열로 비밀번호 비교
            boolean isSuccess = Arrays.equals(inputPassword, REAL_PASSWORD);

            // 2. [가장 중요] 검증이 끝난 즉시 원본 데이터 파괴!
            Arrays.fill(inputPassword, '0');
            logger.info("[Security] 메모리 무효화(Zeroization) 완료 -> 입력값 '0000'으로 물리적 덮어쓰기 완료");

            return isSuccess;
        } catch (Exception e) {
            logger.warning("[Security Error] 비밀번호 처리 중 에러 발생: " + e.getMessage());
            return false;
        }
    }
}