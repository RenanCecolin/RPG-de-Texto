import java.util.Random;

public final class Dado { 

    private static final Random random = new Random();

    private Dado() {
        // Construtor privado para impedir instância
    }

    public static int rolar(int faces) {
        if (faces < 1) {
            throw new IllegalArgumentException("Número de faces deve ser >= 1");
        }
        return random.nextInt(faces) + 1;
    }

    public static int rolar() {
        return rolar(6);
    }
}