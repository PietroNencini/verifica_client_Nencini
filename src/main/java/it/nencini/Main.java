package it.nencini;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void showInfo() {
        System.out.println("****** RICHIESTE POSSIBLI *******");
        System.out.println(" Invia una nota al server *");
        System.out.println(" ? - Richiedi le note già inviate *");
        System.out.println(" ! - chiudi la connessione *");
        System.out.println(" Digita '*' e poi la nota (senza spazi di mezzo) per eliminare quella nota (se esiste)");
        System.out.println(" Digita '^' prima della nota (senza spazi di mezzo) per inviare quella nota a tutti gli altri utenti");
    }
    public static void main(String[] args) throws Exception  {
        System.out.println("Client attivo e pronto a collegarsi");
        
        Scanner s = new Scanner(System.in);
        
        Socket clientSocket = new Socket("localhost", 3000);


        String clientUsername = s.nextLine();

        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

        out.writeBytes(clientUsername + "\n");
        String intro_msg = in.readLine();
        System.out.println("\n" + intro_msg + "\n");
        if(intro_msg.equals("UTENTE GIA IN USO\n")) {
            System.exit(0);
        }
        
        String request;
        do {
            showInfo();
            System.out.println("Inserire la richiesta");
            request = s.nextLine();

            out.writeBytes(request + "\n");
            if(!request.equals("!")) {
                String server_response = in.readLine();

                System.out.println("Risposta dal server: " + server_response);

            } else {
                System.out.println("CONNESSIONE TERMINATA");
            }

            s.nextLine();
        } while(request.equals("!"));


        s.close();
        clientSocket.close();
    }
}   