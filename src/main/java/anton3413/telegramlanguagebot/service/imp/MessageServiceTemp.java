package anton3413.telegramlanguagebot.service.imp;

public class MessageServiceTemp {
    private long chatId;
    private String text;

    private MessageServiceTemp(){}

    public static class MessageConstructorBuilder{

        private MessageServiceTemp messageConstructor;

        public MessageConstructorBuilder(){
            this.messageConstructor = new MessageServiceTemp();
        }

        public MessageConstructorBuilder setChatId(long chatId){
            this.messageConstructor.chatId = chatId;
            return this;
        }

        public MessageConstructorBuilder setText(String text){
            this.messageConstructor.text = text;
            return this;
        }

        public MessageServiceTemp build(){
            return this.messageConstructor;
        }
    }

    public static MessageConstructorBuilder builder(){
        return new MessageConstructorBuilder();
    }
}
