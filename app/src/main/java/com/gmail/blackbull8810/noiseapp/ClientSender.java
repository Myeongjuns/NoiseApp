package com.gmail.blackbull8810.noiseapp;

import java.io.DataOutputStream;
import java.net.Socket;

/**
 * Created by Jun on 2017. 4. 16..
 */

// 메시지 전송용 Thread
public class ClientSender implements Runnable {
    Socket socket;
    DataOutputStream out;

    ClientSender(Socket socket) {
        this.socket = socket;

        try {
            this.out = new DataOutputStream(socket.getOutputStream());

        } catch (Exception e) {
        }
    }

    public void run() {
            /*
            //데이터 보내는 코드......
            try {
                Scanner scanner = new Scanner(System.in);
                // 시작하자 마자, 자신의 대화명을 서버로 전송
                if (out != null) {
                    out.writeUTF(name);
                }

                while (out != null) {
                     키보드로 입력받은 데이터를 서버로 전송
                    out.writeUTF(scanner.nextLine());
                }
            } catch (IOException e) {
            }
            */
    }
}//thread_end
