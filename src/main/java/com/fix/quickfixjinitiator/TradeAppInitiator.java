package com.fix.quickfixjinitiator;

import org.springframework.stereotype.Component;
import quickfix.*;

@Component
public class TradeAppInitiator extends MessageCracker implements Application{
    @Override
    public void onCreate(SessionID sessionID) {
        System.out.println(" onCreate");
    }

    @Override
    public void onLogon(SessionID sessionID) {
        System.out.println(" onLogon");
    }

    @Override
    public void onLogout(SessionID sessionID) {
        System.out.println(" onLogout");
    }

    @Override
    public void toAdmin(Message message, SessionID sessionID) {
        System.out.println(" toAdmin "+message.toString());
    }

    @Override
    public void fromAdmin(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
        System.out.println(" fromAdmin  "+message.toString());
    }

    @Override
    public void toApp(Message message, SessionID sessionID) throws DoNotSend {
        System.out.println(" toApp  "+message.toString());
    }

    @Override
    public void fromApp(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        System.out.println(" fromApp  "+message.toString());
    }
}
