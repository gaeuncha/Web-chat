package com.example.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service

public class ChatService {
    private final ObjectMapper objectMapper;
    private Map<String, ChatRoom> chatRooms;    //채팅방정보

    @PostConstruct
    private void init(){
        chatRooms = new LinkedHashMap();
    }

    //채팅방 전체조회
    public List<ChatRoom> findAllRoom(){
        return new ArrayList<>(chatRooms.values());
    }
    //채팅방번호로 조회
    public ChatRoom findRoomById(String roomid){
        return chatRooms.get(roomid);
    }
    //채팅방생성 : 고유의 채팅방 id를 가진 채팅방 객체 생성
    public ChatRoom createRoom(String name){
        String randomid = UUID.randomUUID().toString();
        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(randomid)
                .name(name)
                .build();
        chatRooms.put(randomid, chatRoom);
        return chatRoom;

    }

    //메세지전송 : 지정한 websocket 메세지 발송
    public <T> void sendMessage (WebSocketSession session, T message){
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e){
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
    }
}
