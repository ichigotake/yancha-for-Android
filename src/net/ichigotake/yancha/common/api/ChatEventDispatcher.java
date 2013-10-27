package net.ichigotake.yancha.common.api;

import net.ichigotake.yancha.chat.ChatCallbackListener;


public class ChatEventDispatcher {

	public void dispatch(
			YanchaEmitter emitter, String eventName,
			String response, ChatCallbackListener listener) {
		EmitEvent event = EmitEvent.get(eventName);
		if (null == event) {
			//TODO onError����
			return ;
		}
		switch (event) {
		case ANNONCEMENT:
			break;
		case CONNECT:
			listener.onConnect(emitter);
			break;
		case CONNECTIONG:
			break;
		case DELETE_USER_MESSAGE:
			break;
		case DISCONNECT:
			listener.onDisconnect();
			break;
		case ERROR:
			break;
		case JOIN_TAG:
			break;
		case NICKNAMES:
			listener.onNicknames(emitter, response);
			break;
		case NO_SESSION:
			break;
		case RECONNECT:
			break;
		case RECONNECTING:
			break;
		case TOKEN_LOGIN:
			break;
		case USER_MESSAGE:
			listener.onUserMessage(emitter, response);
			break;
		default:
			break;
		
		}
	}

}