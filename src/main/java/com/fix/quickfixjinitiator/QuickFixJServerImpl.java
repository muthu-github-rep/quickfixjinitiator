package com.fix.quickfixjinitiator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import quickfix.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import  quickfix.Session;

@Component
public class QuickFixJServerImpl {

    private SessionSettings sessionSettings;
    private SocketInitiator  socketInitiator;
    private MessageFactory messageFactory;
    private SessionID  sessionID;
    private DataDictionary dataDictionary;
    @Value("${fixDataDictionary}")
    private String dictionaryPath;

    @Autowired
    private Application application;

    @PostConstruct
    public void initialize(){
        try {
            dataDictionary=new DataDictionary(dictionaryPath);
        } catch (ConfigError configError) {
            configError.printStackTrace();
        }
    }
    @PreDestroy
    public void postDestroy(){
        if (sessionID !=null){

        }
    }
    public void loadConfiguration(){
        try {
            sessionSettings=new SessionSettings("src/main/resources/config/initiator.cfg");
        } catch (ConfigError configError) {
            configError.printStackTrace();
        }
    }
    public void logon(){
        if(isValidConfiguration()){
            FileStoreFactory fileStoreFactory = new FileStoreFactory(sessionSettings);
            FileLogFactory fileLogFactory = new FileLogFactory(sessionSettings);
            if(messageFactory==null){
                messageFactory = new DefaultMessageFactory();
            }
            Application application = new TradeAppInitiator();
            try {
                socketInitiator= new SocketInitiator(application,fileStoreFactory,sessionSettings,fileLogFactory,messageFactory);
                socketInitiator.start();
                sessionID=socketInitiator.getSessions().get(0);
                Session.lookupSession(sessionID).logon();
            } catch (ConfigError configError) {
                configError.printStackTrace();
            }
        }
    }
    private boolean isValidConfiguration(){
        boolean flag=false;
        if(sessionSettings!=null && application !=null){
            flag=true;
        }
        return flag;
    }

    private Message getFixMessage(String message){
        Message fixMsg=null;
        message=message.replace(System.getProperty("line.separator"),"");
        try {
            fixMsg=new Message(message,dataDictionary,false);
        } catch (InvalidMessage invalidMessage) {
            invalidMessage.printStackTrace();
        }
        return fixMsg;
    }
    public void logOut(){
        Session.lookupSession(sessionID).logout();
    }
}
