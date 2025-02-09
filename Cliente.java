public class Cliente implements Runnable{
    private int id;
    private ClusterSync clusterSync;

    public Cliente(int id, ClusterSync clusterSync){
        this.id = id;
        this.clusterSync = clusterSync;
    }

    @Override
    public void run(){
        for (int i = 0; i < 10; i++){
            String request = "Cliente " + id + "Timestamp " + System.currentTimeMillis();//gera um timestamp
            System.out.println("Cliente " + id + " solicita acesso ao recurso R: " + request);
            
            synchronized (clusterSync){
                clusterSync.setPendingRequest(request);
            }
        
        }

        try {
            Thread.sleep(1000); // serve para garantir que apenas um nó acessa o token por vez
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("Cliente " + id + " finalizado.");

        try {
           Thread.sleep((long) (Math.random() * 4000) + 1000); // serve para garantir que apenas um nó acessa o token por vez
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    
}
