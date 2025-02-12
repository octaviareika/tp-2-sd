// Cliente.java
public class Cliente implements Runnable {
    private int id;
    private ClusterSync clusterSync;
    private int numRequests;

    public Cliente(int id, ClusterSync clusterSync) {
        this.id = id;
        this.clusterSync = clusterSync;
        this.numRequests = (int) (Math.random() * 41) + 10; // entre 10 e 50 pedidos
    }

    @Override
    public void run() {
        for (int i = 0; i < numRequests; i++) {
            String request = "Cliente " + id + "\n Timestamp " + System.currentTimeMillis() + "\n";  // gera um timestamp
            System.out.println("Cliente " + id + " solicita acesso ao recurso R: " + request); // request seria tipo o cliente que fez a solicitação

            synchronized (clusterSync) {
                clusterSync.setPendingRequest(request);
            }

            try {
                Thread.sleep(1000); // serve para garantir que apenas um nó acessa o token por vez
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Cliente " + id + " finalizado.");

            try {
                Thread.sleep((long) (Math.random() * 4000) + 1000); // espera de 1 a 5 segundos
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}