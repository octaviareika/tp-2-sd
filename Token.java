public class Token {

    private String[] slots;
    private int currentPosition;

    public Token(int size){
        this.slots = new String[size];
        this.currentPosition = 0;

        // Inicializar tudo com null
        for(int i = 0; i < size; i++){
            this.slots[i] = null;
        }
    }

    // faz a escrita no slot
    public void writeToSlot(int position, String data){
        if (position >= 0 && position < slots.length){
            slots[position] = data;  // Escreve a string na posição
        }

    }
    // faz a leitura do slot, readFromSlot significa ler do slot
    public String readFromSlot(int position){
        if (position >= 0 && position < slots.length){
            return slots[position];  // Retorna a string na posição
        }
        return null;
    }

    public void moveToNextPosition(){
        currentPosition = (currentPosition + 1) % slots.length;
    }


    public int getCurrentPosition(){
        return currentPosition;
    }

    public void clearSlot(int position){
        if (position >= 0 && position < slots.length){
            slots[position] = null;  // Limpa a posição
        }
    }
}