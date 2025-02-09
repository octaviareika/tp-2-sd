public class ClusterSync implements Runnable {

    private int id;
    private Token token;
    private String pendingRequest; // solicitação pendente do cliente

    public ClusterSync(int id, Token token){
        this.id = id;
        this.token = token;
        this.pendingRequest = null;
    }

    public void setPendingRequest(String request){
        this.pendingRequest = request;
    }

    @Override
    public void run() {
        while (true){
            synchronized (token){ // serve para garantir que apenas um nó acessa o token por vez
                // verifica se é a vez deste nó processar o token
                if (token.getCurrentPosition() == id){
                    // verifica se há uma solicitação pendente
                    if (pendingRequest != null){
                        token.writeToSlot(id, pendingRequest);
                        pendingRequest = null;
                    }
                    else {
                        token.writeToSlot(id, "NULL");
                    }

                    if (shouldAccessResource()){
                        accessResource();
                        token.clearSlot(id); // Limpa o slot depois q acessa o recurso
                    }

                    token.moveToNextPosition();
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean shouldAccessResource(){
        // Verifica se o nó tem o menor timestamp gerado
        for (int i = 0; i < token.readFromSlot(id).length(); i++){
            if (token.readFromSlot(i) != null && !token.readFromSlot(i).equals("NULL")){
                if (token.readFromSlot(i).compareTo(token.readFromSlot(id)) < 0){
                    return false;
                }
            }
        }

        return true;
    }

    private void accessResource(){
        System.out.println("ClusterSync " + id + " acessando o recurso R...");

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("ClusterSync " + id + " liberando o recurso R...");
    }
    
}
