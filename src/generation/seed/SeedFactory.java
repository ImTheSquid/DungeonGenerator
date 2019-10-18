package generation.seed;

public class SeedFactory {
    public static GeneratorSeed getDefault(int roomsToGenerate){
        return new GeneratorSeed(roomsToGenerate, 1, 3, 1, 2, 1, 3);
    }
}
