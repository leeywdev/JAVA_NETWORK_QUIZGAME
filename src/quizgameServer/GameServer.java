package quizgameServer;

import java.io.*;
import java.net.*;
import java.util.*;
 
public class GameServer {

	public static int score = 0;
	
    public static String quiz(String exp) {
        String res = "";
        
        if (exp.equalsIgnoreCase("O")) {
        	res = "Correct";
        	score += 10;
        }
        else {
        	res = "Incorrect";
        }
        
        return res;
    }

    public static void main(String[] args) {
        BufferedReader in = null;
        BufferedWriter out = null;
        ServerSocket listener = null;
        Socket socket = null;

        try {
            listener = new ServerSocket(9999); // ���� ���� ����
            System.out.println("������ ��ٸ��� �ֽ��ϴ�.....");
            socket = listener.accept(); // Ŭ���̾�Ʈ�κ��� ���� ��û ���
            System.out.println("����Ǿ����ϴ�.");
            
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));
            
            while (true) {
                String inputMessage = in.readLine();
                
                if (inputMessage.equalsIgnoreCase("bye")) {
                    System.out.println("Ŭ���̾�Ʈ���� ������ �����Ͽ���");
                    out.write("Total score:" + score + "\n");
                    out.flush();
                    break; // "bye"�� ������ ���� ����
                }
                
                System.out.println(inputMessage); // ���� �޽����� ȭ�鿡 ���
                
                String res = quiz(inputMessage); // quiz.  ����� res
                out.write(res + "\n"); //  ��� ���ڿ� ����
                out.flush();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (socket != null)
                    socket.close(); // ��ſ� ���� �ݱ�
                if (listener != null)
                    listener.close(); // ���� ���� �ݱ�
            } catch (IOException e) {
                System.out.println("Ŭ���̾�Ʈ�� ä�� �� ������ �߻��߽��ϴ�.");
            }
        }
    }
}
