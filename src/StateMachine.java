/**
 * Created by Wenjian on 2016/5/14, 0014.
 */
public class StateMachine {

    private volatile State state;
    public void run(){

        this.state = State.HELLO;
        while (!state.isHandshakeComplete()) state.processMessage(this, state);

        this.state = State.TALKING;
        state.processMessage(this, state);
    }
    private void setState(State state){
        this.state = state;
    }

    enum State{
        HELLO(false){
            @Override
            void sayHello() {
                System.out.println("hello");
            }

            @Override
            void ourPosition(StateMachine sm) {
                System.out.println("we are in the " + State.HELLO);
                sm.setState(HANDSHAKE);
            }
        },

        HANDSHAKE(false){
            @Override
            void handShake() {
                System.out.println("handshake");
            }

            @Override
            void ourPosition(StateMachine sm) {
                System.out.println("we are in the " + State.HANDSHAKE);
                sm.setState(TALKING);
            }
        },

        TALKING(true){
            @Override
            void talking() {
                System.out.println("talking");
            }

            @Override
            void ourPosition(StateMachine sm) {
                System.out.println("we are in the " + State.TALKING);
            }
        };

        private final boolean isHandshakeComplete;
        State(boolean b){this.isHandshakeComplete = b;}

        public boolean isHandshakeComplete(){return isHandshakeComplete;}

        void sayHello(){}

        void handShake(){}
        void talking(){}

        void ourPosition(StateMachine sm){}

        void processMessage(StateMachine sm, State s){
            switch (s){
                case HELLO:
                    sayHello();
                    ourPosition(sm);
                    break;
                case HANDSHAKE:
                    handShake();
                    ourPosition(sm);
                    break;
                case TALKING:
                    talking();
                    ourPosition(sm);
                    break;
                default:
                    System.out.println("error");
                    break;
            }
        }

    }

}
