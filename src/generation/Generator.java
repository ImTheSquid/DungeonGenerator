package generation;

import generation.seed.GeneratorSeed;

public class Generator {
    private static GeneratorSeed seed=null;
    private static boolean isActive=false;
    public static void init(GeneratorSeed seed){
        Generator.seed=seed;
        isActive=true;
    }

    public static void generateNext(){
        if(seed==null||!isActive)return;

        seed.stepGenerator();

        if(seed.isFinished()){
            isActive=false;
            seed=null;
        }
    }
}
