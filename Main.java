// Main.java
public class Main {

    public static void main(String[] args) {
        int numNodes = 10; // NUMERO DE NOS NO CLUSTERSYNC
        Token token = new Token(numNodes);

        // Cria os elementos do ClusterSync e inicia as threads
        ClusterSync[] clusterSyncs = new ClusterSync[numNodes];
        for (int i = 0; i < numNodes; i++) {
            clusterSyncs[i] = new ClusterSync(i, token, clusterSyncs); // tem que passar o proprio clusterSyncs
            new Thread(clusterSyncs[i]).start(); // começa a thread
        }

        // Cria cliente
        Cliente[] clientes = new Cliente[numNodes];
        for (int i = 0; i < numNodes; i++) {
            clientes[i] = new Cliente(i, clusterSyncs[i % numNodes]);
            new Thread(clientes[i]).start();
        }
    }
}