package edu.groupchat.server;

import java.util.ArrayList;

public class GroupChatServer {

    private ArrayList<String> clientList; //TODO: make client class and make arrayList list of type client

    public static void main(String[] args) {

        //initialize client list
        // does it make sense to create a client class that holds all info about client?

        // launch separate thread to constantly accept new clients, up to upper limit (10?)
        //


    }

    private void pushMessage() {
        /*
        this will push messages to all clients
        TODO: see below
        NOTE: this is the way messages should be handled:
        use new method for messages that has be implemented in multicast version - when hitting enter
        on text box it does not push the message to your chat, it only pushes messages received from
        server (or multicast group). This is more robust and makes more sense
         */

    }

    private void updateClientList() {

    }

    private void addClient() {

    }

    private void removeClient() {

    }


}
