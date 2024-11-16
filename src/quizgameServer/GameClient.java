package quizgameServer;


import java.io.*;
import java.net.*;
import java.util.*;

public class GameClient {
    public static void main(String[] args) {
        BufferedReader in = null;
        BufferedWriter out = null;
        Socket socket = null;
        Scanner scanner = new Scanner(System.in);
        try {
            socket = new Socket("localhost", 9999);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            while (true) {
                System.out.print("정답 입력(O/X)>>"); // 프롬프트
                String outputMessage = scanner.nextLine(); // 키보드에서  읽기
                if (outputMessage.equalsIgnoreCase("bye")) {
                    out.write(outputMessage + "\n"); // "bye" 문자열 전송
                    out.flush();
                    String inputMessage = in.readLine(); // 서버로부터 최종 점수 수신
                    System.out.println("총점: " + inputMessage);
                    break; // 사용자가 "bye"를 입력한 경우 서버로 전송 후 연결 종료
                }
                out.write(outputMessage + "\n"); // 키보드에서 읽은  문자열 전송
                out.flush();
                String inputMessage = in.readLine(); // 서버로부터  결과 수신
                System.out.println("결과: " + inputMessage);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                scanner.close();
                if (socket != null)
                    socket.close(); // 클라이언트 소켓 닫기
            } catch (IOException e) {
                System.out.println("서버와 채팅 중 오류가 발생했습니다.");
            }
        }
    }
}
